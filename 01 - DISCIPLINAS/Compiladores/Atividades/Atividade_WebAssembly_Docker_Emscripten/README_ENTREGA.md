# RELATORIO DE ATIVIDADE: COMPILANDO C++ PARA A WEB VIA WEBASSEMBLY (WASM)

- **Disciplina:** Compiladores (Turma N13208A) — 8º Semestre
- **Docente:** Prof. Luiz Claudio Chiavini Oliveira Junior
- **Aluno:** Matheus Sousa dos Santos (Taiwansz)
- **Data da Aula:** 2026-10-26
- **Data de Entrega Oficial:** 2026-11-03 (100 Pontos)

---

## 1. O Problema & O Desafio Arquitetural (Startup Lotus/EcoLaudo)
A startup de tecnologia agricola da universidade possui um algoritmo pesado de predicao de safra implementado em C++. A diretoria executiva demandou que esse algoritmo execute diretamente no navegador do cliente (SaaS), sem consumir ciclos de CPU dos servidores em nuvem e sem a necessidade de reescrever centenas de milhares de linhas para JavaScript.

### O Gargalo:
Instalar a cadeia de compilacao **Emscripten (baseada no backend do LLVM)** localmente no Windows/Mac dos desenvolvedores e um processo custoso, com conflitos de variaveis de ambiente e quebra de dependencias.

### A Solucao de Engenharia:
Utilizar o **Docker como Compilador Descartavel (*Ephemeral Compiler*)** atraves de um **Multi-stage Build**:
- **Estagio 1 (Builder):** Utiliza a imagem `emscripten/emsdk:latest` contendo a infraestrutura completa do LLVM para compilar `algoritmo.cpp` com `-O3` diretamente para o binario `index.wasm` acompanhado dos arquivos de ponte `index.js` e `index.html`.
- **Estagio 2 (Producao):** Utiliza um servidor web ultraleve (`nginx:alpine`). Apenas os artefatos compilados finais (`.html`, `.js`, `.wasm`) sao copiados. A imagem pesada do compilador e sumariamente descartada, gerando uma imagem final com menos de 25 MB.

---

## 2. Estrutura do Projeto

```
Atividade_WebAssembly_Docker_Emscripten/
|-- algoritmo.cpp        # Codigo-fonte C++ com saidas std::cout direcionadas a Web
|-- Dockerfile          # Multi-stage build (Emscripten LLVM -> Nginx Alpine)
`-- README_ENTREGA.md    # Guia tecnico de execucao e fundamentos de compilacao
```

---

## 3. Instrucoes de Execucao (Passo a Passo)

### Passo 1: Construir a Imagem Multi-stage
No terminal da sua maquina (com Docker Desktop ou no Linux com Docker instalado):
```bash
docker build -t meu-wasm .
```
*(Nota: O primeiro build baixa o SDK do Emscripten e realiza a compilacao via LLVM).*

### Passo 2: Executar o Servidor Web
```bash
docker run -d -p 8080:80 --name wasm-service meu-wasm
```

### Passo 3: Abrir o Navegador e Capturar o Print da Evidencia
1. Abra o navegador e acesse: `http://localhost:8080`
2. Pressione a tecla **F12** (ou clique com o botao direito e selecione **Inspecionar**).
3. Va para a aba **Console** (Developer Tools).
4. As seguintes mensagens do `std::cout` compilado em C++ estarao impressas no console:
   ```
   Sucesso! Codigo C++ rodando nativamente no Navegador via WebAssembly!
   O processamento pesado agora acontece na maquina do cliente, economizando nuvem.
   ```
5. Tire o print da tela inteira (mostrando a pagina e o console F12 com a mensagem) e anexe na atividade do Classroom.

---

## 4. Fundamentacao Teorica para Compiladores

```
+-------------------------------------------------------------------------------+
|                      PIPELINE LLVM -> WEBASSEMBLY (WASM)                      |
+-------------------------------------------------------------------------------+
| [ Codigo C++ ] ---> [ Clang Frontend ] ---> [ LLVM IR (SSA) ]                |
|                              |                                                |
|                              v                                                |
|                   [ Passes de Otimizacao ] (-O3, Inlining, DCE, Mem2Reg)     |
|                              |                                                |
|                              v                                                |
|                   [ LLVM Backend Wasm32 ]                                     |
|                              |                                                |
|                              v                                                |
|                   [ index.wasm (Stack Bytecode) ]                             |
+-------------------------------------------------------------------------------+
```

1. **A Quebra de Paradigma**: O código C++ nao foi "traduzido" para JavaScript. O backend do LLVM gerou **instrucoes em bytecode para uma maquina virtual de pilha (WebAssembly)**.
2. **Execucao Quase Nativa**: O motor V8 do navegador compila o `.wasm` para codigo de maquina nativo da CPU do cliente (x86-64 ou ARM64) logo na carga, executando com performance proxima a C++ nativo e memoria linear isolada.
3. **Economia de Nuvem (FinOps)**: Em vez de manter servidores caros rodando simulacoes pesadas na nuvem, o SaaS entrega a computacao na borda (Edge / Client-Side), derrubando a fatura de nuvem para custos minimos de entrega de arquivos estaticos via Nginx ou Bucket S3.
