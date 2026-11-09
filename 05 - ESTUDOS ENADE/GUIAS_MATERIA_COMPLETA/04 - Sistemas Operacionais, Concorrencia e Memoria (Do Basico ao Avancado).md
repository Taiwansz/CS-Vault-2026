---
tipo: guia-enade
disciplina: "Sistemas Operacionais e Concorrencia"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

> [!NOTE] Navegacao de Estudos ENADE
> **Central:** [[00 - Dashboard]] | **Hub Mestre ENADE:** [[INDEX_ESTUDOS_ENADE]]
> **Modulo Anterior:** [[03 - Teoria da Computacao, Automatos e Compiladores (Do Basico ao Avancado)]]
> **Proximo Modulo:** [[05 - Banco de Dados, SQL Avancado e Modelagem (Do Basico ao Avancado)]]

# 04 — Sistemas Operacionais, Concorrencia e Memoria (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Este modulo aborda os pilares da interacao entre software e hardware: o gerenciamento de processos e threads, primitivas de sincronizacao e deadlocks, paginacao de memoria virtual e a arquitetura moderna de execucao e memoria na JVM HotSpot.

---

## CAMADA 1: O BASICO (PROCESSOS, THREADS E MODOS DE EXECUCAO)

O Sistema Operacional e o arbitro de hardware. Ele impede que aplicacoes de usuario interfiram umas nas outras ou travem a maquina fisica.

- **Dual-Mode Operation (Modo Kernel vs. Modo Usuario):**
 - *Modo Usuario (Ring 3):* Codigo de aplicacao comum. Nao pode acessar a memoria fisica diretamente nem emitir instrucoes privilegiadas de CPU.
 - *Modo Kernel (Ring 0):* Privilegio maximo. Acesso irrestrito a hardware, portas de I/O e registradores de controle.
 - *System Call (Chamada de Sistema):* A ponte segura para transitar de Modo Usuario para Modo Kernel (ex: `read()`, `write()`, `fork()`, `socket()`).

### Processo vs. Thread:
- **Processo:** Uma instancia de programa em execucao com seu **espaco de memoria virtual proprio e isolado**, tabela de descritores de arquivos, PCB (Process Control Block) e variaveis de ambiente. Dois processos nao compartilham memoria a menos que usem mecanismos explicitos de IPC (Shared Memory, Sockets, Pipes).
- **Thread (Linha de Execucao / Processo Leve):** Uma unidade de escalonamento dentro de um processo.
 - *Compartilha:* A memoria Heap, o codigo do programa (`.text`), variaveis globais e arquivos abertos.
 - *Possui Privativo:* Sua propria **Pilha (Stack)**, ponteiro de instrucao (PC) e registradores de CPU.

---

## CAMADA 2: MODELOS DE CONCORRENCIA E JAVA 21 (VIRTUAL THREADS)

Historicamente, as linguagens implementavam concorrência em dois extremos:

| Modelo | Mapeamento | Caracteristicas | Exemplo |
| :--- | :---: | :--- | :--- |
| **Platform Threads (Classico)** | **1:1** | Cada thread da linguagem e uma thread real gerenciada pelo kernel do SO. Criar dezenas de milhares esgota a RAM (cada uma gasta ~1MB de stack) e sobrecarrega a CPU com troca de contexto no kernel (*context switch*). | Java ate a versao 20, C++ `std::thread`, POSIX Threads. |
| **Virtual Threads (Loom)** | **M:N** | Milhoes de threads virtuais ultra-leves gerenciadas pela JVM no espaco de usuario (Heap), mapeadas sobre um pequeno pool de threads reais do SO (**Carrier Threads**). | Java 21+ (Virtual Threads), Go (Goroutines), Elixir (BEAM). |

### O Fenomeno do Thread Pinning (Fixacao de Thread):
- Ocorre quando uma Virtual Thread executa uma operacao de I/O bloqueante (consulta a banco de dados ou chamada de rede) dentro de um bloco ou metodo **`synchronized`** (ou ao chamar codigo nativo em C via JNI).
- **O Desastre na Escalabilidade:** A JVM nao consegue desacoplar (*unmount*) a Virtual Thread da Carrier Thread real. Como o pool de Carrier Threads possui tipicamente o mesmo numero de nucleos da CPU (ex: 8 ou 16), poucas chamadas lentas travam todas as threads reais da maquina, congelando a aplicacao inteira.
- **A Solucao:** Substituir `synchronized` por **`java.util.concurrent.locks.ReentrantLock`**.

---

## CAMADA 3: SINCRONIZACAO, CONDICOES DE CORRIDA E DEADLOCKS

Quando multiplas threads acessam e alteram uma variavel compartilhada simultaneamente, ocorre uma **Condicao de Corrida (Race Condition)**. A regiao de codigo que acessa esse recurso e a **Secao Critica**.

### As 4 Condicoes Invariantes de Coffman para DEADLOCK:
Um impasse mortal (Deadlock) so pode ocorrer se **TODAS as quatro condicoes a seguir forem verdadeiras simultaneamente**:

1. **Exclusao Mutua (Mutual Exclusion):** Pelo menos um recurso deve ser mantido de forma nao-compartilhavel (apenas uma thread por vez).
2. **Posse e Espera (Hold and Wait):** Uma thread que ja detem recursos esta bloqueada esperando por recursos adicionais mantidos por outras threads.
3. **Nao-Preempcao (No Preemption):** Recursos nao podem ser retirados a forca de uma thread; eles so sao liberados voluntariamente apos o termino da tarefa.
4. **Espera Circular (Circular Wait):** Existe um conjunto fechado de threads $\{T_0, T_1, \dots, T_n\}$ tal que $T_0$ espera por recurso mantido por $T_1$, $T_1$ espera por $T_2$, e $T_n$ espera por $T_0$.

> [!TIP]
> **Como quebrar o Deadlock no ENADE?** 
> Para prevenir deadlocks, basta quebrar **qualquer uma** das 4 condicoes. A estrategia mais comum na pratica e **quebrar a Espera Circular**, impondo uma ordenacao global estrita de aquisicao de recursos (todas as threads devem requisitar o Lock A antes do Lock B).

---

## CAMADA 4: GERENCIAMENTO DE MEMORIA VIRTUAL E PAGINACAO

A memoria virtual permite que programas executem utilizando mais memoria do que a RAM fisica existente, alem de fornecer protecao e isolamento completo.

- **Paginas (Pages):** Blocos de tamanho fixo na memoria logica do programa (tipicamente 4 KB).
- **Molduras de Pagina (Frames):** Blocos de tamanho fixo na memoria fisica (RAM).
- **MMU (Memory Management Unit):** Hardware que traduz enderecos virtuais em fisicos consultando a **Tabela de Paginas**.
- **TLB (Translation Lookaside Buffer):** Cache de altissima velocidade na CPU que guarda as ultimas traducoes de enderecos virtuais. Um *TLB Miss* forca a consulta lenta a tabela na RAM.

### Algoritmos de Substituicao de Paginas:
Quando a RAM esta cheia e ocorre uma Falha de Pagina (*Page Fault*), o SO precisa escolher uma pagina para gravar no disco de swap e liberar espaco:

1. **FIFO (First-In, First-Out):** Substitui a pagina mais antiga. 
 *A Grande Armadilha do ENADE:* O FIFO sofre da **Anomalia de Belády** — em certas sequencias de acesso, **aumentar o numero de molduras de memoria RAM disponiveis AUMENTA o numero de falhas de pagina**, em vez de diminuir!
2. **LRU (Least Recently Used):** Substitui a pagina que nao e acessada ha mais tempo. Excelente desempenho pratico, baseado no principio da localidade temporal. Nao sofre da anomalia de Belády.
3. **Algoritmo Otimo de Belády (OPT):** Substitui a pagina que nao sera usada pelo maior periodo de tempo no futuro. E impossivel de implementar na pratica (exige videncia do futuro), servindo como padrao-ouro teorico de benchmark.
4. **Thrashing (Hiperpaginacao):** Quando o sistema passa quase 100% do tempo de CPU fazendo trocas de paginas entre a RAM e o disco (swap) porque a soma dos conjuntos de trabalho (*working sets*) dos processos e maior do que a RAM fisica disponivel.

---

## CAMADA 5: ARQUITETURA DE MEMORIA DA JVM (HEAP VS. METASPACE)

A partir do Java 8, a JVM HotSpot aboliu o antigo PermGen e reestruturou a memoria:

- **Stack (Pilha de Execucao):** Armazena chamadas de metodos, variaveis locais primitivas e referencias para o Heap. E alocada individualmente para cada thread.
- **Heap (Monte):** Armazena todos os objetos instanciados com `new` e arrays. Compartilhado entre todas as threads e gerenciado pelo Garbage Collector.
- **Metaspace:** Armazena metadados de classes (tabelas de metodos, constantes, bytecode). 
 *Diferenca Critica:* O Metaspace **nao reside no Heap**; ele aloca memoria nativa diretamente do Sistema Operacional.
 - Se o parametro `-XX:MaxMetaspaceSize` nao for definido, o Metaspace pode crescer ate devorar toda a RAM fisica da maquina servidora.
 - Aplicacoes que geram classes dinamicamente em runtime (proxies Spring, CGLIB, ByteBuddy, ClassLoaders com vazamento) podem disparar `java.lang.OutOfMemoryError: Metaspace`.

---

## CAMADA 6: ARMADILHAS CLASSICAS DO ENADE

1. **Anomalia de Belády:** Perguntas afirmando que "adicionar memoria RAM sempre reduz page faults" sao **FALSAS** se o algoritmo de substituicao for o FIFO.
2. **Fragmentacao Interna vs. Externa:**
 - *Paginacao:* Elimina fragmentacao externa (pois qualquer pagina cabe em qualquer moldura), mas gera **Fragmentacao Interna** (o ultimo bloco de 4 KB de um processo pode usar apenas 100 bytes, desperdicando o restante).
 - *Segmentacao:* Trabalha com blocos de tamanho variavel; elimina fragmentacao interna, mas gera **Fragmentacao Externa** (buracos dispersos na memoria).
3. **Colecoes Concorrentes:** `Collections.synchronizedMap` usa um unico lock global no mapa todo (gargalo total). O `ConcurrentHashMap` usa locks granulares em buckets combinados com operacoes atomicas CAS (*Compare-And-Swap*), permitindo leituras sem lock e gravacoes simultaneas em baldes diferentes.

---

## Conexoes no Cofre e Disciplinas Praticas

- **Hub Geral:** [[INDEX_ESTUDOS_ENADE]] | [[00 - Dashboard]]
- **Simulado de Referencia:** [[Simulado 04 - Ciencia da Computacao Completo 20 Questoes 2026-11-09]]
- **Disciplinas Praticas:** [[Tópicos Especiais II]], [[Estudos Avançados em Ciências da Computação]]
