# ==============================================================================
# ATIVIDADE: COMPILANDO C++ PARA A WEB (WEBASSEMBLY COM DOCKER MULTI-STAGE)
# Disciplina: Compiladores - Prof. Luiz Claudio Chiavini Oliveira Junior
# Aluno: Matheus Sousa dos Santos (Taiwansz)
# Data de Entrega: 03/11/2026
# ==============================================================================

# ==========================================
# ESTAGIO 1: O Compilador WebAssembly (LLVM)
# ==========================================
# Baixamos a imagem oficial do Emscripten (ambiente completo com LLVM/Clang)
FROM emscripten/emsdk:latest AS builder

WORKDIR /app
COPY algoritmo.cpp .

# O Back-end do LLVM entra em acao: compila C++ para .wasm e gera o HTML/JS de ponte
# -O3 ativa otimizacao maxima de performance (Dead Code Elimination, Constant Folding)
RUN emcc algoritmo.cpp -O3 -o index.html

# ==========================================
# ESTAGIO 2: O Servidor Web (Producao)
# ==========================================
# Usamos o Nginx minimalista para servir arquivos estaticos
FROM nginx:alpine

WORKDIR /usr/share/nginx/html

# Limpamos a pagina padrao do Nginx
RUN rm -rf ./*

# Copiamos apenas os artefatos compilados do estagio 1 (html, js, wasm)
# O compilador gigante do Emscripten fica para tras e e descartado
COPY --from=builder /app/index.html .
COPY --from=builder /app/index.js .
COPY --from=builder /app/index.wasm .

# Expoe a porta web padrao
EXPOSE 80
