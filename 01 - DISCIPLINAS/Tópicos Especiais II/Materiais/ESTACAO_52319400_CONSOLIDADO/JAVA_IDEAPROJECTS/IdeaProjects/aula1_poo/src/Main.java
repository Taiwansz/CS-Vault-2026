//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Carro carroDoVitor = new Carro(mocelo:"Honda Fit", cor:"Cinza", ano:2014, placa:"ABC-1234");
        Carro carroDoGustavo = new Carro(modelo:"Onix",cor:"Azul",ano:"2020",placa:"QWE-1234");

        carroDoGustavo.acelerar();
        carroDoGustavo.acelerar();
        carroDoVitor.acelerar();
        carroDoGustavo.acelerar();

    }
}