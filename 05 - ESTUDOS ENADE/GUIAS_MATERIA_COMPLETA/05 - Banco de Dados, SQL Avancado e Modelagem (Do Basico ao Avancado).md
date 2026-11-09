---
tipo: guia-enade
disciplina: "Banco de Dados e Engenharia de Dados"
nivel: "Do Basico ao Avancado ENADE"
data: 2026-11-09
autor: "ThSyr Cognitive Sentinel"
---

> [!NOTE] Navegacao de Estudos ENADE
> **Central:** [[00 - Dashboard]] | **Hub Mestre ENADE:** [[INDEX_ESTUDOS_ENADE]]
> **Modulo Anterior:** [[04 - Sistemas Operacionais, Concorrencia e Memoria (Do Basico ao Avancado)]]
> **Proximo Modulo:** [[06 - Redes de Computadores e Sistemas Distribuidos (Do Basico ao Avancado)]]

# 05 — Banco de Dados, SQL Avancado e Modelagem (Do Basico ao Nivel ENADE)

> [!INFO] **Objetivo deste Guia**
> Este modulo aborda as questoes essenciais de Banco de Dados do ENADE: formalismo da Algebra Relacional, o processo matematico de Normalizacao (1FN a BCNF), a governanca de Transacoes ACID, niveis de isolamento de concorrencia e consultas analiticas com Window Functions.

---

## CAMADA 1: O BASICO (O MODELO RELACIONAL E CHAVES)

Proposto por Edgar F. Codd em 1970, o modelo relacional organiza dados em **relacoes (tabelas)** compostas por **tuplas (linhas)** e **atributos (colunas)**.

### Tipos de Chaves:
- **Superchave:** Um conjunto de um ou mais atributos que identifica unicamente cada tupla em uma relacao.
- **Chave Candidata:** Uma superchave minima (sem atributos redundantes). Uma tabela pode ter varias chaves candidatas (ex: `id` e `cpf`).
- **Chave Primaria (Primary Key — PK):** A chave candidata escolhida pelo arquiteto para ser o identificador principal. Nao aceita valores nulos (`NULL`) nem duplicados.
- **Chave Estrangeira (Foreign Key — FK):** Um atributo em uma tabela que faz referencia a chave primaria de outra tabela, garantindo a **Integridade Referencial** (nao e permitido cadastrar um pedido para um cliente inexistente).

---

## CAMADA 2: ALGEBRA RELACIONAL

A Algebra Relacional e a linguagem procedural teorica que serve de base para o SQL. Uma consulta recebe relacoes e produz uma nova relacao.

### Operadores Fundamentais:
1. **Selecao ($\sigma_{condicao}(R)$):** Filtra linhas que atendem a um predicado (equivale ao `WHERE` do SQL). 
 *Exemplo:* $\sigma_{idade > 18}(Alunos)$.
2. **Projecao ($\pi_{colunas}(R)$):** Filtra e extrai colunas especificas, eliminando duplicatas (equivale ao `SELECT DISTINCT` do SQL). 
 *Exemplo:* $\pi_{nome, email}(Alunos)$.
3. **Uniao ($R \cup S$):** Combina tuplas de duas relacoes compativeis quanto a esquema.
4. **Diferenca ($R - S$):** Tuplas que estao em $R$ mas nao estao em $S$.
5. **Produto Cartesiano ($R \times S$):** Combina todas as tuplas de $R$ com todas as tuplas de $S$.
6. **Renomeacao ($\rho_{S}(R)$):** Altera o nome de uma relacao ou atributos.

### Operadores Derivados:
- **Juncao Natural ($R \bowtie S$):** Faz o produto cartesiano seguido de uma selecao de igualdade nos atributos com nomes identicos, projetando apenas uma copia dos atributos repetidos.
- **Theta-Join ($R \bowtie_{\theta} S$):** Produto cartesiano seguido de selecao com condicao arbitraria $\theta$ (equivale ao `INNER JOIN ... ON ...`).
- **Divisao ($R \div S$):** Responde a perguntas do tipo "encontre clientes que compraram **TODOS** os produtos da categoria X".

---

## CAMADA 3: NORMALIZACAO DE DADOS E DEPENDENCIAS FUNCIONAIS

Normalizar serve para eliminar redundancia de dados e anomalias de insercao, atualizacao e exclusao.

### Dependencia Funcional (DF):
Dada uma relacao $R$, diz-se que $X \to Y$ ($X$ determina $Y$) se e somente se, para quaisquer duas tuplas que tenham o mesmo valor de $X$, elas obrigatoriamente possuem o mesmo valor de $Y$.

### As Formas Normais:

| Forma Normal | Regra Central | Anomalia Eliminada |
| :--- | :--- | :--- |
| **1FN (Primeira Forma Normal)** | Todos os atributos devem conter apenas **valores atomicos e indivisiveis**. Vetores, arrays aninhados ou listas multivaloradas sao proibidos. | Linhas com multiplos telefones na mesma celula ou colunas duplicadas (`tel1`, `tel2`). |
| **2FN (Segunda Forma Normal)** | Deve estar na 1FN e **todo atributo nao-chave deve depender totalmente da chave primaria inteira**. Nao pode haver dependencia funcional parcial. | Em chaves compostas $(A, B)$, se $C$ depender apenas de $A$, ele deve ir para outra tabela. |
| **3FN (Terceira Forma Normal)** | Deve estar na 2FN e **nao pode haver dependencias funcionais transitivas** entre atributos nao-chave. Atributo nao-chave deve depender apenas da PK. | Se $PK \to CEP$ e $CEP \to Cidade$, $Cidade$ e dependente transitiva de $PK$. Deve-se criar tabela de CEPs. |
| **BCNF (Boyce-Codd)** | Todo determinante de uma dependencia funcional nao-trivial $X \to Y$ **deve ser obrigatoriamente uma Superchave**. | Casos raros de dependencias entre chaves candidatas compostas sobrepostas. |

---

## CAMADA 4: TRANSACOES E AS PROPRIEDADES ACID

Uma transacao e uma unidade logica de trabalho que deve ser executada de forma confiavel.

- **A — Atomicidade:** Tudo ou nada. Se uma etapa falhar, todas as alteracoes anteriores sao revertidas (*Rollback*).
- **C — Consistencia:** A transacao leva o banco de um estado valido a outro estado valido, respeitando todas as restricoes de integridade.
- **I — Isolamento:** Transacoes simultaneas nao devem interferir umas nas outras. O resultado de transacoes concorrentes deve ser equivalente a uma execucao serial.
- **D — Durabilidade:** Uma vez confirmada (*Commit*), as alteracoes sao gravadas de forma permanente no disco (via *Write-Ahead Logging* — WAL), resistindo a quedas de energia.

### Niveis de Isolamento ANSI SQL e Anomalias de Concorrencia:

| Nivel de Isolamento | Leitura Suja (Dirty Read) | Leitura Nao-Repetivel (Non-Repeatable Read) | Leitura Fantasma (Phantom Read) |
| :--- | :---: | :---: | :---: |
| **Read Uncommitted** | Ocorre | Ocorre | Ocorre |
| **Read Committed** | **Prevenido** | Ocorre | Ocorre |
| **Repeatable Read** | **Prevenido** | **Prevenido** | Ocorre |
| **Serializable** | **Prevenido** | **Prevenido** | **Prevenido** |

- **Dirty Read (Leitura Suja):** Transacao A le dados alterados por Transacao B que ainda nao fez commit e posteriormente executa rollback.
- **Non-Repeatable Read (Leitura Nao-Repetivel):** Transacao A le uma linha, Transacao B altera e comita essa linha; Transacao A le a mesma linha novamente e ve dados diferentes.
- **Phantom Read (Leitura Fantasma):** Transacao A consulta um conjunto de linhas com base em um filtro (`WHERE valor > 100`), Transacao B insere uma nova linha que atende a esse filtro; ao reexecutar a busca, Transacao A ve linhas novas "fantasmas".

---

## CAMADA 5: SQL AVANCADO E WINDOW FUNCTIONS

### Clausula WHERE vs. HAVING:
- `WHERE`: Filtra linhas **individuais** ANTES da agregacao ser executada. Nao aceita funcoes agregadas (`WHERE COUNT(*) > 5` e erro de sintaxe).
- `HAVING`: Filtra **grupos** DEPOIS que o `GROUP BY` calculou as somas, medias e contagens.

### Window Functions (Funcoes de Janela):
Diferente do `GROUP BY` (que colapsa varias linhas em uma unica saida), as Window Functions calculam agregacoes mantendo as linhas individuais intactas:

```sql
SELECT 
 nome_funcionario,
 departamento,
 salario,
 AVG(salario) OVER (PARTITION BY departamento) AS media_salarial_depto,
 DENSE_RANK() OVER (PARTITION BY departamento ORDER BY salario DESC) AS ranking_no_depto
FROM Funcionarios;
```
- `ROW_NUMBER()`: Atribui um numero sequencial unico ($1, 2, 3$).
- `RANK()`: Empata com a mesma nota, mas pula posicoes subsequentes ($1, 2, 2, 4$).
- `DENSE_RANK()`: Empata com a mesma nota e nao pula posicoes ($1, 2, 2, 3$).

---

## CAMADA 6: ARMADILHAS CLASSICAS DO ENADE

1. **Diferenca entre 2FN e 3FN:** 
 - Se a questao mostrar uma tabela com **Chave Primaria Simples (um unico atributo)**, ela **automaticamente ja esta na 2FN** (pois e impossivel haver dependencia parcial de chave unica!). O problema so podera ser violacao de 1FN ou 3FN.
2. **Where com Funcao de Agregacao:** Bancas adoram colocar `SELECT dept, AVG(sal) FROM emp WHERE AVG(sal) > 5000`. Isso gera **ERRO DE SINTAXE**. O correto e `HAVING AVG(sal) > 5000`.
3. **Nulos em Chaves:** Chave Primaria **NUNCA** pode ser nula. Chave Estrangeira **PODE** aceitar valores nulos (se o relacionamento for opcional).

---

## Conexoes no Cofre e Disciplinas Praticas

- **Hub Geral:** [[INDEX_ESTUDOS_ENADE]] | [[00 - Dashboard]]
- **Simulado de Referencia:** [[Simulado 04 - Ciencia da Computacao Completo 20 Questoes 2026-11-09]]
- **Disciplinas Praticas:** [[Banco de Dados]], [[Banco de Dados e Aplicações]], [[Tópicos Especiais II]]
