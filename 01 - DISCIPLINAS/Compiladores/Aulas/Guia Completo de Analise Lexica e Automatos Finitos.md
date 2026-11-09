---
tipo: aula
disciplina: Compiladores
professor: Daniel
tema: "Analise Lexica, Expressoes Regulares e Automatos Finitos"
status: concluido
---

# Guia Completo de Analise Lexica e Automatos Finitos

> [!INFO] **Visao Geral do Modulo**
> A analise lexica (Scanning) constitui a primeira fase de um compilador. Seu objetivo primario e ler o fluxo linear de caracteres do codigo-fonte e agrupá-los em sequencias significativas denominadas **lexemas**, gerando como saida uma sequencia de **tokens**.

---

## 1. Conceitos Fundamentais: Token, Padrao e Lexema

- **Lexema:** A sequencia concreta de caracteres lida do codigo-fonte que bate com um determinado padrao (ex: `total`, `125`, `while`, `==`).
- **Token:** Um par abstrato `<nome_do_token, valor_do_atributo>` que representa uma unidade logica da gramatica (ex: `<ID, "total">`, `<NUM, 125>`, `<WHILE, ->`, `<RELOP, EQ>`).
- **Padrao (Pattern):** A regra formal descrita por uma expressao regular que caracteriza a familia de lexemas aceitos para aquele token.

---

## 2. Expressoes Regulares (Regex) e Operacoes

Dado um alfabeto $\Sigma$, uma expressao regular define uma Linguagem Regular:
1. **Uniao (Alternancia):** $r | s$ representa $L(r) \cup L(s)$.
2. **Concatenacao:** $rs$ representa $L(r)L(s)$.
3. **Fecho de Kleene:** $r^*$ representa zero ou mais repeticoes de $r$.
4. **Fecho Positivo:** $r^+ = rr^*$ (uma ou mais repeticoes).

---

## 3. Automatos Finitos Deterministicos (DFA) vs. Nao-Deterministicos (NFA)

| Caracteristica | NFA (Automato Nao-Deterministico) | DFA (Automato Deterministico) |
| :--- | :--- | :--- |
| **Transicoes por $\epsilon$** | Permitidas (muda de estado sem ler simbolo) | Nao permitidas |
| **Destino por Simbolo** | Pode ter multiplos estados de destino para o mesmo simbolo | Exatamente um estado de destino para cada simbolo do alfabeto |
| **Construcao a partir de Regex** | Direta e trivial via Construcao de Thompson | Exige Algoritmo dos Subconjuntos (*Powerset Construction*) |
| **Execucao na Pratica** | Lenta na simulacao ($O(\|V\| \cdot n)$) | Ultra-rapida ($O(n)$ onde $n$ e o tamanho da string) |

### Algoritmo de Thompson:
Converte qualquer expressao regular em um NFA equivalente com no maximo $2|r|$ estados e $4|r|$ transicoes.

### Conversao de NFA para DFA (Algoritmo dos Subconjuntos):
1. Calcula o $\epsilon\text{-closure}(s_0)$ do estado inicial.
2. Para cada conjunto de estados e para cada simbolo de entrada, calcula o fechamento $\epsilon$ dos estados alcancaveis.
3. Cada conjunto unico de estados do NFA vira um estado unico no DFA.

---

## 4. O Problema do Maior Casamento (Longest Match / Maximal Munch)

Ao ler caracteres, o analisador lexico deve sempre priorizar o maior lexema possivel:
- Exemplo: Ao ler `var1`, o lexico nao deve emitir o token `ID("var")` e o numero `NUM(1)`; ele consome toda a sequencia `var1` como um unico identificador `ID("var1")`.
- Ao ler `==`, deve reconhecer o operador relacional `EQ` em vez de dois operadores de atribuicao `ASSIGN` consecutivos.
- Em caso de empate no tamanho, a regra de precedencia (ordem no arquivo de especificacao Lex/Flex) desempata (ex: palavras reservadas `if`, `while` tem precedencia sobre identificadores comuns).
