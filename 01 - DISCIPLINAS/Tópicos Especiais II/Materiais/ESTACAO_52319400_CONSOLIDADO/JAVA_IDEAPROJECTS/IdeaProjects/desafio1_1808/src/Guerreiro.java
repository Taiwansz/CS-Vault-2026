import java.util.Random;

public class Guerreiro extends Personagem implements Magia {

    public Guerreiro(String nome, int vida, int pontosAtaque) {
        super(nome, vida, pontosAtaque);
    }

    @Override
    public void curar() {
        System.out.println("O guerreiro não sabe usar magias");
    }

    @Override
    public void fireBall(Personagem alvo) {
        System.out.println("O guerreiro não sabe usar magias");

    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("O guerreiro atacou!");
        int danoCausado = this.pontosAtaque;
        //desafio - implementar ataque crítico quando maior que 100
        Random random = new Random();
        int chance = random.nextInt(110);
        alvo.receberDano(danoCausado);

    }
}
