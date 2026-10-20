---
tipo: aula
disciplina: "Tópicos Especiais II"
data: 2026-10-20
assunto: "Integração Java e Banco de Dados Relacional via JDBC (Java Database Connectivity), MySQL Connector/J, PreparedStatement e Padrão DAO"
professor: "Luiz Claudio Chiavini Oliveira Junior"
status: concluido
---

# Aula 20/10 — Integração Java e Banco de Dados com JDBC

> [!info] Informações da Aula
> - **Data:** 20/10/2026
> - **Disciplina:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior (Luyz)
> - **Foco:** Ponte aplicacional entre Java SE e SGBD Relacional (MySQL), configuração do driver MySQL Connector/J 8.4.0, gerenciamento de conexões TCP via `DriverManager`, parametrização segura com `PreparedStatement` e leitura de cursores via `ResultSet`.
> - **Repositório de Código Local:** `C:\Users\52319400\IdeaProjects\AULA2010`
> - **Materiais no Cofre:** [[Aula_20261020_Java_JDBC]]

---

## 1. Fundamentos Arquiteturais do JDBC (*Java Database Connectivity*)

A arquitetura JDBC constitui a camada canônica de abstração da plataforma Java para comunicação uniforme com Sistemas de Gerenciamento de Banco de Dados Relacionais (SGBD). Ela atua como uma interface de baixo nível baseada na especificação do Service Provider Interface (SPI).

```
+-------------------------------------------------------------+
|                 Aplicacao Java (POO / Negocio)              |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                     JDBC API (java.sql)                     |
|        (Connection, PreparedStatement, Statement, ResultSet)|
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                     DriverManager SPI                       |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|            Driver MySQL Connector/J (com.mysql.cj.jdbc)     |
+-------------------------------------------------------------+
                              |
                              v  (Socket TCP / Porta 3306)
+-------------------------------------------------------------+
|                Servidor MySQL 8.x (Daemon SGBD)             |
+-------------------------------------------------------------+
```

### 1.1. O Papel do Driver e do Classpath
O Java puro desconhece o protocolo binário de rede do MySQL (ou de qualquer outro fabricante). O pacote `mysql-connector-j-8.4.0.jar` fornece a implementação concreta das interfaces de `java.sql`. 
- **Driver Class:** `com.mysql.cj.jdbc.Driver`
- **String de Conexão (JDBC URL):** `jdbc:mysql://localhost:3306/aula_java`
  - `jdbc`: Protocolo mestre.
  - `mysql`: Subprotocolo correspondente ao conector.
  - `localhost:3306`: Host de rede e porta TCP do daemon MySQL.
  - `/aula_java`: Database/Schema alvo da sessão.

---

## 2. Anatomia do Código Desenvolvido em Aula

O professor orientou a construção de dois módulos procedimentais diretos dentro do projeto `AULA2010`: um para inserção de dados (`Main.java`) e outro para consulta e iteração sobre o banco (`VerDados.java`).

### 2.1. Inserção Parametrizada (`Main.java`)

```java
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/aula_java";
        String user = "root";
        String password = "root753";

        String sql = "insert into alunos(nome, idade) values(?,?)";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1,"Matheus");
            psmt.setInt(2,21);

            int linhasAfetadas = psmt.executeUpdate();
            System.out.println("Cadastrado");

        } catch (SQLException e){
            System.out.println("Erro ao cadastrar aluno" + e.getMessage() );
        }
    }
}
```

#### Aspectos Positivos Implementados:
1. **`PreparedStatement` com Marcadores Posicionais (`?`):** Evita concatenação crua de Strings (`"insert into ... '" + nome + "'"`), eliminando a vulnerabilidade elementar de *SQL Injection*. O SGBD compila o plano de execução da query previamente antes de receber os literais.
2. **Gerenciamento de Recursos com `try-with-resources`:** As instâncias de `Connection` e `PreparedStatement` implementam `java.lang.AutoCloseable`. Declaradas no cabeçalho do bloco `try(...)`, a JVM assegura o fechamento compulsório dos sockets e cursores mesmo em caso de lançamento de `SQLException`.

### 2.2. Leitura de Cursores e Iteração (`VerDados.java`)

```java
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerDados {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/aula_java";
        String user = "root";
        String password = "root753";

        String sql = "select * from alunos";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery()){

                System.out.println("Dados da tabela: ");
                while(rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    System.out.println(id + " " + nome + " "+ idade);
                }

        } catch (SQLException e){
                System.out.println("ERRO: "+e.getMessage());
        }
    }
}
```

#### Dinâmica de Execução:
- **`psmt.executeQuery()`:** Dispara a consulta DQL e aloca um buffer de cursor (`ResultSet`) apontando inicialmente para a posição imediatamente anterior ao primeiro registro.
- **`rs.next()`:** Desloca o ponteiro para a próxima linha da tabela. Retorna `true` enquanto houver tuplas e `false` quando atingir o fim da relação.
- **Extração Tipada:** `getInt("id")`, `getString("nome")`, `getInt("idade")` realizam a conversão do tipo relacional (SQL) para os primitivos e objetos do Java.

---

## 3. Inspeção Forense do Banco de Dados Local (`aula_java`)

Auditoria direta realizada na instância MySQL 8.0 local (`localhost:3306`):

### 3.1. DDL Real Extraído do SGBD
```sql
CREATE DATABASE IF NOT EXISTS `aula_java` 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_0900_ai_ci;

USE `aula_java`;

CREATE TABLE `alunos` (
    `id` int NOT NULL AUTO_INCREMENT,
    `nome` varchar(255) NOT NULL,
    `idade` int DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

### 3.2. Registros Persistidos no Disco
```text
+----+---------+-------+
| id | nome    | idade |
+----+---------+-------+
|  1 | Matheus |    21 |
+----+---------+-------+
1 row in set (0.00 sec)
```

---

## 4. Análise Crítica e Falhas Arquiteturais do Código Didático

Embora o código cumpra a função pedagógica imediata de introduzir o JDBC, ele apresenta falhas severas de engenharia de software sob a ótica de sistemas corporativos e de produção.

### 4.1. Acoplamento e Strings Hardcoded
As credenciais de acesso (`root` / `root753`) e a URL do banco estão duplicadas tanto no `Main.java` quanto no `VerDados.java`. Se o IP do servidor ou a senha mudarem, exige-se recompilação de código em múltiplos pontos.
- **Solução Canônica:** Centralização em uma classe Factory (`ConnectionFactory`) ou injeção via variáveis de ambiente (`System.getenv()`).

### 4.2. Ausência de Modelo de Domínio (POJO / Entidade)
O código manipula variáveis soltas (`int id`, `String nome`, `int idade`) em vez de instanciar e trafegar um objeto `Aluno`. Isso quebra o encapsulamento da Orientação a Objetos e transforma o código Java em scripts procedimentais simples.

### 4.3. Falta do Padrão DAO (*Data Access Object*)
As queries SQL e os comandos de persistência estão inseridos diretamente dentro do método `main`. Em uma arquitetura real, o método de inicialização ou o controller nunca deve tocar diretamente em SQL. A persistência deve ser delegada a uma camada isolada de acesso a dados.

### 4.4. Armadilha do Primitivo `int` com Valores SQL `NULL`
Na tabela `alunos`, a coluna `idade` aceita `NULL` (`idade int DEFAULT NULL`).
No entanto, no código do `VerDados.java`, utiliza-se o primitivo:
```java
int idade = rs.getInt("idade");
```
Na especificação JDBC, quando uma coluna contém `SQL NULL`, o método `rs.getInt()` retorna o valor default do primitivo em Java: **`0`**. Ou seja, um aluno cuja idade não foi informada passará a ter idade 0 anos, corrompendo o significado do dado de domínio.
- **Solução Canônica:** Utilizar o wrapper `Integer` associado à checagem `rs.wasNull()` ou `rs.getObject("idade", Integer.class)`.

### 4.5. Overhead de Abertura de Conexões TCP (Ausência de Pool)
Para cada inserção ou consulta, o código executa `DriverManager.getConnection()`. Esta chamada realiza um handshake TCP completo de três vias (SYN, SYN-ACK, ACK), negociação TLS/SSL e autenticação de usuário no MySQL.
- **Custo Operacional:** Em alta carga, abrir conexões a cada requisição esgota as portas efêmeras do sistema operacional e degrada a latência. Em ambientes profissionais, utiliza-se um Pool de Conexões (como HikariCP ou Apache DBCP).

---

## 5. Arquitetura Refatorada de Alta Fidelidade (Padrão DAO)

Para elevar o código ao padrão de engenharia sagital, o projeto foi refatorado na pasta de materiais:

```
Aula_20261020_Java_JDBC/
├── schema_aula_java.sql
└── src/
    ├── Main.java               (Original de aula)
    ├── VerDados.java           (Original de aula)
    ├── AppRefatorado.java      (Executavel refatorado)
    ├── model/
    │   └── Aluno.java          (Entidade de Dominio)
    ├── factory/
    │   └── ConnectionFactory.java (Provedor central de conexao)
    └── dao/
        ├── AlunoDAO.java       (Contrato de interface)
        └── AlunoDAOImpl.java   (Implementacao defensiva)
```

### 5.1. Entidade de Domínio (`model/Aluno.java`)
Encapsula o estado, validações de negócio e suporta `idade` como `Integer` nulo.

### 5.2. Fábrica de Conexões (`factory/ConnectionFactory.java`)
Centraliza os parâmetros de conexão e otimiza a URL com `characterEncoding=UTF-8` e `serverTimezone=UTC`.

### 5.3. Camada de Persistência (`dao/AlunoDAOImpl.java`)
- Captura de chaves primárias geradas automaticamente via `Statement.RETURN_GENERATED_KEYS`.
- Utilização de `ps.setNull(2, Types.INTEGER)` quando a idade for omitida.
- Leitura defensiva com `rs.wasNull() ? null : rs.getInt("idade")`.

---

## 6. Conclusão e Próximos Passos na Ementa

A aula de hoje consolidou a transição da persistência em memória e arquivos planos para a persistência transacional corporativa. 

**Evolução Prevista:**
1. Evolução dos exercícios didáticos para a aplicação formal do padrão DAO nos próximos projetos práticos;
2. Introdução de operações de atualização (`UPDATE`) e deleção (`DELETE`) com tratamento de chaves estrangeiras;
3. Integração com interfaces gráficas (Swing / JavaFX) consumindo a camada DAO.
