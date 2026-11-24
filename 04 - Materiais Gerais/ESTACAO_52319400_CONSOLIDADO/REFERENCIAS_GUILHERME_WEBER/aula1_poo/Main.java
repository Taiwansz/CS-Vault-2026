
public class Main {
    public static void main(String[] args) {
        ContaBancaria conta1 = new ContaBancaria(
                "Guilherme",
                "Nubank",
                "1234",
                "Indaiatuba");

        conta1.depositar(200);
        conta1.sacar(950);
    }

}