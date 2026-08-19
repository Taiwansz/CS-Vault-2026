---
tipo: simulado
categoria: "Estudos ENADE"
data: 2026-08-18
professor: "Luiz Claudio Chiavini Oliveira Junior"
assunto: "Questões de Análise de Algoritmos, Engenharia de Software, Banco de Dados, Sistemas Operacionais e Teoria da Computação"
---

# 📝 Formulário de Estudos para o ENADE — Simulado 02 (Ciência da Computação)

> [!info] 📌 Informações do Formulário
> - **Finalidade:** Preparatório para a Prova do ENADE (Ciência da Computação)
> - **Professor Responsável:** Prof. Luiz Claudio Chiavini Oliveira Junior
> - **Data de Realização:** 18/08/2026
> - **Total de Questões:** 5
> - **Áreas Cobertas:** Análise de Algoritmos (Merge Sort), Engenharia de Software (GoF), Banco de Dados (2FN), Sistemas Operacionais (Memória Virtual), Teoria da Computação (Chomsky).

---

> [!tip] 💡 CONCEITO FUNDAMENTAL PARA INICIANTES: Como o Computador Armazena e Processa Dados
> Antes de resolvermos as questões, vamos revisar como a programação funciona na essência:
> 
> 1. **Variáveis são Caixinhas na RAM:** Na programação, quando criamos uma variável (ex: `int idade = 20;`), o computador aloca um espaço na memória RAM e coloca uma etiqueta com o nome `idade`.
> 2. **O Símbolo `=` é Atribuição:** O operador `=` **NÃO é igualdade matemática estática**. Ele é uma ordem de ação: *"Calcule tudo do lado DIREITO e guarde dentro da variável do lado ESQUERDO!"*.
> 3. **Divisão e Conquista:** Computadores resolvem problemas gigantescos dividindo-os em partes menores e mais fáceis de resolver.

---

## ❓ Questão 1 — Análise de Algoritmos (Teorema Mestre e Merge Sort)

Considere a função de recorrência \(T(n) = 2T(n/2) + O(n)\), que descreve o tempo de execução do algoritmo de ordenação **Merge Sort** para uma entrada de tamanho \(n\). Utilizando o **Teorema Mestre**, qual é a complexidade assintótica de tempo no pior caso?

- [ ] **A)** \(O(n)\)
- [x] **B)** \(O(n \log n)\)  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** \(O(n^2)\)
- [ ] **D)** \(O(\log n)\)

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> 1. **O que é o Merge Sort?**
>    Imagine que você tem 1.000 cartas de baralho misturadas e precisa colocá-las em ordem. Em vez de olhar uma por uma (o que levaria muito tempo), a estratégia de "Divisão e Conquista" do Merge Sort faz o seguinte:
>    - **Divisão:** Divide a pilha de cartas ao meio repetidamente até ter pilhas minúsculas de 1 carta.
>    - **Conquista (Intercalação/Merge):** Junta as pilhas de duas em duas, ordenando no caminho.
> 
> 2. **Desmontando a fórmula matemática \(T(n) = 2T(n/2) + O(n)\):**
>    - \(2T(n/2)\): Significa que o problema original de tamanho \(n\) é quebrado em **2 subproblemas**, cada um com a **metade do tamanho (\(n/2\))**.
>    - \(+ O(n)\): É o trabalho necessário para juntar/intercalar as duas metades já ordenadas, que custa tempo proporcional a \(n\) em cada nível.
> 
> 3. **Por que o resultado final é \(O(n \log n)\)?**
>    - Quantas vezes conseguimos dividir uma pilha de tamanho \(n\) ao meio até restar apenas 1 elemento? A resposta matemática para essa quantidade de divisões é \(\log_2 n\) (logaritmo de \(n\) na base 2).
>    - Em cada um desses \(\log n\) níveis de divisão, o computador faz um trabalho de varredura de tamanho \(n\).
>    - Multiplicando a quantidade de níveis pelo trabalho feito em cada nível: \(n \times \log n = O(n \log n)\).

---

## ❓ Questão 2 — Engenharia de Software (Padrões GoF Criacionais - Factory Method)

Em um sistema de vendas online, deseja-se instanciar diferentes modalidades de pagamento (Cartão de Crédito, Pix, Boleto) sem expor a lógica de criação diretamente ao cliente e permitindo que novas formas de pagamento sejam adicionadas com facilidade. Qual padrão de projeto GoF (*Gang of Four*) criacional é o mais adequado para isolar esse processo?

- [x] **A)** Factory Method  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** Singleton
- [ ] **C)** Observer
- [ ] **D)** Decorator

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa A é a correta?**
> 
> 1. **O que é um Padrão de Projeto (*Design Pattern*)?**
>    São soluções prontas e recomendadas por especialistas para resolver problemas comuns de arquitetura e organização de código.
> 
> 2. **O Problema de criar objetos manualmente:**
>    Se o código do seu carrinho de compras fizer diretamente `new CartaoCredito()`, `new Pix()`, `new Boleto()`, toda vez que surgir uma nova opção (como `Criptomoeda` ou `ApplePay`) você terá que alterar e retestar o código do carrinho inteiro. Isso gera um acoplamento (dependência) muito alto e arriscado.
> 
> 3. **A Solução da "Fábrica" (*Factory Method*):**
>    Em uma fábrica de carros real, você não precisa saber montar o motor; você apenas solicita à fábrica: *"Por favor, me entregue um carro modelo Sedan"*.
>    O **Factory Method** funciona exatamente assim: ele cria uma classe "Fábrica de Pagamentos". O código do seu sistema apenas diz: *"Fábrica, crie para mim um objeto de pagamento do tipo PIX"*. A fábrica encapsula a lógica de criação e devolve o objeto pronto. Se surgirem novos pagamentos amanhã, você só atualiza a fábrica e o resto do sistema continua funcionando intacto!

---

## ❓ Questão 3 — Banco de Dados Relacional (Normalização e Segunda Forma Normal - 2FN)

Em um banco de dados relacional, uma tabela encontra-se na Primeira Forma Normal (1FN) e possui uma chave primária composta por dois atributos \((A, B)\). Se existir um atributo não-chave \(C\) que depende funcionalmente apenas do atributo \(A\), qual forma normal a tabela está violando?

- [ ] **A)** Primeira Forma Normal (1FN)
- [x] **B)** Segunda Forma Normal (2FN)  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Terceira Forma Normal (3FN)
- [ ] **D)** Forma Normal de Boyce-Codd (FNBC)

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa B é a correta?**
> 
> 1. **O que é Normalização de Banco de Dados?**
>    É o processo de organizar as tabelas de um banco de dados para eliminar repetição desnecessária de informações (redundância) e evitar erros de atualização.
> 
> 2. **Entendendo as Chaves e Dependências:**
>    - **Chave Primária Composta \((A, B)\):** É a combinação de dois campos usada para identificar uma linha de forma única. Exemplo: `(CodigoCurso, IDAluno)`.
>    - **Atributo Não-Chave \(C\):** É uma informação armazenada na tabela, como o `NomeDoCurso`.
> 
> 3. **Por que viola a Segunda Forma Normal (2FN)?**
>    - A **2FN** estabelece a seguinte regra de ouro: **Nenhum campo da tabela pode depender de APENAS UMA PARTE da chave primária.** Ele precisa depender de TODA a chave primária composta!
>    - No problema, a chave completa é \((A, B)\). Se o campo \(C\) (`NomeDoCurso`) depende apenas de \(A\) (`CodigoCurso`) e ignora \(B\) (`IDAluno`), existe uma **dependência parcial**.
>    - Essa dependência parcial viola frontalmente a **2FN**, pois o nome do curso ficaria se repetindo em todas as linhas dos alunos matriculados naquele curso.

---

## ❓ Questão 4 — Sistemas Operacionais (Gerenciamento de Memória Virtual e Page Fault)

No gerenciamento de memória virtual de um sistema operacional por paginação, o fenômeno de falta de página (*page fault*) ocorre quando:

- [x] **A)** A página referenciada pelo processador não está presente na memória RAM e precisa ser buscada no disco secundário.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** A memória cache L1 transborda e exige a invalidação imediata dos registradores centrais.
- [ ] **C)** Ocorre fragmentação externa excessiva impedindo a alocação de blocos contíguos de memória.
- [ ] **D)** Uma instrução tenta acessar um endereço físico inexistente no barramento de dados da CPU.

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa A é a correta?**
> 
> 1. **Como funciona a Memória Virtual?**
>    A memória RAM é ultrarrápida, mas pequena (ex: 16 GB). O seu disco SSD/HD é gigante (ex: 1.000 GB), porém mais lento. Para você conseguir abrir muitos programas pesados ao mesmo tempo, o Sistema Operacional usa a **Memória Virtual**: ele divide a memória em pedacinhos de tamanho fixo chamados **Páginas**.
> 
> 2. **A "Mágica" da Paginação:**
>    O processador acha que todos os programas abertos estão inteiros na memória RAM. Mas na verdade, o sistema operacional deixa na RAM apenas as páginas que estão sendo usadas *neste exato segundo*. As páginas que não estão em uso no momento ficam salvas temporariamente no disco SSD.
> 
> 3. **O que é o *Page Fault* (Falta de Página)?**
>    Quando o processador vai executar um comando e percebe que a página de memória necessária **não está na RAM** (pois ficou guardada no SSD), ocorre um evento chamado **Page Fault**!
>    O processador faz uma pausa rápida, o Sistema Operacional vai até o disco SSD, lê a página faltante, carrega-a para a memória RAM e só então o processador retoma a execução do programa.

---

## ❓ Questão 5 — Teoria da Computação (Hierarquia de Chomsky e Linguagens Livres de Contexto)

Na Hierarquia de Chomsky de teoria da computação, qual das alternativas expressa corretamente a relação de reconhecimento para as linguagens livres de contexto?

- [ ] **A)** São reconhecidas por autômatos finitos determinísticos sem memória auxiliar.
- [ ] **B)** Exigem obrigatoriamente a capacidade computacional irrestrita de uma Máquina de Turing universal.
- [ ] **C)** São estritamente menos expressivas do que as linguagens regulares.
- [x] **D)** São reconhecidas por autômatos com pilha (*Pushdown Automata*).  <span class="badge badge-success">🟢 Resposta Correta</span>

> [!success] 🎓 Explicação Didática Passo a Passo (Do Zero ao Avançado)
> **Por que a alternativa D é a correta?**
> 
> 1. **O que é a Hierarquia de Chomsky?**
>    O matemático e linguista Noam Chomsky organizou todas as linguagens formais (incluindo as linguagens de programação) em 4 níveis de capacidade e complexidade:
>    - **Tipo 3 (Linguagens Regulares):** Mais simples. Reconhecidas por Autômatos Finitos (sem memória auxiliar). Usadas para palavras e tokens.
>    - **Tipo 2 (Linguagens Livres de Contexto):** Intermediárias. Geradas por gramáticas livres de contexto e reconhecidas por **Autômatos com Pilha (*Pushdown Automata*)**. Usadas para a estrutura gramatical dos programas.
>    - **Tipo 1 (Linguagens Sensíveis ao Contexto):** Reconhecidas por Autômatos Finitos Limitados.
>    - **Tipo 0 (Linguagens Irrestritas):** Mais poderosas. Reconhecidas por Máquinas de Turing.
> 
> 2. **Por que o Autômato com Pilha reconhece a Linguagem Livre de Contexto?**
>    Para validar a estrutura de um código de programação (por exemplo, garantir que para cada parêntese aberto `(` existe um parêntese fechado correspondente `)`), precisamos de um mecanismo de memória que guarde o que foi aberto para fechar na ordem inversa.
>    Essa estrutura de memória "O último que entra é o primeiro que sai" é uma **Pilha** (*Stack*). Um autômato dotado de uma pilha é chamado de **Autômato com Pilha (PDA)**, e ele é a ferramenta exata que reconhece as Linguagens Livres de Contexto (Tipo 2).
