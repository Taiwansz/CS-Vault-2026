---
tipo: simulado
categoria: "Estudos ENADE"
data: 2026-08-17
professor: "Luiz Claudio Chiavini Oliveira Junior"
assunto: "Compiladores, Análise Léxica, Sintática, Semântica, Tabela de Símbolos e Representação Intermediária"
---

# 📝 Formulário de Estudos para o ENADE — Simulado 01 (Compiladores)

> [!info] 📌 Informações do Formulário
> - **Finalidade:** Preparatório para a Prova do ENADE (Ciência da Computação)
> - **Professor Responsável:** Prof. Luiz Claudio Chiavini Oliveira Junior
> - **Data de Passagem em Aula:** 17/08/2026 (Segunda-feira)
> - **Total de Questões:** 10
> - **Área Coberta:** Compiladores, Linguagens de Programação, Autômatos, AST, Tabela de Símbolos e Arquitetura Front-end / Back-end.

---

> [!tip] 💡 CONCEITO FUNDAMENTAL PARA INICIANTES: O Operador de Atribuição `=`
> Antes de começar as questões, vamos entender o comando mais básico da programação: o símbolo `=`.
> 
> Na **matemática tradicional da escola**, o `=` significa "igualdade estática" (exemplo: \(x = 5\) significa que os dois lados são idênticos).
> 
> Na **programação de computadores**, o `=` é o comando de **ATRIBUIÇÃO** (guardar valor).
> Significa exatamente isso:
> > *"Calcule ou pegue tudo o que está do lado DIREITO e guarde dentro da caixinha (variável) do lado ESQUERDO!"*
> 
> **Exemplo:** `resultado = 10 + 5;`
> 1. O computador primeiro resolve a conta do lado direito: \(10 + 5 = 15\).
> 2. Em seguida, pega o valor `15` e guarda dentro da caixinha da memória chamada `resultado`.

---

## ❓ Questão 1 — Fases do Compilador (Análise Léxica)

O processo de compilação é classicamente dividido em diversas fases, agrupadas em Front-end (análise) e Back-end (síntese). Qual das alternativas abaixo descreve corretamente a principal responsabilidade da **Análise Léxica**?

- [ ] **A)** Verificar se as variáveis utilizadas foram previamente declaradas e se os tipos são compatíveis.
- [x] **B)** Agrupar os caracteres do código-fonte em unidades lógicas chamadas *tokens*, eliminando espaços em branco e comentários.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Construir a Árvore Sintática Abstrata (AST) a partir das regras gramaticais da linguagem.
- [ ] **D)** Converter o código-fonte diretamente para linguagem de máquina (código objeto).
- [ ] **E)** Otimizar o código intermediário para que execute mais rápido na máquina alvo.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> Imagine que você recebe uma carta escrita à mão com borrões de café e letras juntas sem espaço. O computador não enxerga frases inteiras de uma vez; ele lê letra por letra (o caractere `i`, depois `n`, depois `t`, depois um espaço em branco).
> 
> A **Análise Léxica** (*Lexer* ou *Scanner*) é o "leitor inicial" do computador. O trabalho dele é:
> 1. **Limpar a sujeira:** Jogar fora espaços em branco desnecessários, comentários e quebras de linha (pois o computador não precisa deles para entender a lógica).
> 2. **Juntar os caracteres:** Agrupar letrinhas isoladas para formar "palavras com significado" chamadas de **tokens**.
> 
> *Exemplo:* Ao ler o código `int idade = 20;`, o analisador léxico gera 4 tokens:
> - Token 1: Tipo da variável (`int`)
> - Token 2: Nome do identificador (`idade`)
> - Token 3: Operador de atribuição (`=`)
> - Token 4: Valor numérico (`20`)

---

## ❓ Questão 2 — Formalismos da Análise Léxica (Expressões Regulares e AFD)

Um aluno de Ciência da Computação está desenvolvendo um compilador e definiu as regras de sua linguagem utilizando **Expressões Regulares**. Ele implementou um **Autômato Finito Determinístico (AFD)** para reconhecer essas regras. Em qual fase do compilador o aluno está trabalhando?

- [x] **A)** Análise Léxica.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** Análise Sintática.
- [ ] **C)** Análise Semântica.
- [ ] **D)** Geração de Código.
- [ ] **E)** Otimização de Código.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa A é a correta?**
> 
> Como o computador sabe se um conjunto de caracteres é um número ou um nome de variável? Ele usa regras de padrão!
> - **Expressão Regular:** É uma regra escrita que descreve o padrão da palavra. Exemplo: *"Um número inteiro deve conter apenas dígitos de 0 a 9"*.
> - **Autômato Finito Determinístico (AFD):** É como uma catraca inteligente de metrô. Conforme lê cada caractere, ele muda de estado. Se lê apenas dígitos `1`, `5`, `0`, a catraca permite a passagem e confirma: *"Isto é um número válido!"*.
> 
> Como a função de identificar e montar palavras (*tokens*) pertence exclusivamente à **Análise Léxica**, é nessa fase que essas duas ferramentas são utilizadas.

---

## ❓ Questão 3 — Diagnóstico de Erros (Análise Sintática)

Durante a compilação de um programa escrito em linguagem C, o compilador emite a seguinte mensagem de erro: `error: expected ';' before 'return'`. Com base nos conceitos de compiladores, em qual fase esse erro foi detectado?

- [ ] **A)** Análise Léxica, pois o caractere `;` não pertence ao alfabeto da linguagem.
- [ ] **B)** Análise Semântica, pois o tipo de retorno não corresponde ao esperado.
- [x] **C)** Análise Sintática, pois a estrutura hierárquica da frase violou as regras de produção da gramática.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** Geração de Código Intermediário, pois a instrução não pôde ser traduzida.
- [ ] **E)** Ligação (*Linking*), pois a função `return` não foi encontrada na biblioteca padrão.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa C é a correta?**
> 
> Na nossa língua portuguesa, toda frase precisa seguir uma regra gramatical (Sujeito + Verbo + Predicado) e terminar com um ponto final `.`. Se você escrever *"Gato o peixe comeu"*, as palavras existem, mas a frase violou a gramática.
> 
> Em linguagens como C ou Java, o ponto e vírgula `;` funciona exatamente como o ponto final da frase. A **Análise Sintática** é o "professor de português" do compilador. Ela verifica a ordem das palavras e o cumprimento da estrutura gramatical. Quando falta o `;`, ela aponta: *"Erro Sintático! A regra da gramática exige um ';' antes do comando return"*.

---

## ❓ Questão 4 — Gramáticas Livres de Contexto (Ambiguidade)

As Gramáticas Livres de Contexto (GLC) são a base matemática para a construção dos analisadores sintáticos (*parsers*). Em relação à construção da Árvore de Derivação (ou Árvore Sintática), dizemos que uma gramática é **ambígua** quando:

- [ ] **A)** Não é capaz de reconhecer todas as palavras reservadas da linguagem.
- [x] **B)** Produz mais de uma árvore de derivação (ou sintática) válida para uma mesma sentença.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Permite a declaração de variáveis com o mesmo nome em escopos diferentes.
- [ ] **D)** Exige o uso de Autômatos Finitos Não-Determinísticos para ser processada.
- [ ] **E)** Não consegue resolver conflitos de tipagem de dados dinâmicos.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> Em português, a frase *"Vi o homem com o binóculo"* é ambígua porque pode significar duas coisas:
> 1. Eu usei um binóculo para ver o homem.
> 2. Eu vi um homem que estava segurando um binóculo.
> 
> Um computador **jamais pode ter dúvidas sobre o significado de um comando**. Se uma gramática de programação for **ambígua**, significa que o compilador consegue construir duas árvores de interpretação diferentes para o exato mesmo código-fonte. Isso é inaceitável em programação, pois o computador poderia executar ações totalmente distintas dependendo de qual interpretação escolhesse.

---

## ❓ Questão 5 — Árvore Sintática Abstrata (AST) e Precedência de Operadores

Considere a seguinte expressão matemática no código-fonte de um programa: `resultado = a + b * c;` Ao gerar a **Árvore Sintática Abstrata (AST)** para essa expressão, considerando as regras padrão de precedência matemática, é correto afirmar que:

- [ ] **A)** O operador de adição (`+`) estará em um nível mais profundo (mais próximo das folhas) do que o operador de multiplicação (`*`).
- [ ] **B)** Os operadores (`+` e `*`) estarão no mesmo nível hierárquico na árvore.
- [x] **C)** O nó raiz da árvore dessa expressão será o operador de atribuição (`=`).  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** A árvore gerada será puramente linear, semelhante a uma lista encadeada.
- [ ] **E)** A precedência dos operadores só é resolvida durante a execução do programa (em tempo de execução), e não na AST.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa C é a correta? (Entendendo o `=` detalhadamente)**
> 
> Vamos desmontar o comando `resultado = a + b * c;`:
> 1. **A Função do `=`: ** Como vimos no conceito inicial, o `=` **NÃO significa que os dois lados são iguais**. Ele significa: *"Calcule todo o resultado do lado direito e guarde na variável do lado esquerdo"*.
> 2. **Quem manda no comando inteiro?** A ação principal de toda essa linha é **guardar o valor final** dentro da variável `resultado`. Por ser a ação de menor precedência e que engloba toda a linha, o operador `=` fica no topo absoluto (na **raiz**) da árvore!
> 3. **E a matemática do lado direito?**
>    - Pela regra matemática, a multiplicação `b * c` é mais forte (maior precedência) e deve ser resolvida antes da adição. Na árvore, o que é resolvido primeiro fica mais fundo (mais perto das folhas `b` e `c`).
>    - Depois, o resultado de `b * c` é somado com `a` através do operador `+`.
>    - Por fim, o valor total dessa soma sobe até o nó raiz (`=`) para ser armazenado na variável `resultado`.

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

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa C é a correta?**
> 
> Pense na frase: *"O suco de laranja bebeu o menino"*.
> - As palavras existem no dicionário? Sim (Léxico OK).
> - A gramática está certa? Sujeito + Verbo + Objeto, sim (Sintático OK).
> - Faz sentido no mundo real? **NÃO!** Suco não bebe pessoas (Semântico FALHOU!).
> 
> Na programação, a **Análise Semântica** valida o **sentido e a coerência** do código.
> No código acima, a estrutura matemática `z = x / y` está gramaticalmente perfeita. Porém, **não faz o menor sentido matemático dividir o número 10 pela palavra "Ola"**! A checagem de tipos (*type checking*) é a função da Análise Semântica.

---

## ❓ Questão 7 — Estrutura de Dados (Tabela de Símbolos)

A **Tabela de Símbolos** é uma estrutura de dados fundamental em compiladores. Sobre ela, é correto afirmar:

- [ ] **A)** É utilizada exclusivamente pela Análise Léxica para descartar espaços em branco.
- [x] **B)** Serve para armazenar informações sobre identificadores (variáveis, funções, classes), como seus tipos, escopos e endereços de memória.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** É uma tabela de roteamento usada na fase de ligação (*linking*) de bibliotecas externas.
- [ ] **D)** Armazena apenas as palavras reservadas da linguagem, não permitindo a inserção de novos dados durante a compilação.
- [ ] **E)** É construída apenas no *back-end*, após a geração de código de máquina.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> A **Tabela de Símbolos** é o **fichário ou agenda de contatos central** do compilador.
> Toda vez que o programador cria uma variável, como `int idade = 20;`, o compilador anota na ficha:
> - **Nome da variável:** `idade`
> - **Tipo de dado:** Número inteiro (`int`)
> - **Escopo:** Dentro da função principal
> - **Endereço na RAM:** Onde ela será guardada na memória do computador
> 
> Conforme o código avança, todas as fases do compilador consultam essa tabela para saber se a variável existe, qual o seu tipo e como lidar com ela.

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

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa C é a correta?**
> 
> - **I. Autômato Finito Determinístico (AFD):** É um mecanismo simples sem memória extra que apenas lê uma sequência contínua de letrinhas para formar palavras (*tokens*). Portanto, pertence à **Análise Léxica (I - B)**.
> - **II. Autômato com Pilha (PDA):** É um mecanismo mais avançado que possui uma "pilha de memória" (como uma pilha de pratos onde você coloca e tira coisas no topo). Essa pilha é indispensável para controlar parênteses que abrem e fecham `((a + b) * c)` e validar a estrutura gramatical das frases. Portanto, pertence à **Análise Sintática (II - A)**.

---

## ❓ Questão 9 — Arquitetura de Compiladores (Representação Intermediária - IR)

Sobre a arquitetura moderna de compiladores, a separação clara entre *Front-end* e *Back-end* traz diversos benefícios de engenharia de software. Qual é a principal vantagem de realizar essa separação utilizando uma **Representação Intermediária (IR)**?

- [ ] **A)** Eliminar a necessidade da Análise Semântica, pois o *Back-end* realiza essa checagem dinamicamente.
- [x] **B)** Facilitar a criação de compiladores para \(m\) linguagens e \(n\) arquiteturas de hardware, resultando no desenvolvimento de apenas \(m + n\) módulos, em vez de \(m \times n\) compiladores completos.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Tornar a Análise Léxica independente de Expressões Regulares.
- [ ] **D)** Fazer com que o código execute diretamente no hardware sem precisar gerar código de máquina.
- [ ] **E)** Diminuir o tamanho do código-fonte escrito pelo programador original.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> Imagine que existem 5 línguas no mundo (Português, Inglês, Espanhol, Francês e Alemão). Se você quisesse tradutores diretos de cada uma para todas as outras, precisaria criar \(5 \times 5 = 25\) tradutores individuais!
> 
> Agora imagine inventar um **idioma intermediário universal** (o Esperanto):
> - Você cria 5 tradutores de cada língua para o Esperanto (*Front-end*).
> - E 5 tradutores do Esperanto para cada língua (*Back-end*).
> - **Total:** Apenas \(5 + 5 = 10\) tradutores em vez de 25!
> 
> Nos compiladores modernos (como LLVM e GCC), o *Front-end* traduz C, C++, Java ou Rust para uma linguagem intermediária universal chamada **IR**. O *Back-end* pega essa IR e traduz para processadores Intel, AMD ou ARM. Isso reduz a complexidade de \(m \times n\) para apenas \(m + n\).

---

## ❓ Questão 10 — Erro de Escopo e Declaração (Análise Semântica)

Durante o desenvolvimento de um compilador para uma nova linguagem, o projetista encontrou o erro genérico de `"Undeclared Identifier"` (Identificador Não Declarado) ao compilar um programa teste. Esse erro acontece porque:

- [ ] **A)** O analisador léxico não conseguiu reconhecer os caracteres que compõem o nome do identificador.
- [ ] **B)** O analisador sintático falhou ao tentar montar a árvore abstrata.
- [x] **C)** O analisador semântico procurou o identificador na Tabela de Símbolos e não encontrou nenhum registro correspondente naquele escopo.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **D)** O gerador de código final falhou ao alocar registradores no processador.
- [ ] **E)** O programador utilizou uma palavra reservada como nome de variável.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa C é a correta?**
> 
> Imagine entrar em uma lanchonete e pedir: *"Por favor, me dê um X-Mágico!"*.
> O atendente abre o cardápio (a Tabela de Símbolos) e procura por *"X-Mágico"*. Ao constatar que esse lanche não está cadastrado no cardápio, ele avisa: *"Desculpe, item não cadastrado!"*.
> 
> Na programação é a mesma coisa! Se você tentar usar uma variável `resultado` sem antes ter criado ela (ex: `int resultado;`), o **analisador semântico** faz uma busca na Tabela de Símbolos. Como não encontra nenhum registro de criação para `resultado`, ele interrompe e avisa: *"Erro: Identificador Não Declarado!"*.
