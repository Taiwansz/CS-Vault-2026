// MISSÃO 6 — Rede de Sensores
// TODO: 1) interface Monitoravel com o método: double lerTemperatura();
//       2) classe SensorTermico implements Monitoravel
//          com @Override retornando 87.5

// (pode escrever a interface e a classe no mesmo arquivo)

public interface Monitoravel{
    public double lerTemperatura();

}

public class SensorTermico implements Monitoravel{
    @Override
    double lerTemperatura() {
        return 87.5;
    }
}