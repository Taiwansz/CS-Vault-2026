public class ContaBancaria {
    String nome, banco, cpf, cidade;
    double saldo = 1500;

    public ContaBancaria(String nome, String banco, String cpf, String cidade){
        this.nome = nome;
        this.banco = banco;
        this.cpf = cpf;
        this.cidade = cidade;
    }

    public void depositar(double valor) {
        this.saldo = this.saldo + valor;
        System.out.println("Valor R$" + valor + " Depositado com Sucesso! \n Novo Saldo: R$" + this.saldo);
    }

    public void sacar(double valor) {
        this.saldo = this.saldo - valor;
        System.out.println("Valor R$" + valor + " Retirado com Sucesso! \n Novo Saldo: R$" + this.saldo);
    }
}
