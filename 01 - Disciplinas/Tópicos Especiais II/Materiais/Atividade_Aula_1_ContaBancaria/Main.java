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
