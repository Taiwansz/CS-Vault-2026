---
tipo: atividade
disciplina: Tópicos Especiais II
data: 2026-08-04
professor: Luiz Claudio Chiavini Oliveira Junior
status: entregue
link-github: https://github.com/Taiwansz/aula-Luyz
---

# 💻 Atividade 01 — Criar Classe ContaBancaria e Construtor (Java)

> [!success] 🚀 Status: Entregue no GitHub
> **Link do Repositório:** [github.com/Taiwansz/aula-Luyz](https://github.com/Taiwansz/aula-Luyz)  
> **Professor:** Luiz Claudio Chiavini Oliveira Junior  
> **Linguagem:** Java

---

## 📝 Enunciado da Atividade

1. **Criar a classe `ContaBancaria`:**
   - Adicionar ao menos 5 atributos diferentes (`titular`, `numeroConta`, `agencia`, `cpf`, `saldo`).
   - Definir o atributo `saldo` inicial com valor **1500**.
2. **Implementar Método Construtor:**
   - Receber os parâmetros para inicializar os atributos da conta.
3. **Implementar Métodos Operacionais:**
   - `depositar(double valor)`: soma ao saldo atual.
   - `sacar(double valor)`: subtrai do saldo atual (verificando saldo suficiente).
4. **Criar Classe `Main`:**
   - Instanciar objetos de cliente, exibir os dados e testar as operações de depósito e saque.

---

## ☕ Código Fonte Implementado

### 1. `ContaBancaria.java`

```java
public class ContaBancaria {

    String titular;
    String numeroConta;
    String agencia;
    String cpf;
    double saldo = 1500;

    // Metodo construtor
    public ContaBancaria(String titular, String numeroConta, String agencia, String cpf){
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.cpf = cpf;
        this.saldo = 1500;
    }

    public void depositar(double valor){
        saldo = saldo + valor;
        System.out.println("Deposito de " + valor + " realizado!");
    }

    public void sacar(double valor){
        if (valor <= saldo){
            saldo = saldo - valor;
            System.out.println("Saque de " + valor + " realizado!");
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }
}
```

---

### 2. `Main.java`

```java
public class Main {
    public static void main(String[] args) {

        ContaBancaria contaDoMatheus = new ContaBancaria("Matheus Sousa", "12345-6", "0001", "123.456.789-00");
        ContaBancaria contaDoFelipe = new ContaBancaria("Felipe Pinete", "65432-1", "0001", "987.654.321-11");
        ContaBancaria contaDoLuyz = new ContaBancaria("Luyz Chavoso", "99999-9", "0002", "555.444.333-22");

        System.out.println("=== Dados do Cliente ===");
        System.out.println("Titular: " + contaDoMatheus.titular);
        System.out.println("Numero da Conta: " + contaDoMatheus.numeroConta);
        System.out.println("Agencia: " + contaDoMatheus.agencia);
        System.out.println("CPF: " + contaDoMatheus.cpf);
        System.out.println("Saldo: R$ " + contaDoMatheus.saldo);
        System.out.println();

        contaDoMatheus.depositar(500);
        System.out.println("Saldo atual do Matheus: R$ " + contaDoMatheus.saldo);
        System.out.println();

        contaDoFelipe.sacar(300);
        System.out.println("Saldo atual do Felipe: R$ " + contaDoFelipe.saldo);
        System.out.println();

        contaDoLuyz.depositar(1000);
        System.out.println("Saldo atual do Luyz: R$ " + contaDoLuyz.saldo);
    }
}
```

---

## 🔗 Arquivos Locais no Cofre
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_1_ContaBancaria/ContaBancaria.java|ContaBancaria.java]]
- 📄 [[01 - Disciplinas/Tópicos Especiais II/Materiais/Atividade_Aula_1_ContaBancaria/Main.java|Main.java]]
