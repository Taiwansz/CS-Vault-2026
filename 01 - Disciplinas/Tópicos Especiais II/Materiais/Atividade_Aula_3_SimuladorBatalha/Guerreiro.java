import java.util.Random;

public class Guerreiro extends Personagem implements Magia {

    public Guerreiro(String nome, int vida, int pontosAtaque) {
        super(nome, vida, pontosAtaque);
    }

    @Override
    public void curar() {
        System.out.println("O guerreiro não sabe usar magias de cura.");
    }

    @Override
    public void fireBall(Personagem alvo) {
        System.out.println("O guerreiro não sabe conjurar bolas de fogo.");
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("O guerreiro " + this.nome + " atacou com sua espada!");
        int danoCausado = this.pontosAtaque;

        // Mecânica de ataque crítico
        Random random = new Random();
        int chance = random.nextInt(100);
        if (chance > 70) {
            danoCausado += 10;
            System.out.println("Golpe crítico desferido! (+10 de dano adicional)");
        }

        alvo.receberDano(danoCausado);
    }
}
