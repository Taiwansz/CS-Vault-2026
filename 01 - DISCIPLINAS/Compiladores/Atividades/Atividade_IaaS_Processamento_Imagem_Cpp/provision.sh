#!/usr/bin/env bash
# ==============================================================================
# SCRIPT DE AUTOMACAO E PROVISIONAMENTO IaaS (SERVIDOR LINUX UBUNTU CRU)
# Disciplina: Compiladores / Infraestrutura em Nuvem
# Cenário: Servidor Linux/Ubuntu recem-criado com acesso SSH limpo.
# Prova Pratica: No IaaS, a responsabilidade pelo ambiente e 100% do desenvolvedor.
# Autor: Matheus Sousa dos Santos (Taiwansz)
# ==============================================================================

set -euo pipefail

# 1. Verificacao de Privilegios Administrativos (Root / Sudo)
if [[ $EUID -ne 0 ]]; then
   echo "[-] Erro: Este script de provisionamento IaaS requer privilegios de superusuario."
   echo "[!] Execute com: sudo ./provision.sh"
   exit 1
fi

echo "======================================================================"
echo "  PROVISIONAMENTO IaaS: SERVICO C++ DE PROCESSAMENTO DE IMAGENS       "
echo "  Provando a gestao integral de ambiente pelo desenvolvedor           "
echo "======================================================================"

# 2. Desabilitar Prompts Interativos do Sistema Operacional
export DEBIAN_FRONTEND=noninteractive
export NEEDRESTART_MODE=a

# 3. Atualizacao dos Repositorios do Sistema Operacional
echo "[1/6] Atualizando repositorios e pacotes do sistema base..."
apt-get update -y
apt-get upgrade -y

# 4. Instalacao do Compilador (g++) e Dependencias de Construcao
echo "[2/6] Instalando compilador g++, bibliotecas OpenMP e utilitarios de rede..."
apt-get install -y \
    build-essential \
    g++ \
    libomp-dev \
    curl \
    net-tools \
    ufw \
    htop

# 5. Configuracao de Variaveis de Ambiente do Servico
echo "[3/6] Configurando variaveis de ambiente persistentes no SO..."
export PORT=8080
export NUM_THREADS=$(nproc --all)
export BENCHMARK_RES=2048

# Persistir no ambiente do sistema (/etc/environment e perfil global)
cat << 'EOF' > /etc/profile.d/image_service_env.sh
export PORT=8080
export NUM_THREADS=$(nproc --all)
export BENCHMARK_RES=2048
EOF
chmod +x /etc/profile.d/image_service_env.sh

echo "  [+] PORT configurada para: $PORT"
echo "  [+] NUM_THREADS configurado para: $NUM_THREADS (todos os nucleos fisicos)"
echo "  [+] BENCHMARK_RES configurado para: ${BENCHMARK_RES}x${BENCHMARK_RES}"

# 6. Liberacao da Porta no Firewall (Seguranca IaaS)
if command -v ufw &> /dev/null && ufw status | grep -q "Status: active"; then
    echo "[*] Liberando porta ${PORT}/tcp no firewall UFW..."
    ufw allow ${PORT}/tcp
fi

# 7. Compilacao do Codigo-Fonte C++ com Otimizacao Maxima
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SOURCE_FILE="${SCRIPT_DIR}/image_processor.cpp"
BINARY_FILE="${SCRIPT_DIR}/image_service"

if [[ ! -f "$SOURCE_FILE" ]]; then
    echo "[-] Codigo fonte image_processor.cpp nao encontrado em ${SCRIPT_DIR}."
    exit 1
fi

echo "[4/6] Compilando codigo fonte C++ com g++ (-O3, -march=native, -fopenmp)..."
g++ -O3 -march=native -fopenmp -std=c++17 "$SOURCE_FILE" -o "$BINARY_FILE"
chmod +x "$BINARY_FILE"

echo "[+] Binario executavel gerado com sucesso: $BINARY_FILE"

# 8. Criacao e Inicializacao do Servico no Sistema Operacional (systemd)
echo "[5/6] Registrando e iniciando servico daemon no systemd..."
cat << EOF > /etc/systemd/system/image-processor.service
[Unit]
Description=Servico C++ de Processamento Pesado de Imagens (IaaS)
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=${SCRIPT_DIR}
Environment="PORT=${PORT}"
Environment="NUM_THREADS=${NUM_THREADS}"
Environment="BENCHMARK_RES=${BENCHMARK_RES}"
ExecStart=${BINARY_FILE}
Restart=always
RestartSec=3

[Install]
WantedBy=multi-user.target
EOF

systemctl daemon-reload
systemctl enable image-processor.service
systemctl restart image-processor.service

# Aguardar subida do socket
sleep 2

# 9. Validacao da Inicializacao na Porta Correta e Teste Funcional
echo "[6/6] Validando o servico ativo na porta ${PORT}..."

if netstat -tuln | grep -q ":${PORT} "; then
    echo "[+] SUCESSO: Servico ouvindo ativamente na porta ${PORT}!"
else
    echo "[-] AVISO: netstat nao detectou a porta ainda. Verificando logs do systemd..."
    journalctl -u image-processor.service --no-pager -n 10
fi

echo "--- TESTE DE SAUDE (GET /health) ---"
curl -s "http://localhost:${PORT}/health" || true
echo ""

echo "--- TESTE DE PROCESSAMENTO PESADO (POST /process) ---"
curl -s -X POST "http://localhost:${PORT}/process" || true
echo ""

echo "======================================================================"
echo "  PROVISIONAMENTO CONCLUIDO COM EXITO!                                "
echo "  Status do Servico: systemctl status image-processor.service         "
echo "  Porta Ativa: http://<IP_DO_SERVIDOR>:${PORT}/                       "
echo "  No IaaS, toda a cadeia (SO, g++, env vars, systemd) foi erguida     "
echo "  pelo desenvolvedor do zero.                                         "
echo "======================================================================"
