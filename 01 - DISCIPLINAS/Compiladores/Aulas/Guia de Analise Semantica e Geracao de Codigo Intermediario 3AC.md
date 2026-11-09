---
tipo: aula
disciplina: Compiladores
professor: Daniel
tema: "Analise Semantica, Verificacao de Tipos e Codigo de Tres Enderecos (3AC)"
status: concluido
---

> [!NOTE] Navegacao no Cofre
> **Painel Geral:** [[00 - Dashboard]] | **Indice de Aulas:** [[Índice Geral de Aulas]]
> **Disciplina:** [[Compiladores]] | **Nucleo ENADE:** [[03 - Teoria da Computacao, Automatos e Compiladores (Do Basico ao Avancado)]]

# Guia de Analise Semantica e Geracao de Codigo Intermediario 3AC

> [!INFO] **Visao Geral do Modulo**
> A analise semantica valida o significado do programa contra as regras da linguagem que nao podem ser expressas por gramaticas livres de contexto (ex: declaracao previa de variaveis, compatibilidade de tipos e escopo). A geracao de codigo intermediario traduz a AST validada em uma representacao agnostica de maquina (Three-Address Code).

---

## 1. Tabela de Simbolos e Gestao de Escopo

A Tabela de Simbolos e a estrutura de dados central que armazena informacoes sobre identificadores:
- **Atributos:** Nome, tipo de dado (int, float, array, classe), tamanho em bytes, deslocamento de memoria (*offset*), linha de declaracao e escopo.
- **Hierarquia de Escopos (Pilha de Tabelas):** Quando um novo bloco `{ ... }` e aberto, uma nova tabela e empilhada apontando para o escopo pai (*parent scope*). Ao buscar um identificador, busca-se do escopo atual para o topo. Ao fechar o bloco, a tabela local e desempilhada.

---

## 2. Verificacao de Tipos (Type Checking) e Coercoes

O sistema de tipos do compilador aplica regras de deducao estatica:
- **Compatibilidade:** Em `a = b + c`, verifica se os tipos de `b` e `c` suportam a operacao `+` e se o resultado e atribuivel a `a`.
- **Coercao Implicita (Widening):** Conversao automatica e segura promovida pelo compilador (ex: converter `int` para `float` antes de somar).
- **Casting Explicito (Narrowing):** Conversao forcada pelo programador com risco de perda de precisao.

---

## 3. Codigo de Tres Enderecos (Three-Address Code — 3AC)

O 3AC e uma representacao intermediaria linearizada onde cada instrucao possui no maximo um operador e tres enderecos de memoria (dois operandos e um destino).

### Formato Geral:
$$x = y \text{ op } z$$

### Exemplo de Linearizacao:
Para a expressao em codigo-fonte:
```c
x = (-b + sqrt(b * b - 4 * a * c)) / (2 * a);
```

Instrucoes geradas em 3AC:
```text
t1 = -b
t2 = b * b
t3 = 4 * a
t4 = t3 * c
t5 = t2 - t4
t6 = call sqrt, t5
t7 = t1 + t6
t8 = 2 * a
t9 = t7 / t8
x = t9
```

### Otimizacoes Basicas sobre Blocos Basicos:
1. **Eliminacao de Subexpressoes Comuns (Common Subexpression Elimination):** Reutiliza temporarios com computacoes identicas.
2. **Propagacao de Constantes:** Se `a = 5` e `b = a + 2`, substitui diretamente `b = 7`.
3. **Eliminacao de Codigo Morto (Dead Code Elimination):** Remove atribuicoes a variaveis que nunca sao lidas posteriormente.
