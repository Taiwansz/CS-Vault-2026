public class BracoRobotico extends Maquina {
    public BracoRobotico(String nome, int potencia) {
        super(nome, potencia);
    }

    @Override
    public void operar() {
        System.out.println(getNome() + " soldando com precisão!");
    }
}
