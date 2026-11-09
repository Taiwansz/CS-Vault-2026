---
tipo: aula
disciplina: Compiladores
professor: Daniel
tema: "Analise Sintatica, Gramaticas Livres de Contexto, LL(1) e LR(1)"
status: concluido
---

# Guia de Analise Sintatica LL(1) e LR(1) com Tabelas de Parsing

> [!INFO] **Visao Geral do Modulo**
> A analise sintatica (Parsing) recebe a sequencia de tokens do lexico e verifica se ela respeita as regras de derivacao de uma **Gramatica Livre de Contexto (GLC)**. A saida pode ser uma Arvore de Derivacao concreta (Parse Tree) ou uma Arvore Sintatica Abstrata (AST).

---

## 1. Classificacao dos Parsers

```text
Analisadores Sintaticos
├── Top-Down (Descendentes)
│   ├── Preditivo Recursivo sem Backtracking (LL(1))
│   └── Com Backtracking (Lento)
└── Bottom-Up (Ascendentes)
    ├── Shift-Reduce Classico
    ├── SLR(1) (Simple LR)
    ├── LR(1) Canonico
    └── LALR(1) (Look-Ahead LR — Usado pelo Yacc/Bison)
```

---

## 2. Conjuntos FIRST e FOLLOW em LL(1)

Para construir uma tabela de parsing descendente sem conflito ($M[A, a]$), e necessario calcular os conjuntos de caracteres de avanco:

### Calculo do FIRST($\alpha$):
- Se $X$ e terminal, $\text{FIRST}(X) = \{X\}$.
- Se $X \to \epsilon$, entao $\epsilon \in \text{FIRST}(X)$.
- Se $X \to Y_1 Y_2 \dots Y_k$, adicione $\text{FIRST}(Y_1)$ a $\text{FIRST}(X)$. Se $Y_1$ pode derivar $\epsilon$, continue para $Y_2$ ate o primeiro que nao derive vazio.

### Calculo do FOLLOW($A$):
- Coloque $\$$ (marcador de fim de cadeia) em $\text{FOLLOW}(S)$, onde $S$ e o simbolo inicial.
- Se ha uma producao $A \to \alpha B \beta$, tudo em $\text{FIRST}(\beta)$ (exceto $\epsilon$) entra em $\text{FOLLOW}(B)$.
- Se ha $A \to \alpha B$ ou $A \to \alpha B \beta$ onde $\text{FIRST}(\beta)$ contem $\epsilon$, entao tudo em $\text{FOLLOW}(A)$ entra em $\text{FOLLOW}(B)$.

### Criterio de Gramatica LL(1):
Uma gramatica e LL(1) se e somente se, para quaisquer producoes $A \to \alpha \mid \beta$:
1. $\text{FIRST}(\alpha) \cap \text{FIRST}(\beta) = \emptyset$.
2. Se $\beta \Rightarrow^* \epsilon$, entao $\text{FIRST}(\alpha) \cap \text{FOLLOW}(A) = \emptyset$.

---

## 3. Analisadores Ascendentes LR (Shift-Reduce)

Os parsers ascendentes constroem a arvore a partir das folhas em direcao a raiz (Bottom-Up), executando quatro acoes basicas sobre a pilha:
1. **Shift (Deslocamento):** Empilha o token lido e o proximo estado.
2. **Reduce (Reducao):** Quando o topo da pilha contem o lado direito de uma producao (um *handle*), desempilha os simbolos e empilha o nao-terminal correspondente.
3. **Accept (Aceitacao):** Entrada vazia e simbolo inicial reduzido com sucesso.
4. **Error:** Nenhuma transicao valida encontrada na tabela de acao.

### Conflitos Classicos em Parsers LR:
- **Shift/Reduce:** O parser nao sabe se deve empilhar o proximo token ou reduzir a expressao ja lida (ex: o classico problema do `else` pendente).
- **Reduce/Reduce:** O topo da pilha pode ser reduzido para mais de um nao-terminal diferente, indicando ambiguidade na gramatica.
