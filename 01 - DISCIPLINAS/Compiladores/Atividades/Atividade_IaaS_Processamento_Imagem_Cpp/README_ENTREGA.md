# RELATORIO DE ENTREGA: PROCESSAMENTO DE IMAGENS EM C++ & PROVISIONAMENTO IaaS

- **Disciplina:** Compiladores / Infraestrutura em Nuvem
- **Docente:** Prof. Luiz Claudio Chiavini Oliveira Junior
- **Aluno:** Matheus Sousa dos Santos (Taiwansz)
- **Data:** 2026-10-26

---

## 1. O Problema & O Cenário Operacional
Uma equipe de operacoes disponibilizou apenas o acesso SSH a um servidor Linux Ubuntu limpo (IaaS puro). A missao consiste em:
1. Desenvolver um algoritmo pesado de processamento de imagens em C++.
2. Criar um script de provisionamento automatizado (`provision.sh`) capaz de transformar a maquina crua em um ambiente de execucao de alta performance, instalando toolchains, compilando o codigo com otimizacoes de compilador e executando o processamento.

---

## 2. Estrutura dos Arquivos Entregues

```
Atividade_IaaS_Processamento_Imagem_Cpp/
|-- image_processor.cpp   # Motor C++ com convolucao 2D de Sobel e paralelismo OpenMP
|-- provision.sh         # Script Bash automatizado e idempotente de provisionamento
`-- README_ENTREGA.md     # Documentacao tecnica de entrega e guia de operacao
```

---

## 3. Guia de Execucao Paso a Passo (No Terminal da Maquina)

### Passo 1: Transferir os arquivos para a VM via SSH/SCP
A partir da sua maquina local:
```bash
scp image_processor.cpp provision.sh usuario@ip-da-vm:~/
```

### Passo 2: Acessar a Maquina via SSH
```bash
ssh usuario@ip-da-vm
```

### Passo 3: Conceder Permissao de Execucao e Rodar o Provisionamento
```bash
chmod +x provision.sh
sudo ./provision.sh
```

O script ira automaticamente:
1. Atualizar a arvore de pacotes APT sem travar em prompts interativos (`DEBIAN_FRONTEND=noninteractive`).
2. Instalar as cadeias de compiladores: `build-essential`, `g++`, `clang`, `llvm`, `libomp-dev`, `cmake`, `htop`.
3. Coletar a telemetria do hardware (modelo de CPU, nucleos e memoria disponivel).
4. Compilar o codigo-fonte em duas frentes com otimizacoes agressivas:
   - **GCC:** `g++ -O3 -march=native -fopenmp -std=c++17 image_processor.cpp -o image_processor_gcc`
   - **LLVM Clang:** `clang++ -O3 -march=native -flto -fopenmp -std=c++17 image_processor.cpp -o image_processor_llvm`
5. Executar o benchmark imediato com geracao de imagem convolucionada de alta resolucao (`output_sobel_processed.ppm`).

---

## 4. Analise sob a Otica de Compiladores

### Otimizacoes de Compilacao Aplicadas:
- `-O3`: Habilita otimizacoes agressivas de loop unrolling, vetorizacao automatica de instrucoes, propagacao de constantes e eliminacao de codigo morto.
- `-march=native`: Instrui o backend do compilador a emitir instrucoes vetoriais especificas do processador do servidor (AVX2, AVX-512, FMA), eliminando overhead de instrucoes genericas.
- `-flto` (Link-Time Optimization via LLVM): Permite que o middle-end do LLVM analise o grafo de chamada global e realize *inlining* e otimizacoes inter-modulares durante a fase de linkagem.
- `-fopenmp`: Despacha as iteracoes da convolucao 2D de Sobel entre todos os nucleos fisicos da maquina virtual IaaS.
