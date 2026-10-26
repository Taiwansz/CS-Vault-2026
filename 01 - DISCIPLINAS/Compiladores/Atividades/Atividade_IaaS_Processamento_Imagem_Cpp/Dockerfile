# ==============================================================================
# DOCKERFILE: SERVICO C++ DE PROCESSAMENTO DE IMAGENS (UBUNTU LIMPO)
# Disciplina: Compiladores / Infraestrutura em Nuvem
# Cenário: Container Ubuntu limpo provisionado do zero pelo desenvolvedor
# Autor: Matheus Sousa dos Santos (Taiwansz)
# ==============================================================================

FROM ubuntu:22.04

# 1. Evitar prompts interativos
ENV DEBIAN_FRONTEND=noninteractive

# 2. Instalar compilador g++, bibliotecas OpenMP e utilitarios
RUN apt-get update && apt-get install -y \
    build-essential \
    g++ \
    libomp-dev \
    curl \
    net-tools \
    && rm -rf /var/lib/apt/lists/*

# 3. Diretorio de trabalho
WORKDIR /app

# 4. Copiar codigo fonte C++
COPY image_processor.cpp .

# 5. Compilar com g++ e otimizacoes de compilador (-O3, -fopenmp)
RUN g++ -O3 -fopenmp -std=c++17 image_processor.cpp -o image_service

# 6. Configuracao de Variaveis de Ambiente Obrigatorias
ENV PORT=8080
ENV NUM_THREADS=4
ENV BENCHMARK_RES=2048

# 7. Expor a porta do servico
EXPOSE 8080

# 8. Iniciar o servico na porta correta
CMD ["./image_service"]
