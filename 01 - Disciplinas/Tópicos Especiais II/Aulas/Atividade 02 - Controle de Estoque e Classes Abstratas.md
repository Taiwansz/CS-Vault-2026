---
tipo: atividade
disciplina: Tópicos Especiais II
data: 2026-08-11
professor: Luiz Claudio Chiavini Oliveira Junior
status: concluido
---

# 📦 Atividade 02 — Projeto 2: Controle de Estoque e Classes Abstratas em Java

> [!info] 📌 Informações da Atividade
> - **Disciplina Hub:** [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
> - **Professor:** Luiz Claudio Chiavini Oliveira Junior
> - **Data:** 11/08/2026
> - **Foco Temático:** Classes Abstratas, Herança, Encapsulamento, Construtores com Regra de Negócio e Polimorfismo em Java.
> - **Atividade Anterior:** [[01 - Disciplinas/Tópicos Especiais II/Aulas/Atividade 01 - Conta Bancaria em Java|Atividade 01 - Conta Bancária em Java]]

---

## 🎯 Objetivo e Contexto da Atividade
Nesta atividade (*Projeto 2*), foi desenvolvido um sistema para o **controle de estoque e gestão de equipe de um galpão industrial**. 

O objetivo principal é exercitar o uso de **Classes Abstratas**, garantindo o correto encadeamento de herança, sobrescrita de métodos (`@Override`), proteção de visibilidade com encabeçamento de construtores e polimorfismo em tempo de execução.

---

## 🧠 Guia Explicativo Detalhado das Classes e Conceitos

### 1. O Princípio das Classes Abstratas (`abstract class`)
- **O que é uma Classe Abstrata?** Uma classe declarada com a palavra-chave `abstract` serve como um **modelo genérico** para outras classes. Ela **não pode ser instanciada diretamente** com a palavra-chave `new` (ou seja, você não pode fazer `new Funcionario()`).
- **Por que utilizar?** No contexto do sistema, todo funcionário no galpão possui um cargo específico (ou é da *Produção*, ou é *Gestor*). Declarar `Funcionario` como classe abstrata impede que um funcionário "genérico" sem função definida seja criado por engano no sistema.
- **Métodos Abstratos (`public abstract void trabalhar();`):** São assinaturas de métodos declarados na superclasse **sem corpo** (sem chaves `{}`). Isso **obriga obrigatoriamente** cada subclasse concreta (`FuncProducao` e `Gestor`) a fornecer sua própria implementação.

---

### 2. Detalhamento das Classes do Sistema

#### 📦 Classe 1: `Produto.java` (Entidade do Estoque)
- **Atributos:**
  - `private String nome;` — Nome de identificação do produto.
  - `private double preco;` — Valor unitário em Reais.
  - `private int quantidadeEmEstoque;` — Quantidade física guardada no galpão.
- **Regra de Negócio do Construtor:**
  - O construtor `public Produto(String nome, double preco)` exige a passagem do nome e preço.
  - **Forçamento de Estado Inicial:** O atributo `this.quantidadeEmEstoque` é inicializado rigidamente com **`0`** dentro do construtor, garantindo que nenhum produto entre no catálogo com estoque fictício antes de uma movimentação física.
- **Métodos de Controle:**
  - `adicionarEstoque(int quant)`: Incrementa o saldo de estoque validando se a quantidade inserida é maior que zero.
  - `removerEstoque(int quant)`: Decrementa o estoque validando se há saldo suficiente para evitar estoque negativo.

---

#### 👤 Classe 2: `Funcionario.java` (Superclasse Abstrata)
- **Modificador de Acesso `protected`:** Os atributos `protected String nome` e `protected double salario` permitem que as subclasses (`FuncProducao` e `Gestor`) acessem diretamente esses campos mantendo a proteção contra o mundo externo.
- **Construtor de Herança:** Fornece o construtor `public Funcionario(String nome, double salario)`, repassando a inicialização para suas subclasses filhas via `super(nome, salario)`.
- **Contrato Abstrato:** Contém a declaração `public abstract void trabalhar();` que obriga a padronização das subclasses.

---

#### 🏭 Classe 3: `FuncProducao.java` (Subclasse Concreta)
- **Herança:** Utiliza a instrução `extends Funcionario`.
- **Especialização de Atributo:** Adiciona o campo exclusivo `private String turno;` (ex: "Manhã", "Tarde", "Noite").
- **Método Específico (`operar()`)**: Executa a ação exclusiva da linha de produção (manuseio de empilhadeiras e maquinário do galpão).
- **Implementação Obrigatória (`@Override trabalhar()`)**: Define que o "trabalhar" deste colaborador consiste em movimentar e organizar paletes.

---

#### 📊 Classe 4: `Gestor.java` (Subclasse Concreta)
- **Herança:** Utiliza a instrução `extends Funcionario`.
- **Método Específico (`atribuirTarefas()`)**: Simula a distribuição de demandas e organização das escalas do galpão.
- **Implementação Obrigatória (`@Override trabalhar()`)**: Define que o "trabalhar" do gestor consiste em auditar inventário e supervisionar a equipe.

---

#### 🚀 Classe 5: `Main.java` (Testes & Polimorfismo)
- **Polimorfismo em Ação:** Demonstra como tratar objetos de classes filhas diferentes através da referência da superclasse abstrata `Funcionario`:
  ```java
  Funcionario[] equipe = { operador, gestor };
  for (Funcionario f : equipe) {
      f.trabalhar(); // A JVM identifica dinamicamente qual método executar em tempo de execução!
  }
  ```

---

## 💻 Código-Fonte Completo da Resolução

### 📁 `Produto.java`
```java
public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

    // Construtor: obriga a inicializar nome e preco, estoque inicia sempre com zero
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEmEstoque += quantidade;
            System.out.println("➕ " + quantidade + " unidade(s) de \"" + nome + "\" adicionada(s) ao estoque.");
        } else {
            System.out.println("⚠️ Quantidade inválida para adição.");
        }
    }

    public void removerEstoque(int quantidade) {
        if (quantidade > 0 && quantidade <= this.quantidadeEmEstoque) {
            this.quantidadeEmEstoque -= quantidade;
            System.out.println("➖ " + quantidade + " unidade(s) de \"" + nome + "\" removida(s) do estoque.");
        } else {
            System.out.println("⚠️ Quantidade insuficiente em estoque ou valor inválido.");
        }
    }

    public void exibirInformacoes() {
        System.out.printf("📦 Produto: %-32s | Preço: R$ %8.2f | Estoque: %d unidade(s)%n", 
                          nome, preco, quantidadeEmEstoque);
    }
}
```

---

### 📁 `Funcionario.java`
```java
// Superclasse abstrata que representa um funcionário genérico do galpão
public abstract class Funcionario {
    protected String nome;
    protected double salario;

    public Funcionario(String nome, double salario) {
        this.nome = nome;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    // Método abstrato: obriga todas as subclasses a definirem como trabalham
    public abstract void trabalhar();
}
```

---

### 📁 `FuncProducao.java`
```java
// Subclasse de Funcionario voltada para a produção no galpão
public class FuncProducao extends Funcionario {
    private String turno;

    public FuncProducao(String nome, double salario, String turno) {
        super(nome, salario);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void operar() {
        System.out.println("🏭 [PRODUÇÃO] " + nome + " está operando as máquinas e empilhadeiras do galpão no turno da " + turno + ".");
    }

    @Override
    public void trabalhar() {
        System.out.println("⚙️ [TRABALHAR] " + nome + " (Produção - Turno: " + turno + ") está organizando paletes e separando cargas.");
    }
}
```

---

### 📁 `Gestor.java`
```java
// Subclasse de Funcionario responsável pela gestão do galpão
public class Gestor extends Funcionario {

    public Gestor(String nome, double salario) {
        super(nome, salario);
    }

    public void atribuirTarefas() {
        System.out.println("📋 [GESTÃO] O Gestor " + nome + " está distribuindo a lista de tarefas e organizando a escala do galpão.");
    }

    @Override
    public void trabalhar() {
        System.out.println("📊 [TRABALHAR] O Gestor " + nome + " está realizando a auditoria do inventário e emitindo relatórios.");
    }
}
```

---

### 📁 `Main.java`
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("    🏭 PROJETO 2 - SISTEMA DE CONTROLE DE ESTOQUE DE GALPÃO");
        System.out.println("==================================================================\n");

        // 1. Teste da classe Produto (estoque inicia obrigatoriamente com 0)
        System.out.println("--- 📦 1. CADASTRO E MOVIMENTAÇÃO DE PRODUTOS ---");
        Produto p1 = new Produto("Caixa de Ferramentas Industriais", 350.00);
        Produto p2 = new Produto("Palete de Madeira Tratada", 120.50);

        System.out.println(">> Estado Inicial (Estoque deve ser 0):");
        p1.exibirInformacoes();
        p2.exibirInformacoes();

        System.out.println("\n>> Realizando Entradas no Estoque:");
        p1.adicionarEstoque(50);
        p2.adicionarEstoque(120);

        System.out.println("\n>> Estado Atualizado:");
        p1.exibirInformacoes();
        p2.exibirInformacoes();

        System.out.println("\n>> Realizando Saída do Estoque:");
        p1.removerEstoque(15);
        p1.exibirInformacoes();

        // 2. Teste da hierarquia de Funcionários (Classe Abstrata e Subclasses)
        System.out.println("\n--- 👥 2. EQUIPE DO GALPÃO (CLASSES ABSTRATAS E HERANÇA) ---");
        FuncProducao operador = new FuncProducao("Carlos Eduardo", 3200.00, "Manhã");
        Gestor gestor = new Gestor("Ana Beatriz", 7800.00);

        System.out.println(">> Métodos Específicos:");
        operador.operar();
        gestor.atribuirTarefas();

        System.out.println("\n>> Demonstração do Polimorfismo com a Classe Abstrata Funcionario:");
        Funcionario[] equipe = { operador, gestor };

        for (Funcionario f : equipe) {
            System.out.println("\nColaborador: " + f.getNome() + " | Salário: R$ " + String.format("%.2f", f.getSalario()));
            f.trabalhar(); // Chamada ao método abstrato implementado em cada classe
        }

        System.out.println("\n==================================================================");
        System.out.println("                     FIM DA EXECUÇÃO DO PROJETO 2");
        System.out.println("==================================================================");
    }
}
```

---

## 🔗 Conexões e Nódulos no Cofre (Graph View)

- 📖 Hub Central da Disciplina: [[01 - Disciplinas/Tópicos Especiais II/Tópicos Especiais II|Tópicos Especiais II]]
- 📄 Atividade Anterior: [[01 - Disciplinas/Tópicos Especiais II/Aulas/Atividade 01 - Conta Bancaria em Java|Atividade 01 - Conta Bancária em Java]]
- 📄 Aula Inicial: [[01 - Disciplinas/Tópicos Especiais II/Aulas/2026-08-04 - Aula Inaugural - POO e Diagramas|Aula Inaugural - POO e Diagramas]]
- 💻 Arquivos de Código Conectados:
  - [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Produto.java|Produto.java]]
  - [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Funcionario.java|Funcionario.java]]
  - [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/FuncProducao.java|FuncProducao.java]]
  - [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Gestor.java|Gestor.java]]
  - [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_2_ControleEstoque/Main.java|Main.java]]
