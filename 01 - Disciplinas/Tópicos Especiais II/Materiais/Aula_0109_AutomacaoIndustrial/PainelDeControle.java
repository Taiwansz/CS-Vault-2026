import java.util.ArrayList;

public class PainelDeControle {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PAINEL DE CONTROLE - AUTOMAÇÃO INDUSTRIAL     ");
        System.out.println("=================================================");

        // Missões 1 a 4: Maquinas e Polimorfismo
        ArrayList<Maquina> linha = new ArrayList<>();
        linha.add(new Maquina("Gerador Central", 5000));
        linha.add(new Esteira("Linha A", 800, 1.8));
        linha.add(new BracoRobotico("BR-01", 1500));

        System.out.println("\n[STATUS] Executando ciclo operacional das máquinas:");
        for (Maquina m : linha) {
            m.operar();
        }

        // Missão 5: Estação Industrial Abstrata
        System.out.println("\n[STATUS] Acionando estações industriais pesadas:");
        EstacaoIndustrial prensa = new Prensa();
        prensa.executarCiclo();

        // Missão 6: Rede de Sensores e Telemetria
        System.out.println("\n[STATUS] Coletando telemetria dos sensores térmicos:");
        Monitoravel sensor = new SensorTermico();
        System.out.println("Sensor Térmico ST-01: " + sensor.lerTemperatura() + " °C");

        System.out.println("\n=================================================");
        System.out.println("     SISTEMA INDUSTRIAL OPERANDO NORMALMENTE     ");
        System.out.println("=================================================");
    }
}
