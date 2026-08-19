---
tipo: simulado
categoria: "Estudos ENADE"
data: 2026-08-18
assunto: "Questões de Análise de Algoritmos, Engenharia de Software, Banco de Dados, Sistemas Operacionais e Teoria da Computação"
---

# 📝 Formulário de Estudos para o ENADE — Simulado 01

> [!info] 📌 Informações do Formulário
> - **Finalidade:** Preparatório para a Prova do ENADE (Ciência da Computação)
> - **Data de Realização:** 18/08/2026
> - **Total de Questões:** 5
> - **Áreas Cobertas:** Análise de Algoritmos, Engenharia de Software (GoF), Banco de Dados (Formas Normais), Sistemas Operacionais (Memória Virtual), Teoria da Computação (Chomsky).

---

## ❓ Questão 1 — Análise de Algoritmos (Teorema Mestre)

Considere a função de recorrência \(T(n) = 2T(n/2) + O(n)\), que descreve o tempo de execução do algoritmo de ordenação **Merge Sort** para uma entrada de tamanho \(n\). Utilizando o **Teorema Mestre**, qual é a complexidade assintótica de tempo no pior caso?

- [ ] **A)** \(O(n)\)
- [x] **B)** \(O(n \log n)\)  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** \(O(n^2)\)
- [ ] **D)** \(O(\log n)\)

> [!success] 💡 Feedback Explicativo
> O Merge Sort divide a entrada ao meio a cada nível da árvore de recursão (com \(\log n\) níveis) e realiza a intercalação dos elementos com custo linear \(n\) em cada nível, resultando na complexidade assintótica de tempo no pior caso de **\(O(n \log n)\)**.

---

## ❓ Questão 2 — Engenharia de Software (Padrões GoF Criacionais)

Em um sistema de vendas online, deseja-se instanciar diferentes modalidades de pagamento (Cartão de Crédito, Pix, Boleto) sem expor a lógica de criação diretamente ao cliente e permitindo que novas formas de pagamento sejam adicionadas com facilidade. Qual padrão de projeto GoF (*Gang of Four*) criacional é o mais adequado para isolar esse processo?

- [x] **A)** Factory Method  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** Singleton
- [ ] **C)** Observer
- [ ] **D)** Decorator

> [!success] 💡 Feedback Explicativo
> O **Factory Method** encapsula a criação de objetos definindo uma interface para criá-los, permitindo que as subclasses decidam qual classe concreta instanciar sem acoplar o código do cliente.

---

## ❓ Questão 3 — Banco de Dados Relacional (Normalização e Formas Normais)

Em um banco de dados relacional, uma tabela encontra-se na Primeira Forma Normal (1FN) e possui uma chave primária composta por dois atributos \((A, B)\). Se existir um atributo não-chave \(C\) que depende funcionalmente apenas do atributo \(A\), qual forma normal a tabela está violando?

- [ ] **A)** Primeira Forma Normal (1FN)
- [x] **B)** Segunda Forma Normal (2FN)  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **C)** Terceira Forma Normal (3FN)
- [ ] **D)** Forma Normal de Boyce-Codd (FNBC)

> [!success] 💡 Feedback Explicativo
> A **Segunda Forma Normal (2FN)** exige que a tabela esteja na 1FN e que nenhum atributo não-chave dependa parcialmente da chave primária. Como a chave é composta por \((A, B)\) e o atributo \(C\) depende apenas de parte da chave (\(A\)), ocorre uma **dependência funcional parcial**, violando a 2FN.

---

## ❓ Questão 4 — Sistemas Operacionais (Gerenciamento de Memória Virtual)

No gerenciamento de memória virtual de um sistema operacional por paginação, o fenômeno de falta de página (*page fault*) ocorre quando:

- [x] **A)** A página referenciada pelo processador não está presente na memória RAM e precisa ser buscada no disco secundário.  <span class="badge badge-success">🟢 Resposta Correta</span>
- [ ] **B)** A memória cache L1 transborda e exige a invalidação imediata dos registradores centrais.
- [ ] **C)** Ocorre fragmentação externa excessiva impedindo a alocação de blocos contíguos de memória.
- [ ] **D)** Uma instrução tenta acessar um endereço físico inexistente no barramento de dados da CPU.

> [!success] 💡 Feedback Explicativo
> A **falta de página (*page fault*)** ocorre quando o endereço virtual acessado não possui seu bit de presença/validade ativo na tabela de páginas, exigindo uma operação de Entrada/Saída (E/S) no disco para carregar a página necessária para a memória RAM.

---

## ❓ Questão 5 — Teoria da Computação (Hierarquia de Chomsky)

Na Hierarquia de Chomsky de teoria da computação, qual das alternativas expressa corretamente a relação de reconhecimento para as linguagens livres de contexto?

- [ ] **A)** São reconhecidas por autômatos finitos determinísticos sem memória auxiliar.
- [ ] **B)** Exigem obrigatoriamente a capacidade computacional irrestrita de uma Máquina de Turing universal.
- [ ] **C)** São estritamente menos expressivas do que as linguagens regulares.
- [x] **D)** São reconhecidas por autômatos com pilha (*Pushdown Automata*).  <span class="badge badge-success">🟢 Resposta Correta</span>

> [!success] 💡 Feedback Explicativo
> As **linguagens livres de contexto** (Tipo 2 na Hierarquia de Chomsky) são geradas por gramáticas livres de contexto e reconhecidas precisamente por **Autômatos com Pilha (*Pushdown Automata* - PDA)**.
