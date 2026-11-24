import java.util.ArrayList;

public class PainelDeControle {
    public static void main(String[] args) {

        ArrayList<Maquina> linha = new ArrayList<>();

        linha.add(new Maquina("Gerador Central", 5000));
        linha.add(new Esteira("Linha A", 800, 1.8));
        linha.add(new BracoRobotico("BR-01", 1500));

        for(Maquina m : linha) {
            m.operar();
        }

    }
}