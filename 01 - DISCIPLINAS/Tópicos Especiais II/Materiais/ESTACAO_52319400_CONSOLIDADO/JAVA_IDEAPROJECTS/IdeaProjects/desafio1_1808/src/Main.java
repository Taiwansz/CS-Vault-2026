public class Main {
    public static void main(String[] args) throws InterruptedException {


        System.out.println("VAMOS INICIAR A BATALHA");
        Guerreiro guerreiro = new Guerreiro("Pineti", 100, 25);
        Mago mago = new Mago("Juan", 80, 30);

        int turno = 1;

        while (guerreiro.estaVivo() && mago.estaVivo()) {
            System.out.println("turno " + turno);
            guerreiro.atacar(mago);
            Thread.sleep(1500);

            if (mago.getVida() > 30) {
                mago.fireBall(guerreiro);
            } else {
                mago.curar();
                System.out.println("O MAGO CUROU");
            }
        }
    }
}
