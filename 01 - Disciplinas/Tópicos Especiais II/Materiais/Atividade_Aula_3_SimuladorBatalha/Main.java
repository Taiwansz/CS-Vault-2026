public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  ARENA DE BATALHA: REINO DE JAVALAND    ");
        System.out.println("=========================================");

        Guerreiro guerreiro = new Guerreiro("Pineti", 100, 25);
        Mago mago = new Mago("Juan", 80, 30);

        int turno = 1;

        while (guerreiro.estaVivo() && mago.estaVivo()) {
            System.out.println("\n--- TURNO " + turno + " ---");

            // Turno do Guerreiro
            guerreiro.atacar(mago);

            if (!mago.estaVivo()) {
                System.out.println("\nO Mago " + mago.getNome() + " foi derrotado!");
                break;
            }

            // Turno do Mago
            if (mago.getVida() > 30) {
                mago.fireBall(guerreiro);
            } else {
                mago.curar();
            }

            if (!guerreiro.estaVivo()) {
                System.out.println("\nO Guerreiro " + guerreiro.getNome() + " caiu em combate!");
                break;
            }

            turno++;
        }

        System.out.println("\n=========================================");
        System.out.println("            FIM DE COMBATE               ");
        System.out.println("=========================================");
        if (guerreiro.estaVivo()) {
            System.out.println("Vencedor: Guerreiro " + guerreiro.getNome());
        } else {
            System.out.println("Vencedor: Mago " + mago.getNome());
        }
    }
}
