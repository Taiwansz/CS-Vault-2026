//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Carro carroDoVitor = new Carro("Honda Fit", "Cinza", 2014, "ABC-1234");
        Carro carroDoGustavo = new Carro("Onix", "Azul", 2020, "QWE-1234");

        carroDoGustavo.acelerar();
        carroDoGustavo.acelerar();
        carroDoVitor.acelerar();
        carroDoGustavo.acelerar();
    }
}