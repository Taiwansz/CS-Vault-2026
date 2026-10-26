---
tipo: aula
disciplina: Compiladores
data: 2026-10-26
professor: Luiz Claudio Chiavini Oliveira Junior
status: em_andamento
padrao_auditoria: atlas-deep-reporting-v1
tags:
  - compiladores
  - llvm
  - llvm-ir
  - webassembly
  - wasm
  - ssa
  - backend
  - intermediario
  - teoria-da-computacao
---

# CADERNO DE BANCADA: LLVM, REPRESENTACAO INTERMEDIARIA (IR) E WEBASSEMBLY

## 1. Metadados da Sessao
- **Disciplina:** Compiladores (Turma N13208A) — 8º Semestre
- **Docente:** Prof. Luiz Claudio Chiavini Oliveira Junior
- **Data/Hora:** 2026-10-26T19:25:00-03:00
- **Status:** Registro em Tempo Real (Live Notebook)
- **Topico de Abertura:** LLVM e WebAssembly (Lousa Inicial)

---

## 2. Contexto Epistemologico & Arquitetura de Tres Fases

A justaposicao de **LLVM** e **WebAssembly** na lousa sintetiza a solucao contemporanea para a geracao e otimizacao de codigo na fase de sintese de compiladores:

```
[ FRONTEND ]                  [ MIDDLE-END ]                  [ BACKEND ]
(Clang, Rustc, Swift)          (Passes de Otimizacao)         (Geradores de Codigo)
---------------------         ----------------------         ---------------------
Fonte (C, Rust, etc.)  --->   LLVM IR (Forma SSA)    --->    x86-64, AArch64, ARM
Parser + Analise Sem.         Inlining, Mem2Reg,             RISC-V
                              DCE, Loop Vectorize            WebAssembly (WASM)
```

### 2.1. O Problema $M \times N$ resolvido para $M + N$
- Em compiladores monoliticos tradicionais (modelo classico do GCC pre-v4): Para compilar $M$ linguagens para $N$ arquiteturas de hardware, seriam necessarios $M \times N$ compiladores independentes.
- A arquitetura LLVM desacopla essa matriz atraves de uma **Representacao Intermediaria (IR)** universal strongly typed em **Forma Estatica de Atribuicao Unica (SSA - Static Single Assignment)**:
  - $M$ Frontends convertem fonte para LLVM IR.
  - O Otimizador processa LLVM IR de forma agnostica.
  - $N$ Backends convertem LLVM IR para silicio alvo ou maquinas virtuais.
  - Custo de engenharia: Reduzido de $O(M \cdot N)$ para $O(M + N)$.

### 2.2. WebAssembly (Wasm) como Target Universal
- WebAssembly nao e uma linguagem de programacao de alto nivel; e um formato de instrucoes em bytecode para uma maquina virtual baseada em pilha (stack machine).
- O backend do LLVM trata WebAssembly (`wasm32` / `wasm64`) exatamente como trata uma arquitetura fisica (como x86 ou ARM), emitindo binarios estruturados `.wasm`.
- Vantagens estruturais: Seguranca de memoria linear isolada (sandboxed memory), portabilidade universal (browsers, runtimes WASI, edge computing) e execucao proxima ao nativo.

---

## 3. Registro Cronologico da Aula

### 3.1. [19:27] Provocacao do Docente: Vantagens de Trabalhar com Rotas em JavaScript

> **Questionamento em Sala:** *"Em JavaScript, qual a vantagem de trabalhar com rotas?"*

#### Analise sob a Otica de Arquitetura de Software e Compilacao:

Se o docente estiver conectando o ecossistema Web (JS) ao WebAssembly (Wasm):
O JavaScript atua como a **camada de orquestracao e ciclo de vida**, enquanto o Wasm atua como o motor de execucao pesado. O roteamento e o mecanismo central que organiza o carregamento sob demanda dessas pecas.

#### Vantagens Estruturais do Roteamento em JavaScript:

1. **Client-Side Routing & SPA (Single Page Application)**:
   - **Persistencia de Estado e Zero Recarregamento de Pagina**: Evita o ciclo destrutivo de recarregar o documento HTML inteiro a cada transicao. A History API (`pushState`, `popstate`) sincroniza a URL do navegador com a arvore de componentes em memoria sem reinicializar o runtime JS ou o motor V8.
   - **Deep Linking e Determinismo de Estado**: Permite que uma aplicacao com estado complexo em memoria continue indexavel e compartilhavel atraves de URIs semanticas (`/dashboard/relatorio?id=42`).
   - **Code Splitting & Dynamic Imports (Compilacao/Empacotamento Modular)**: Roteadores modernos permitem que o compilador/bundler fatiar a aplicacao em *chunks*. Ao acessar a rota `/analise-pesada`, o JavaScript carrega e instancia o modulo compilado (inclusive o binario `.wasm`) estritamente sob demanda (*lazy loading*), poupando memoria e tempo de parse.

### 3.2. [19:29] O Caso Forense da Semana Passada: PHP Quebrado ("PHP2Shell" - Outubro/2026)

> **Contexto da Lousa:** Chiavini invocou o colapso de seguranca do interpretador PHP ocorrido na semana passada para contrapor a fragilidade de runtimes legados em C com a seguranca estrutural de **LLVM e WebAssembly**.

#### A Anatomia da Quebra do PHP:
Na terceira semana de Outubro de 2026, foram divulgadas e exploradas massivamente as vulnerabilidades criticas batizadas de **"PHP2Shell"** (CVE-2026-109618 e CVE-2026-109619, CVSS 9.2):
1. **Falha de Gerenciamento de Memoria no Interpretador (Zend Engine)**:
   - Em condicoes de esgotamento de memoria (*Out-Of-Memory - OOM*), o interpretador PHP abortava a execucao utilizando `longjmp()` em C.
   - O `longjmp()` desenrolava a pilha de forma abrupta, deixando estruturas internas e globais parcialmente inicializadas.
   - Na rotina de desalocacao (*cleanup*), dados corrompidos eram tratados como validos, gerando **Use-After-Free (UAF)** e corrupcao de heap no processo worker (PHP-FPM / `mod_php`).
2. **Impacto:** Remote Code Execution (RCE) nao autenticado disparado por uma unica requisicao HTTP especialmente forjada — executando shellcode no servidor **antes mesmo do script PHP do usuario iniciar sua execucao**.

#### A Conexao com Compiladores: Por que LLVM e WebAssembly sao a Resposta?

| Vetor Arquitetural | Runtime Legado / Interpretado (PHP / Zend Engine em C) | Modelo Moderno: LLVM + WebAssembly (Wasm) |
| :--- | :--- | :--- |
| **Espaco de Enderecamento** | Compartilhado diretamente com o processo do SO. Corrupcao de heap vira RCE nativo. | **Memoria Linear Isolada (*Sandboxed Linear Memory*)**. O bytecode Wasm so enxerga seu buffer interno. |
| **Fluxo de Controle** | Saltos arbitrarios, ponteiros de funcao e `longjmp()` frageis em C. | **Structured Control Flow**. Proibicao de saltos para enderecos arbitrarios fora da tabela tipada. |
| **Fase de Compilacao** | Interpretacao sob demanda de opcodes com extensões nativas em C inseguro. | Compilacao antecipada (AOT via LLVM) ou JIT validado formalmente antes da execucao. |
### 3.3. [19:33] Python no Backend de IA: O Paradigma da Cola de Orquestracao e a Arquitetura em Camadas

> **Questionamento do Docente:** *"E bom usar Python no backend? Ele usa o principio de fazer diversas camadas para conseguir executar para nos."*
> **Diretiva Pratica da Aula:** *"Boa parte das ferramentas que utilizamos, instalamos um plugin no sistema, passamos o link do repositorio Git da biblioteca e o sistema consome/expõe via API."*

#### Dissecacao do Paradigma: Python e Realmente Bom no Backend de IA?

A resposta formal sob a otica de compiladores e engenharia de sistemas e: **Python e um pessimo motor de computacao, mas e o melhor orquestrador de camadas (*Glue Code*) do mundo.**

```
+-------------------------------------------------------------------------------+
|                       PILHA DE EXECUCAO EM SISTEMAS DE IA                     |
+-------------------------------------------------------------------------------+
| CAMADA 4 (Alto Nivel)  | Python (Ergonomia: PyTorch, HuggingFace, FastAPI)   |
|                        | Interpretador CPython (Bytecode + GIL)               |
+------------------------+------------------------------------------------------+
| CAMADA 3 (Integracao)  | C-Bindings / FFI (pybind11, Cython, CFFI)            |
+------------------------+------------------------------------------------------+
| CAMADA 2 (Compilacao)  | Motores Intermediarios: MLIR, LLVM IR, Triton, XLA   |
|                        | Otimizacoes de Grafo, Fusao de Kernels, SSA          |
+------------------------+------------------------------------------------------+
| CAMADA 1 (Silicio Puro)| Binarios Nativos em C/C++, CUDA, cuDNN, PTX, AVX-512 |
+------------------------+------------------------------------------------------+
```

1. **A Ilusao da Execucao em Python**:
   - Se o backend de IA executasse tensores em lacos nativos de Python (`for x in array`), o throughput seria degradado em $100\times$ a $1000\times$ devido ao *Global Interpreter Lock (GIL)*, sobrecarga de boxing/unboxing dinamico de tipos e interpretacao sequencial de bytecode.
   - O Python atua estritamente como um **despachante de comandos (frontend)**. Quando o desenvolvedor escreve `model(inputs)`, o Python apenas monta o grafo e transfere o controle para o backend compilado em C++/CUDA otimizado via LLVM/Triton.

2. **Dinamica de Plugins e Consumo via Repositorios Git + API**:
   - **Desacoplamento por Contrato de API**: Em vez de recompilar um monolito a cada nova funcionalidade, os sistemas modernos utilizam plugins carregados dinamicamente via Git URLs (`pip install git+https://...` ou clone sob demanda) e expostos como servicos efemeros via API REST/gRPC.
### 3.4. [19:42] Transicao para Computacao em Nuvem & A Nova Fronteira do Edge/Serverless

> **Definicao Registrada em Sala:** *"Computacao em nuvem e a entrega de servicos de computacao por meio da internet, possibilitando uma inovacao mais rapida, recursos flexiveis e economia de escala."*

#### Analise Critica e Conexao com Compiladores (LLVM & Wasm):

Esta definicao textual reproduz o padrao do NIST e dos provedores de nuvem (AWS/Azure/GCP). No contexto estrito de **Compiladores e WebAssembly**, a nuvem contemporanea vive uma mudanca de paradigma:

1. **A Crise dos Containers na Nuvem Tradicional**:
   - O modelo IaaS/PaaS baseia-se em conteinerizacao (Docker/Kubernetes).
   - Um container carrega um sistema de arquivos completo, bibliotecas compartilhadas e runtime do SO, pesando centenas de megabytes com *cold starts* de centenas de milissegundos a segundos.

2. **A Revolucao do WebAssembly na Nuvem (FaaS / Edge Computing)**:
   - Plataformas como Cloudflare Workers, Fastly Compute@Edge e AWS Lambda Wasm usam o binario `.wasm` gerado pelo **LLVM** como unidade de computacao em nuvem.
   - **Vantagens de Escala e Flexibilidade**:
     - *Cold start* inferior a $1\text{ ms}$ (inicializacao quase instantanea de isolados V8 / Wasmtime).
     - Consumo de memoria na ordem de kilobytes (nao gigabytes).
     - Seguranca intrinseca atraves de memoria linear isolada sem necessitar de isolamento via kernel namespaces ou cgroups do Linux.
### 3.5. [19:43] Desafio Conceitual: "Se eu uso container, eu uso sistema operacional?"

> **Questionamento em Sala:** *"Se eu uso container, eu uso sistema operacional?"*

#### A Resposta Forense de Engenharia de Sistemas:

**SIM, mas voce NAO roda um novo kernel de sistema operacional; voce COMPARTILHA o kernel do sistema operacional hospedeiro (*Host OS*).**

```
+------------------------------------+    +------------------------------------+
|        MAQUINA VIRTUAL (VM)        |    |          CONTAINER (DOCKER)        |
+------------------------------------+    +------------------------------------+
| App A         | App B              |    | App A         | App B              |
| Libs / Bin    | Libs / Bin         |    | Libs / Bin    | Libs / Bin (User)  |
| Guest OS (Kernel Proprio)          |    | (Sem Kernel Proprio)               |
+------------------------------------+    +------------------------------------+
| Hypervisor (KVM, ESXi, Type 1/2)   |    | Container Engine (Docker / containerd)
+------------------------------------+    +------------------------------------+
| Host OS (Kernel do Hospedeiro)     |    | Host OS (Kernel Linux Compartilhado)|
+------------------------------------+    +------------------------------------+
| Silicio / Hardware Fisico          |    | Silicio / Hardware Fisico          |
+------------------------------------+    +------------------------------------+
```

#### Dissecacao em Duas Camadas:

1. **Camada de Kernel (Nucleo do SO)**:
   - O container **NAO** possui um kernel proprio.
   - Um container e simplesmente um processo regular no Linux isolado por recursos nativos do kernel hospedeiro:
     - **Namespaces**: Isolamento visual (o processo acha que e o PID 1, enxerga apenas sua propria rede e sistema de arquivos montado).
     - **cgroups (*Control Groups*)**: Limitacao e medicao de recursos fisicos (teto de CPU, memoria RAM, I/O de disco).
     - **seccomp / AppArmor**: Filtragem e restricao de chamadas de sistema (*syscalls*).
   - Toda chamada de sistema (`syscall`) executada dentro do container e atendida diretamente pelo **kernel do Host**.

2. **Camada de Userspace (Espaco do Usuario)**:
   - O container carrega um **Rootfs (*Root Filesystem*)** proprio: bibliotecas compartilhadas (`glibc` ou `musl`), binarios de utilitarios (`ls`, `cat`, interpretadores) e arquivos de configuracao correspondentes a distribuicao alvo (Ubuntu, Alpine, Debian).
   - Portanto: voce usa o *userspace* empacotado na imagem, mas o *kernel* e 100% compartilhado com a maquina hospedeira.

#### O Elo Final com a Abertura da Aula (WebAssembly vs. Containers):
- No container: A superficie de ataque ainda e o kernel do Host (se houver vulnerabilidade de *kernel privilege escalation*, ha fuga de container).
- No **WebAssembly (Wasm + WASI)**: Nao ha *userspace* Linux, nao ha `/bin/sh`, nao ha dependencias de `glibc` e nao ha acesso a *syscalls* brutas do kernel. O binario compilado via LLVM executa em uma sandbox hermetica com seguranca baseada em capacidades (*capability-based security*).





