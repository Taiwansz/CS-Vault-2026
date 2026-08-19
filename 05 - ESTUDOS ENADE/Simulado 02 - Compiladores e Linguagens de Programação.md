---
tipo: simulado
categoria: "Estudos ENADE"
data: 2026-08-17
professor: "Luiz Claudio Chiavini Oliveira Junior"
assunto: "Compiladores, Análise Léxica, Sintática, Semântica, Tabela de Símbolos e Representação Intermediária"
---

# 📝 Formulário de Estudos para o ENADE — Simulado 02 (Compiladores)

> [!info] 📌 Informações do Formulário
> - **Finalidade:** Preparatório para a Prova do ENADE (Ciência da Computação)
> - **Professor:** Prof. Luiz Claudio Chiavini Oliveira Junior
> - **Data de Passagem em Aula:** 17/08/2026 (Segunda-feira)
> - **Total de Questões:** 10
> - **Área Coberta:** Compiladores, Linguagens de Programação, Autômatos, AST, Tabela de Símbolos e Arquitetura Front-end / Back-end.

---

## ❓ Questão 1 — Fases do Compilador (Análise Léxica)

O processo de compilação é classicamente dividido em diversas fases, agrupadas em Front-end (análise) e Back-end (síntese). Qual das alternativas abaixo descreve corretamente a principal responsabilidade da **Análise Léxica**?

- [ ] **A)** Verificar se as variáveis utilizadas foram previamente declaradas e se os tipos são compatíveis.
- [x] **B)** Agrupar os caracteres do código-fonte em unidades lógicas chamadas *tokens*, eliminando espaços em branco e comentários.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Construir a Árvore Sintática Abstrata (AST) a partir das regras gramaticais da linguagem.
- [ ] **D)** Converter o código-fonte diretamente para linguagem de máquina (código objeto).
- [ ] **E)** Otimizar o código intermediário para que execute mais rápido na máquina alvo.

> [!success] 💡 Feedback Explicativo
> A **Análise Léxica** (*Lexer/Scanner*) lê o fluxo de caracteres do código-fonte, descarta elementos irrelevantes (espaços, comentários e quebras de linha) e agrupa os caracteres válidos em *tokens* (unidades léxicas com significado).

---

## ❓ Questão 2 — Formalismos da Análise Léxica (Expressões Regulares e AFD)

Um aluno de Ciência da Computação está desenvolvendo um compilador e definiu as regras de sua linguagem utilizando **Expressões Regulares**. Ele implementou um **Autômato Finito Determinístico (AFD)** para reconhecer essas regras. Em qual fase do compilador o aluno está trabalhando?

- [x] **A)** Análise Léxica.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** Análise Sintática.
- [ ] **C)** Análise Semântica.
- [ ] **D)** Geração de Código.
- [ ] **E)** Otimização de Código.

> [!success] 💡 Feedback Explicativo
> **Expressões Regulares** e **Autômatos Finitos Determinísticos (AFD)** constituem a base matemática formal utilizada especificamente na fase de **Análise Léxica** para especificar e reconhecer os padrões dos *tokens* da linguagem.

---

## ❓ Questão 3 — Diagnóstico de Erros (Análise Sintática)

Durante a compilação de um programa escrito em linguagem C, o compilador emite a seguinte mensagem de erro: `error: expected ';' before 'return'`. Com base nos conceitos de compiladores, em qual fase esse erro foi detectado?

- [ ] **A)** Análise Léxica, pois o caractere `;` não pertence ao alfabeto da linguagem.
- [ ] **B)** Análise Semântica, pois o tipo de retorno não corresponde ao esperado.
- [x] **C)** Análise Sintática, pois a estrutura hierárquica da frase violou as regras de produção da gramática.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** Geração de Código Intermediário, pois a instrução não pôde ser traduzida.
- [ ] **E)** Ligação (*Linking*), pois a função `return` não foi encontrada na biblioteca padrão.

> [!success] 💡 Feedback Explicativo
> Erros de pontuação, pontuação ausente ou sequência incorreta de símbolos violam a gramática livre de contexto da linguagem e são detectados estritamente na fase de **Análise Sintática** (*parsing*).

---

## ❓ Questão 4 — Gramáticas Livres de Contexto (Ambiguidade)

As Gramáticas Livres de Contexto (GLC) são a base matemática para a construção dos analisadores sintáticos (*parsers*). Em relação à construção da Árvore de Derivação (ou Árvore Sintática), dizemos que uma gramática é **ambígua** quando:

- [ ] **A)** Não é capaz de reconhecer todas as palavras reservadas da linguagem.
- [x] **B)** Produz mais de uma árvore de derivação (ou sintática) válida para uma mesma sentença.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Permite a declaração de variáveis com o mesmo nome em escopos diferentes.
- [ ] **D)** Exige o uso de Autômatos Finitos Não-Determinísticos para ser processada.
- [ ] **E)** Não consegue resolver conflitos de tipagem de dados dinâmicos.

> [!success] 💡 Feedback Explicativo
> Uma gramática é **ambígua** quando existe ao menos uma cadeia de caracteres da linguagem que admite **duas ou mais árvores de derivação distintas**, o que gera indefinição sobre qual interpretação o compilador deve adotar.

---

## ❓ Questão 5 — Árvore Sintática Abstrata (AST) e Precedência

Considere a seguinte expressão matemática no código-fonte de um programa: `resultado = a + b * c;` Ao gerar a **Árvore Sintática Abstrata (AST)** para essa expressão, considerando as regras padrão de precedência matemática, é correto afirmar que:

- [ ] **A)** O operador de adição (`+`) estará em um nível mais profundo (mais próximo das folhas) do que o operador de multiplicação (`*`).
- [ ] **B)** Os operadores (`+` e `*`) estarão no mesmo nível hierárquico na árvore.
- [x] **C)** O nó raiz da árvore dessa expressão será o operador de atribuição (`=`).  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** A árvore gerada será puramente linear, semelhante a uma lista encadeada.
- [ ] **E)** A precedência dos operadores só é resolvida durante a execução do programa (em tempo de execução), e não na AST.

> [!success] 💡 Feedback Explicativo
> Na instrução de atribuição `resultado = a + b * c;`, o operador de atribuição (`=`) possui a menor precedência gramatical entre todos e engloba o comando inteiro, tornando-se o **nó raiz** da AST. A multiplicação `b * c` tem maior precedência que a adição, ficando mais profunda na árvore.

---

## ❓ Questão 6 — Análise Semântica (Checagem de Tipos)

Analise o trecho de código abaixo:

```c
int x = 10;
string y = "Ola";
int z = x / y;
```

Considerando uma linguagem estaticamente tipada, em qual fase da compilação o erro na linha 3 (dividir um inteiro por uma string) será primariamente detectado?

- [ ] **A)** Análise Léxica.
- [ ] **B)** Análise Sintática.
- [x] **C)** Análise Semântica.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** Análise Morfológica.
- [ ] **E)** Otimizador de Código.

> [!success] 💡 Feedback Explicativo
> A verificação de tipos (*type checking*) e a validação de compatibilidade entre operandos e operadores são responsabilidades centrais da **Análise Semântica**.

---

## ❓ Questão 7 — Estrutura de Dados (Tabela de Símbolos)

A **Tabela de Símbolos** é uma estrutura de dados fundamental em compiladores. Sobre ela, é correto afirmar:

- [ ] **A)** É utilizada exclusivamente pela Análise Léxica para descartar espaços em branco.
- [x] **B)** Serve para armazenar informações sobre identificadores (variáveis, funções, classes), como seus tipos, escopos e endereços de memória.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** É uma tabela de roteamento usada na fase de ligação (*linking*) de bibliotecas externas.
- [ ] **D)** Armazena apenas as palavras reservadas da linguagem, não permitindo a inserção de novos dados durante a compilação.
- [ ] **E)** É construída apenas no *back-end*, após a geração de código de máquina.

> [!success] 💡 Feedback Explicativo
> A **Tabela de Símbolos** armazena todas as informações contextuais sobre os identificadores declarados no programa (como tipo, categoria, escopo, dimensão e endereço de memória alocado).

---

## ❓ Questão 8 — Associação de Formalismos Matemáticos

Associe as ferramentas/formalismos matemáticos com a fase apropriada do compilador:

- **I.** Autômato Finito Determinístico (AFD)
- **II.** Autômato com Pilha (*Pushdown Automata*)
- **(A)** Análise Sintática
- **(B)** Análise Léxica

- [ ] **A)** I - A ; II - A
- [ ] **B)** I - B ; II - B
- [x] **C)** I - B ; II - A  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** I - A ; II - B
- [ ] **E)** Nenhuma das alternativas, pois compiladores utilizam apenas Máquinas de Turing.

> [!success] 💡 Feedback Explicativo
> - **Autômatos Finitos (AFD)** reconhecem linguagens regulares e fundamentam a **Análise Léxica (I - B)**.
> - **Autômatos com Pilha (PDA)** reconhecem linguagens livres de contexto e fundamentam a **Análise Sintática (II - A)**.

---

## ❓ Questão 9 — Arquitetura de Compiladores (Representação Intermediária - IR)

Sobre a arquitetura moderna de compiladores, a separação clara entre *Front-end* e *Back-end* traz diversos benefícios de engenharia de software. Qual é a principal vantagem de realizar essa separação utilizando uma **Representação Intermediária (IR)**?

- [ ] **A)** Eliminar a necessidade da Análise Semântica, pois o *Back-end* realiza essa checagem dinamicamente.
- [x] **B)** Facilitar a criação de compiladores para \(m\) linguagens e \(n\) arquiteturas de hardware, resultando no desenvolvimento de apenas \(m + n\) módulos, em vez de \(m \times n\) compiladores completos.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Tornar a Análise Léxica independente de Expressões Regulares.
- [ ] **D)** Fazer com que o código execute diretamente no hardware sem precisar gerar código de máquina.
- [ ] **E)** Diminuir o tamanho do código-fonte escrito pelo programador original.

> [!success] 💡 Feedback Explicativo
> Com uma **Representação Intermediária (IR)** padronizada (como no LLVM e GCC), ao adicionar uma nova linguagem de programação basta criar 1 módulo de *Front-end* que gere a IR; e ao suportar uma nova arquitetura de processador, basta criar 1 módulo de *Back-end* que consuma a IR. Isso reduz a complexidade de \(m \times n\) para \(m + n\).

---

## ❓ Questão 10 — Erro de Escopo e Declaração (Análise Semântica)

Durante o desenvolvimento de um compilador para uma nova linguagem, o projetista encontrou o erro genérico de `"Undeclared Identifier"` (Identificador Não Declarado) ao compilar um programa teste. Esse erro acontece porque:

- [ ] **A)** O analisador léxico não conseguiu reconhecer os caracteres que compõem o nome do identificador.
- [ ] **B)** O analisador sintático falhou ao tentar montar a árvore abstrata.
- [x] **C)** O analisador semântico procurou o identificador na Tabela de Símbolos e não encontrou nenhum registro correspondente naquele escopo.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** O gerador de código final falhou ao alocar registradores no processador.
- [ ] **E)** O programador utilizou uma palavra reservada como nome de variável.

> [!success] 💡 Feedback Explicativo
> A verificação de declaração de variáveis ocorre na **Análise Semântica**. O analisador busca o identificador na Tabela de Símbolos para o escopo corrente. Se não houver registro prévio da declaração, o erro de *"Undeclared Identifier"* é emitido.
