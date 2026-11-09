---
tipo: guia-enade
disciplina: "Teoria da Computacao e Compiladores"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

# 03 — Teoria da Computacao, Automatos e Compiladores (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Este documento consolida as bases formais da Ciencia da Computacao: a Hierarquia de Chomsky, os limites matematicos do que e computavel (Decidibilidade e Maquinas de Turing) e a engenharia de compiladores que traduz especificacoes abstratas em binarios de execucao.

---

## CAMADA 1: O BASICO (LINGUAGENS, GRAMATICAS E RECONHECEDORES)

Na ciencia da computacao, qualquer programa de computador ou comando e uma **cadeia de simbolos (palavra)** pertencente a um **alfabeto $\Sigma$**.
- Um conjunto de palavras validas e uma **Linguagem $L$**.
- Uma **Gramatica** e um conjunto de regras de producao que geram as palavras da linguagem.
- Um **Automato** e uma maquina abstrata que le uma cadeia e decide se ela pertence ou nao a linguagem (Aceita ou Rejeita).

---

## CAMADA 2: A HIERARQUIA DE CHOMSKY

Noam Chomsky classificou as linguagens formais em 4 niveis estritos de poder expressivo:

```text
+--------------------------------------------------------------+
| Tipo 0: Linguagens Recursivamente Enumeraveis (Turing Machine)|
|   +------------------------------------------------------+   |
|   | Tipo 1: Linguagens Sensiveis ao Contexto (LBA)        |   |
|   |   +----------------------------------------------+   |   |
|   |   | Tipo 2: Linguagens Livres de Contexto (PDA)  |   |   |
|   |   |   +--------------------------------------+   |   |   |
|   |   |   | Tipo 3: Linguagens Regulares (DFA/NFA)|   |   |   |
+---+---+---+---+--------------------------------------+---+---+---+
```

### Detalhamento dos 4 Niveis:

| Nivel | Tipo de Gramatica | Forma das Regras de Producao | Reconhecedor Abstrato | Memoria Auxiliar |
| :---: | :--- | :--- | :--- | :--- |
| **Tipo 3** | **Linguagens Regulares** | $A \to aB$ ou $A \to a$ (Linear a direita) | Automato Finito (DFA / NFA) | **Nenhuma** (Apenas o estado atual) |
| **Tipo 2** | **Livres de Contexto (LLC)** | $A \to \alpha$ (Lado esquerdo e um unico nao-terminal) | Automato com Pilha (PDA) | **Uma Pilha (LIFO)** (Contagem simples e aninhamento) |
| **Tipo 1** | **Sensiveis ao Contexto** | $\alpha A \beta \to \alpha \gamma \beta$ ($|\text{saida}| \ge |\text{entrada}|$) | Automato Linearmente Limitado (LBA) | Fita finita limitada pelo tamanho da entrada |
| **Tipo 0** | **Recursivamente Enumeraveis** | $\alpha \to \beta$ (Sem restricoes formais) | **Maquina de Turing (TM)** | **Fita infinita com leitura e escrita bidirecional** |

### O Lema do Bombeamento (Pumping Lemma):
- **Para Linguagens Regulares:** Se uma linguagem e regular, qualquer cadeia suficientemente longa pode ser fatiada em $w = xyz$ tal que $xy^i z$ tambem pertence a linguagem para todo $i \ge 0$.
- **Classico do ENADE:** A linguagem $L = \{a^n b^n \mid n \ge 0\}$ **NAO e regular**. Um automato finito nao tem memoria para "lembrar" quantos $a$'s foram lidos para conferir com os $b$'s. Ela e Livre de Contexto (Tipo 2), pois a pilha consegue empilhar os $a$'s e desempilhar nos $b$'s.
- Ja a linguagem $L = \{a^n b^n c^n \mid n \ge 0\}$ **NAO e Livre de Contexto**. Uma unica pilha nao consegue conferir tres contagens sincronizadas. Ela e Sensivel ao Contexto (Tipo 1).

---

## CAMADA 3: DECIDIBILIDADE E O PROBLEMA DA PARADA

### 3.1 Maquinas de Turing e a Tese de Church-Turing
A Maquina de Turing formaliza matematicamente o conceito de "algoritmo". Qualquer computacao que um supercomputador moderno em 2026 consegue executar pode ser simulada por uma Maquina de Turing.

### 3.2 O Problema da Parada (Halting Problem — Alan Turing, 1936)
- **A Pergunta:** Existe um algoritmo universal $H$ que consiga ler o codigo de qualquer programa $P$ e sua entrada $I$, e dizer com 100% de certeza se $P(I)$ vai terminar (parar) ou entrar em loop infinito?
- **A Resposta Matematica:** **NAO**. O problema da parada e **INDECIDIVEL** (impossivel de ser resolvido algorithmicament no caso geral).
- **A Prova por Diagonalizacao (Contradicao):**  
  Suponha que exista tal programa $H(P, I)$. Construimos uma maquina reversa $D(P)$ que consulta $H(P, P)$. Se $H$ disser que $P(P)$ para, $D$ entra em loop infinito de proposito. Se $H$ disser que $P(P)$ entra em loop, $D$ para imediatamente.  
  O que acontece quando passamos $D$ para ela mesma: $D(D)$?
  - Se $D(D)$ para, por definicao ela entra em loop.
  - Se $D(D)$ entra em loop, por definicao ela para.  
  A contradicao logica prova que a hipotese inicial e falsa: $H$ nao pode existir.

### 3.3 Teorema de Rice:
Qualquer propriedade semântica nao-trivial sobre o comportamento de programas (ex: "este programa imprime o numero 42?", "este codigo contem bugs?", "duas funcoes sao equivalentes?") e **indecidivel**.

---

## CAMADA 4: ARQUITETURA DE COMPILADORES

Um compilador opera em pipeline sequencial dividido entre Frontend (independente de maquina) e Backend (dependente da arquitetura de CPU):

```text
[ Codigo-Fonte ]
       │
       ▼
1. Analise Lexica (Scanner)  ------> Regex / DFA -> Emite Tokens
       │
       ▼
2. Analise Sintatica (Parser) -----> Gramaticas Livres de Contexto -> Gera AST
       │
       ▼
3. Analise Semantica --------------> Tabela de Simbolos, Tipos e Escopo
       │
       ▼
4. Codigo Intermediario (IR) ------> Three-Address Code (3AC) ou SSA Form
       │
       ▼
5. Otimizacao de Codigo -----------> Propagacao de Constantes, Loop Invariant
       │
       ▼
6. Geracao de Codigo Objeto -------> Alocacao de Registradores e Assembly/Binario
```

---

## CAMADA 5: ARMADILHAS CLASSICAS DO ENADE

1. **Linguagem Regular com Aninhamento:** Se a questao pedir para reconhecer "expressoes matematicas com parenteses balanceados aninhados" usando Regex ou Automato Finito, a resposta e **IMPOSSIVEL**. Aninhamento arbitrario exige memoria em pilha (Linguagem Livre de Contexto).
2. **Gramaticas Ambiguas em Compiladores:** Uma gramatica e ambigua quando gera mais de uma Parse Tree diferente para a mesma cadeia. Gramaticas ambiguas **NAO** podem ser convertidas diretamente para parsers preditivos deterministas LL(1) sem reescrita de regras ou associacao de precedencia de operadores.
3. **Decidibilidade vs. Complexidade:** Cuidado para nao confundir:
   - Problemas **NP-Completos** (como Caixeiro Viajante e Satisfatibilidade Booleana/SAT) sao **DECIDIVEIS** (podem ser resolvidos, embora levem tempo exponencial no pior caso se $P \ne NP$).
   - O **Problema da Parada** e **INDECIDIVEL** (nao existe algoritmo possivel, mesmo com tempo infinito).
