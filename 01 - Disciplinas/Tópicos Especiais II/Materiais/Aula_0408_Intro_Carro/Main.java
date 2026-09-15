public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCICIO INTRODUTORIO DE POO: CARROS ===");

        Carro carroDoVitor = new Carro("Honda Fit", "Cinza", 2014, "ABC-1234");
        Carro carroDoGustavo = new Carro("Onix", "Azul", 2020, "QWE-1234");

        System.out.println("Carro 1: " + carroDoVitor.getModelo() + " | Cor: " + carroDoVitor.getCor() + " | Ano: " + carroDoVitor.getAno());
        System.out.println("Carro 2: " + carroDoGustavo.getModelo() + " | Cor: " + carroDoGustavo.getCor() + " | Ano: " + carroDoGustavo.getAno());

        System.out.println("\nTestando aceleração:");
        carroDoVitor.acelerar();
        carroDoVitor.acelerar();
        carroDoGustavo.acelerar();

        System.out.println("\nTestando frenagem:");
        carroDoVitor.frear();
    }
}
