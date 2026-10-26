# RELATORIO DE ENTREGA: PROCESSAMENTO DE IMAGENS EM C++ & PROVISIONAMENTO IaaS

- **Disciplina:** Compiladores / Infraestrutura em Nuvem
- **Docente:** Prof. Luiz Claudio Chiavini Oliveira Junior
- **Aluno:** Matheus Sousa dos Santos (Taiwansz)
- **Data:** 2026-10-26

---

## 1. O Problema & O Cenário Operacional
> *"Uma empresa possui um algoritmo pesado de processamento de imagens escrito em C++ que precisa rodar em um servidor Linux cru. A equipe de operacoes forneceu apenas o acesso via SSH a uma maquina virtual recem-criada (ou um container Ubuntu limpo).*
> *Objetivo: Desenvolver o codigo C++ e criar um script de automacao (Shell Script ou Dockerfile) que prepare toda a infraestrutura do zero. O script deve instalar o compilador (g++), configurar variaveis de ambiente, compilar o codigo fonte e iniciar o servico na porta correta, provando que no IaaS a responsabilidade pelo ambiente e totalmente do desenvolvedor."*

---

## 2. Estrutura dos Arquivos Entregues

```
Atividade_IaaS_Processamento_Imagem_Cpp/
|-- image_processor.cpp   # Servico C++ (socket nativo HTTP, convolucao Sobel e OpenMP)
|-- provision.sh         # Script Shell de automacao e provisionamento de VM/servidor cru
|-- Dockerfile           # Automacao alternativa para container Ubuntu limpo
`-- README_ENTREGA.md     # Relatorio tecnico de entrega e instrucoes de execucao
```

---

## 3. A Prova do Modelo IaaS: O Que o Desenvolvedor Teve que Construir

No modelo **IaaS (Infrastructure as a Service)**, o provedor de nuvem entrega apenas o hardware virtualizado. O desenvolvedor e o unico responsavel por erguer a pilha inteira:

1. **Instalacao do Compilador:** O servidor veio sem compilador; o script instalou o `g++` e as bibliotecas do OpenMP (`libomp-dev`).
2. **Configuracao de Variaveis de Ambiente:** O servico le `$PORT`, `$NUM_THREADS` e `$BENCHMARK_RES` via `std::getenv()`, configuradas no script e persistidas em `/etc/profile.d/`.
3. **Compilacao Otimizada:** O codigo e compilado com `g++ -O3 -march=native -fopenmp -std=c++17` gerando o executavel `image_service`.
4. **Inicializacao como Servico de Rede:** O script cria um daemon no **systemd** (`/etc/systemd/system/image-processor.service`), inicia o servico e abre a porta no firewall (`ufw allow 8080/tcp`).
5. **Validacao de Escuta na Porta:** O servico sobe ouvindo na porta `8080` e responde imediatamente a chamadas `GET /health` e `POST /process`.

---

## 4. Guia de Execucao Paso a Paso

### Opcao A: Execucao Direta na Maquina Virtual (Via Shell Script)
```bash
# 1. Enviar os arquivos para a VM via SCP
scp image_processor.cpp provision.sh usuario@ip-da-vm:~/

# 2. Conectar na VM e rodar o script com privilegios de superusuario
ssh usuario@ip-da-vm
chmod +x provision.sh
sudo ./provision.sh
```

### Opcao B: Execucao em Container Ubuntu Limpo (Via Docker)
```bash
# 1. Construir a imagem a partir do Ubuntu 22.04 cru
docker build -t image-processor-iaas .

# 2. Executar o container mapeando a porta 8080
docker run -d -p 8080:8080 --name image-service image-processor-iaas

# 3. Testar a API
curl http://localhost:8080/health
curl -X POST http://localhost:8080/process
```

---

## 5. Endpoints Disponiveis no Servico

| Metodo | Endpoint | Descricao | Exemplo de Resposta |
|---|---|---|---|
| `GET` | `/health` | Healthcheck e telemetria das variaveis de ambiente | `{"status": "healthy", "port": 8080, "threads": 4, "model": "IaaS"}` |
| `POST` | `/process` | Dispara convolucao 2D de Sobel sobre matriz sintetica | `{"status": "completed", "resolution": "2048x2048", "time_ms": 142.5}` |
