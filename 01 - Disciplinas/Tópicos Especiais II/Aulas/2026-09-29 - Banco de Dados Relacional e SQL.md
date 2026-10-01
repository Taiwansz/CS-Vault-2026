---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-09-29
assunto: "Introdução a Banco de Dados Relacional, SQL e Operação com MySQL Workbench"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: em_andamento
---

# Aula 29/09 — Banco de Dados Relacional, SQL e MySQL Workbench

> [!info] Informações da Aula
> - **Data:** 29/09/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Foco:** Fundamentos de Banco de Dados Relacional, Linguagem SQL (DDL e DML) e Modelagem no MySQL Workbench.

---

## 1. Configurações de Ambiente e Conexão Local

Parâmetros de conexão com a instância local do banco:

| Parâmetro | Valor Configurado |
|---|---|
| **SGBD** | MySQL 8.x |
| **Interface / IDE** | MySQL Workbench |
| **Host** | `localhost` / `127.0.0.1` |
| **Porta** | `3306` |
| **Usuário** | `root` |
| **Senha** | `root753` |

---

## 2. Visão Geral e Arquitetura

Após a consolidação da persistência plana via arquivos texto e CSV (utilizada na aula de 15/09 com o sistema de gestão de lutadores), a disciplina avança para o padrão da indústria: Sistemas de Gerenciamento de Banco de Dados Relacionais (SGBD).

### Objetivos Operacionais da Aula:
1. **Modelagem de Entidades e Relacionamentos**: Definição de tabelas, tipos de dados, chaves primárias (`PRIMARY KEY`) e chaves estrangeiras (`FOREIGN KEY`).
2. **Linguagem SQL**:
   - **DDL (Data Definition Language)**: `CREATE DATABASE`, `CREATE TABLE`, `ALTER TABLE`, `DROP TABLE`.
   - **DML (Data Manipulation Language)**: `INSERT`, `UPDATE`, `DELETE`.
   - **DQL (Data Query Language)**: `SELECT`, filtros `WHERE`, ordenação e junções (`JOIN`).
3. **Interface MySQL Workbench**: Execução de scripts, manipulação de schemas e visualização do diagrama EER (Enhanced Entity-Relationship).

---

## 3. Registro de Scripts e Exercícios Práticos

### Primeiro Contato: Conexão CLI e Inspeção de Schemas

```sql
-- Listar todos os bancos de dados / schemas presentes na instância
SHOW DATABASES;
```

**Saída Obtida no Terminal (MySQL 8.0.43 Community Server):**
```text
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sakila             |
| sys                |
| world              |
+--------------------+
6 rows in set (0.00 sec)
```

#### Anatomia dos Schemas Padrão do MySQL:
- `information_schema`: O dicionário de metadados do SGBD. Armazena informações sobre tabelas, colunas, tipos de dados e privilégios de todos os outros bancos. Somente leitura.
- `mysql`: O núcleo de controle administrativo. Contém as tabelas de contas de usuários, senhas encriptadas, privilégios de acesso e procedimentos armazenados do sistema.
- `performance_schema`: Motor de telemetria e profiling em tempo real. Monitora alocação de memória, concorrência, locks de tabelas e tempo de execução de queries de baixo nível.
- `sys`: Conjunto de views e funções administrativas simplificadas que interpretam os dados densos do `performance_schema` para diagnóstico de performance.
- `sakila`: Banco relacional de demonstração oficial da Oracle/MySQL (modela uma videolocadora clássica, com tabelas de clientes, filmes, atores e locações).
- `world`: Banco de demonstração geográfico com dados de países, cidades e idiomas.

### Modelagem em Aula: Schema `projeto`, Dimensões (`PRODUTO`, `CLIENTES`) e Fato (`VENDAS`)

#### Script Corrigido e Validado:
```sql
USE PROJETO;

CREATE TABLE PRODUTO (
    ID_PRODUTO VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    NOME_PRODUTO VARCHAR(255) NOT NULL,
    CATEGORIA_PRODUTO VARCHAR(255) NOT NULL,
    MARCA_PRODUTO VARCHAR(255)
);

CREATE TABLE CLIENTES (
    ID_CLIENTE VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    NOME_CLIENTE VARCHAR(255) NOT NULL,
    CPF BIGINT NOT NULL UNIQUE,
    CIDADE_CLIENTE VARCHAR(255) NOT NULL,
    ESTADO_CLIENTE VARCHAR(255) NOT NULL
);

CREATE TABLE VENDAS (
    ID_VENDA VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    ID_CLIENTE VARCHAR(36), 
    ID_PRODUTO VARCHAR(36),
    QUANTIDADE INT NOT NULL,
    VALOR DECIMAL(10, 2),
    -- Integridade Referencial: Chaves Estrangeiras apontando para suas respectivas dimensões
    CONSTRAINT FK_PRODUTO FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO(ID_PRODUTO),
    CONSTRAINT FK_CLIENTE FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTES(ID_CLIENTE) -- Corrigido: Aponta para CLIENTES e sem vírgula final
);
```

#### Diagnóstico Cirúrgico dos Erros Detectados no Workbench:
1. **Omissão do Ponto e Vírgula após `USE PROJETO`**:
   - Sem o `;`, o MySQL tentou interpretar `USE PROJETO CREATE TABLE...` como uma instrução única, gerando o **Error Code: 1064**.
2. **Referência Errada de Chave Estrangeira em `VENDAS`**:
   - `CONSTRAINT FK_CLIENTE FOREIGN KEY (ID_CLIENTE) REFERENCES PRODUTO(ID_CLIENTE)`
   - Falha: `PRODUTO` não tem coluna `ID_CLIENTE`. A referência obrigatória é `REFERENCES CLIENTES(ID_CLIENTE)`.
3. **Vírgula Órfã (Trailing Comma) antes de `);` em `VENDAS`**:
   - A vírgula após a última constraint quebra a gramática da linguagem SQL (Error 1064).
4. **Tabela Vazia `fat_venda` no Final**:
   - Deve ser removida ou preenchida, pois um bloco `CREATE TABLE fat_venda ();` gera erro sintático imediato.

#### Status da Instância:
Execução confirmada no daemon MySQL local via `SHOW TABLES IN projeto;`:
- `clientes` (Tabela Dimensão)
- `produto` (Tabela Dimensão)
- `vendas` (Tabela Fato com relacionamentos de FK ativos)

---

## 4. Anotações do Professor e Destaques

### Taxonomia dos Dados na Computação

A base de qualquer arquitetura de persistência divide-se no espectro de rigidez do esquema:

| Categoria | Definição e Anatomia | Onde o Esquema Reside | Exemplos Típicos | Tecnologias / Motores |
|---|---|---|---|---|
| **Estruturados** | Dados altamente organizados em formato tabular bidimensional (linhas e colunas). Cada campo possui tipo, tamanho e restrições fixadas antes da gravação. | ***Schema-on-Write***: O esquema reside estritamente no banco de dados e governa cada escrita. | Cadastros bancários, tabelas de clientes, transações financeiras. | RDBMS: MySQL, PostgreSQL, Oracle, SQL Server. |
| **Semi-estruturados** | Não possuem tabelas rígidas, mas contêm marcadores, tags ou hierarquias internas (chaves/valores) que separam os elementos e criam semântica. Podem ter atributos variáveis por documento. | ***Schema-on-Read***: O esquema reside no dado ou na aplicação que interpreta a leitura. | Documentos JSON, arquivos XML, YAML, cabeçalhos de e-mail. | Bancos NoSQL Documentais (MongoDB, CouchDB), Redis, ElasticSearch. |
| **Não-estruturados** | Ausência completa de modelo conceitual ou esquema pré-definido. Sequências puras de bytes sem campos delimitados nativamente. | Inexistente / Inferido a posteriori por processamento pesado (IA / parsing). | Áudios, imagens, vídeos, PDFs digitalizados, texto livre, streams binários. | Object Storage (AWS S3, MinIO), Data Lakes. |

#### Pontos Críticos para Prova e Arquitetura:
1. **Compensação entre Rigor e Flexibilidade**:
   - Dados **Estruturados** priorizam consistência atômica, integridade referencial e consultas complexas via Álgebra Relacional (SQL), ao custo de rigidez em alterações de esquema (`ALTER TABLE`).
   - Dados **Semi-estruturados** priorizam evolução rápida e esquemas heterogêneos (um registro pode conter campos que o vizinho não tem), ao custo de integridade e joins nativos pesados.
2. **O Papel do SQL**: A linguagem SQL opera por excelência sobre o domínio dos dados **estruturados**, embora extensões modernas de SGBDs relacionais (como o tipo `JSON` nativo no MySQL e PostgreSQL) permitam campos semi-estruturados dentro de colunas estruturadas.

### Modelagem Dimensional: Tabela Fato vs. Tabela Dimensão

Conceito central da modelagem analítica (OLAP / Data Warehouse / Metodologia Ralph Kimball):

| Critério | Tabela Fato (Fact Table) | Tabela Dimensão (Dimension Table) |
|---|---|---|
| **Pergunta Essencial** | *O quê aconteceu?* (O evento em si) | *Quem, Onde, Quando, Como?* (O contexto) |
| **Natureza do Dado** | **Quantitativa / Métrica**: Números, valores agregáveis, totais. | **Qualitativa / Textual**: Categorias, nomes, datas, localizações. |
| **Conteúdo das Colunas** | Chaves Estrangeiras (FKs) + Métricas numéricas (ex: valor da venda, quantidade, desconto). | Chave Primária (PK / Surrogate Key) + Atributos descritivos (ex: nome do cliente, bairro, categoria). |
| **Volumetria / Crescimento** | **Massiva**: Cresce continuamente a cada nova transação (milhões/bilhões de linhas). | **Controlada**: Atualizações menos frequentes e número menor de registros. |
| **Exemplo no Mundo Real** | `Fato_Vendas`: `id_tempo`, `id_cliente`, `id_produto`, `quantidade_vendida`, `valor_total`. | `Dim_Cliente`: `id_cliente`, `nome`, `cidade`, `estado`, `cpf`.<br>`Dim_Tempo`: `id_tempo`, `data`, `mes`, `ano`, `trimestre`.<br>`Dim_Produto`: `id_produto`, `descricao`, `categoria`. |

```
        ┌──────────────────┐
        │   Dim_Cliente    │
        └────────┬─────────┘
                 │
  ┌──────────────┼──────────────┐
  │              ▼              │
┌─┴─────────┐ ┌──────────────┐ ┌┴──────────┐
│ Dim_Tempo │►│ FATO_VENDAS  │◄│Dim_Produto│
└───────────┘ └──────────────┘ └───────────┘
```
#### Anatomia Operacional do Star Schema (Esquema em Estrela):
1. **O Núcleo (Tabela Fato)**:
   - Localizada no epicentro do modelo.
   - Detém as métricas quantitativas e as chaves estrangeiras (`FK`) que amarram o evento a cada dimensão.
   - Relação de cardinalidade: **1 para N** a partir das dimensões para a fato (um cliente pode gerar infinitas vendas; cada venda na fato referencia um cliente na dimensão).
2. **As Pontas da Estrela (Tabelas Dimensão)**:
   - Ficam na periferia do diagrama.
   - **Desnormalização Intencional**: As tabelas dimensão no Star Schema deliberadamente quebram a 3ª Forma Normal (3FN). Mantém-se repetição controlada de dados textuais (ex: nome da cidade e estado gravados diretamente na dimensão cliente em vez de criar uma tabela isolada de cidades) para eliminar a necessidade de múltiplos `JOIN`s em consultas pesadas.
   - Benefício: Simplicidade de query (apenas um nível de `JOIN` entre fato e qualquer dimensão) e altíssima velocidade de leitura.

#### Contraste Vital para Prova: Star Schema vs. Snowflake Schema (Floco de Neve)

```
        DIAGRAMA DE UM FLUXO SNOWFLAKE (NORMALIZADO NAS PONTAS):

        ┌─────────────────────────┐
        │     Dim_Pais            │
        └────────────▲────────────┘
                     │
        ┌────────────┴────────────┐
        │     Dim_Regiao          │
        └────────────▲────────────┘
                     │
        ┌────────────┴────────────┐          ┌───────────────────────┐
        │     Dim_Cliente         │          │     Dim_Categoria     │
        └────────────┬────────────┘          └───────────▲───────────┘
                     │                                   │
                     ▼                       ┌───────────┴───────────┐
              ┌─────────────┐                │     Dim_Subcategoria  │
              │ FATO_VENDAS │                └───────────▲───────────┘
              └──────▲──────┘                            │
                     │                       ┌───────────┴───────────┐
                     │                       │     Dim_Produto       │
                     └───────────────────────┤                       │
                                             └───────────────────────┘
```

| Dimensão de Comparação | Star Schema (Estrela) | Snowflake Schema (Floco de Neve) |
|---|---|---|
| **Estrutura das Dimensões** | **Desnormalizadas** (1 nível de tabela por dimensão). | **Normalizadas** (decompostas em hierarquias 3FN). |
| **Complexidade das Queries** | **Baixa**: Poucos `JOIN`s, consultas simples e legíveis. | **Alta**: Múltiplos `JOIN`s encadeados para obter atributos descritivos. |
| **Consumo de Armazenamento** | Maior (devido à redundância deliberada nas dimensões). | Menor (elimina redundância e anomalias de atualização). |
| **Performance de Leitura (OLAP)** | **Superior**: Ideal para agregação massiva e dashboards. | **Inferior**: O custo computacional de junção de tabelas reduz o throughput. |
| **Cenário de Aplicação Recomendado** | Data Marts, Power BI, consultas analíticas velozes. | Ambientes onde a integridade rígida das dimensões e a economia de disco sobrepõem a performance de query. |

### O Mecanismo de Acoplamento: Chaves, JOIN e Merge

O elo que impede o banco de dados de se tornar um conjunto de ilhas incomunicáveis é a **relação chave primária -> chave estrangeira**:

1. **A Cola Estrutural**:
   - **Chave Primária (`PK - Primary Key`)**: Identificador único e imutável de uma entidade (geralmente na Tabela Dimensão ou tabela pai).
   - **Chave Estrangeira (`FK - Foreign Key`)**: Ponteiro referencial residente na Tabela Fato (ou tabela filha) que aponta diretamente para a PK da dimensão correspondente.
2. **A Operação de Conexão**:
   - **`JOIN` (SQL / Álgebra Relacional)**: Operação em tempo de consulta que funde horizontalmente as tuplas de duas ou mais tabelas com base em uma condição de igualdade nas chaves (`ON Fato.id_cliente = Dim.id_cliente`).
   - **`merge` (Engenharia de Dados / Pandas / ETL)**: Termo análogo utilizado em pipelines de dados para descrever a fusão horizontal de dois datasets estruturados a partir de chaves comuns.
3. **Semântica de Integridade Referencial**: O SGBD garante que nenhuma linha da Tabela Fato possa conter uma FK apontando para uma dimensão inexistente (evitando registros órfãos).

### Anatomia Física no Disco: Onde Ficam Salvos os Arquivos?

Existe uma confusão comum entre **o cliente gráfico (MySQL Workbench)** e **o servidor de banco de dados (MySQL Server)**. São duas camadas físicas distintas:

#### 1. Os Dados e Tabelas Reais (Gerenciados pelo MySQL Server Daemon)
- **Onde ficam**: Em uma pasta oculta do sistema operacional:
  `C:\ProgramData\MySQL\MySQL Server 8.0\Data\`
- **Como são salvos**: O Workbench **não** guarda tabelas. Quem escreve no disco é o processo de segundo plano do servidor (`mysqld.exe`).
- **Arquivos gerados**:
  - Para cada tabela criada, o motor InnoDB gera um arquivo binário `.ibd` (ex: `cliente.ibd`), contendo as páginas de dados e os índices da árvore B+.
  - Arquivos de controle e transação do motor: `ibdata1`, `ib_logfile0`, `undo_001`.
- **Persistência**: Imediata e permanente. Mesmo que você feche o Workbench ou desligue o computador, o que foi executado via `CREATE` ou `INSERT` já está consolidado no disco gerenciado pelo serviço do MySQL.

#### 2. Os Scripts e Códigos que Você Digita no Workbench
- **Scripts SQL (`.sql`)**: **NÃO são salvos automaticamente como arquivos do seu projeto**. Se você fechar a aba de script sem pressionar `Ctrl + S`, o código não existirá como arquivo no seu computador (o Workbench apenas tenta guardar um rascunho de emergência em `%APPDATA%\MySQL\Workbench\sql_workspaces`).
  - **Prática Obrigatória**: Sempre salvar explicitamente os scripts com `Ctrl + S` dentro da pasta de materiais da disciplina (ex: `Materiais/.../script.sql`).
- **Diagramas Visuais (EER Models)**: Ao desenhar tabelas graficamente em *File > New Model*, o projeto só vira arquivo no PC se você salvar explicitamente como arquivo **`.mwb`** (MySQL Workbench Model, que é internamente um arquivo compactado contendo metadados XML).
- **Backups / Dumps**: Quando você exporta um banco (*Data Export*), o Workbench gera um arquivo texto puro `.sql` contendo todos os comandos DDL/DML para recriar o schema em outra máquina.

### Cardinalidade e Diagrama Entidade-Relacionamento (EER)

Na aba de modelagem (`newmodel.mwbd`), o relacionamento visual entre as três tabelas é expresso pela **Notação Pé-de-Galinha (*Crow's Foot*)**:

```
 ┌──────────────┐                                ┌──────────────┐
 │   CLIENTES   │                                │   PRODUTO    │
 ├──────────────┤                                ├──────────────┤
 │PK ID_CLIENTE │                                │PK ID_PRODUTO │
 └──────┬───────┘                                └───────┬──────┘
        │ (1)                                            │ (1)
        │                                                │
        │                  ┌──────────────┐              │
        │                  │    VENDAS    │              │
        │                  ├──────────────┤              │
        └─────────────────<│PK ID_VENDA   │>─────────────┘
                     (N)   │FK ID_CLIENTE │   (N)
                           │FK ID_PRODUTO │
                           └──────────────┘
```

#### As Duas Cardinalidades do Star Schema:
1. **`CLIENTES (1) ----< (N) VENDAS`**:
   - **Regra**: **1 para N (Um para Muitos)**.
   - **Leitura**: Um cliente cadastrado pode realizar **zero ou muitas vendas** ao longo da vida (`0..N`). No entanto, cada linha da tabela de vendas pertence compulsoriamente a **exatamente um cliente** (`1..1`).
   - **O Pé-de-Galinha**: Fica virado para o lado da tabela `VENDAS` (o lado do "Muitos").

2. **`PRODUTO (1) ----< (N) VENDAS`**:
   - **Regra**: **1 para N (Um para Muitos)**.
   - **Leitura**: Um produto cadastrado no catálogo pode constar em **zero ou muitas vendas** (`0..N`). Cada transação de venda registrada nesta tabela fato refere-se a **exatamente um produto** (`1..1`).
   - **O Pé-de-Galinha**: Também fica virado para o lado da tabela `VENDAS`.

#### Ponto Crítico de Arquitetura (OLTP vs. Star Schema OLAP):
- Em um sistema transacional corporativo real (**OLTP** / E-commerce), um "Pedido" pode conter **vários produtos** ao mesmo tempo (arroz, feijão e carne na mesma nota). Isso cria uma relação de muitos-para-muitos (**N:N**) entre Pedido e Produto, exigindo uma tabela associativa intermediária: `PEDIDOS (1) -> (N) ITENS_PEDIDO (N) <- (1) PRODUTOS`.
- No modelo simplificado da aula (Star Schema dimensional analítico), cada linha da tabela `VENDAS` já representa um **fato atômico** (um item vendido com sua quantidade e valor), permitindo a conexão direta 1:N com `PRODUTO`.

---

## 5. Próximos Passos e Integrações

- [ ] Testar conexão no MySQL Workbench utilizando as credenciais salvas.
- [ ] Executar scripts de criação de schema e tabelas propostos.
- [ ] Mapear futura integração com Java via JDBC / DAO.
