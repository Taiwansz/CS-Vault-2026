#!/usr/bin/env bash
# ==============================================================================
# SCRIPT DE PROVISIONAMENTO AUTOMATIZADO PARA SERVIDOR LINUX CRU (IaaS)
# Disciplina: Compiladores / Infraestrutura em Nuvem
# Cenário: Servidor Linux/Ubuntu recém-criado sem ferramentas instaladas.
# Autor: Matheus Sousa dos Santos (Taiwansz)
# Uso: chmod +x provision.sh && sudo ./provision.sh
# ==============================================================================

set -euo pipefail

# 1. Verificacao de Privilegios Administrativos
if [[ $EUID -ne 0 ]]; then
   echo "[-] Este script deve ser executado com privilegios de superusuario (root ou sudo)."
   exit 1
fi

echo "======================================================================"
echo "  PROVISIONAMENTO IaaS: AMBIENTE DE PROCESSAMENTO PESADO EM C++      "
echo "======================================================================"

# 2. Desabilitar prompts interativos do gerenciador de pacotes
export DEBIAN_FRONTEND=noninteractive
export NEEDRESTART_MODE=a

# 3. Atualizacao dos Repositorios do Sistema Operacional
echo "[1/5] Atualizando repositorios de pacotes (APT)..."
apt-get update -y
apt-get upgrade -y

# 4. Instalacao da Cadeia de Compiladores (Toolchains GCC e LLVM/Clang)
echo "[2/5] Instalando toolchains de compilacao e bibliotecas de paralelismo..."
apt-get install -y \
    build-essential \
    g++ \
    clang \
    llvm \
    libomp-dev \
    cmake \
    make \
    htop \
    curl \
    git \
    tree

# 5. Telemetria do Hardware Provisionado
echo "[3/5] Telemetria de Hardware da Instancia IaaS:"
echo "--------------------------------------------------"
echo "CPU Modelo:      $(lscpu | grep 'Model name:' | sed 's/Model name:[ \t]*//')"
echo "Nucleos Fisicos: $(nproc --all)"
echo "Memoria Total:   $(free -h | awk '/^Mem:/ {print $2}')"
echo "Disco Disponivel:$(df -h / | awk 'NR==2 {print $4}')"
echo "--------------------------------------------------"

# 6. Compilacao do Codigo C++ (Fase de Sintese e Otimizacao)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SOURCE_FILE="${SCRIPT_DIR}/image_processor.cpp"

if [[ ! -f "$SOURCE_FILE" ]]; then
    echo "[-] Codigo fonte image_processor.cpp nao encontrado em ${SCRIPT_DIR}."
    echo "[!] Certifique-se de que o arquivo C++ foi transferido via SCP para a maquina."
    exit 1
fi

echo "[4/5] Executando compilacao otimizada em duas frentes (GCC vs. LLVM Clang)..."

# Compilacao via GCC com Otimizacao Maxima (-O3, vetorizacao nativa e OpenMP)
echo "  [*] Compilando com GCC (g++ -O3 -march=native -fopenmp)..."
g++ -O3 -march=native -fopenmp -std=c++17 "$SOURCE_FILE" -o "${SCRIPT_DIR}/image_processor_gcc"

# Compilacao via LLVM Clang com Link-Time Optimization (-flto)
echo "  [*] Compilando com LLVM (clang++ -O3 -march=native -flto -fopenmp)..."
clang++ -O3 -march=native -flto -fopenmp -std=c++17 "$SOURCE_FILE" -o "${SCRIPT_DIR}/image_processor_llvm"

echo "[+] Binarios compilados com sucesso:"
ls -lh "${SCRIPT_DIR}/image_processor_gcc" "${SCRIPT_DIR}/image_processor_llvm"

# 7. Execucao de Benchmark e Validacao do Servico
echo "[5/5] Executando validacao do algoritmo em processamento de alta resolucao..."
echo "--- EXECUCAO COM MOTOR LLVM / CLANG ---"
"${SCRIPT_DIR}/image_processor_llvm" 2048 2048

echo "======================================================================"
echo "  PROVISIONAMENTO CONCLUIDO COM EXITO!                                "
echo "  Binarios prontos em: ${SCRIPT_DIR}                                  "
echo "  Imagem processada gerada: output_sobel_processed.ppm                "
echo "======================================================================"
