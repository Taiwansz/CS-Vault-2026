public class Mago extends Personagem implements Magia {
    private final int VIDA_MAXIMA;

    public Mago(String nome, int vida, int pontosAtaque) {
        super(nome, vida, pontosAtaque);
        this.VIDA_MAXIMA = vida;
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("O mago " + this.nome + " lançou um ataque arcano físico contra " + alvo.getNome() + "!");
        alvo.receberDano(this.pontosAtaque / 2);
    }

    @Override
    public void curar() {
        this.vida += 20;
        if (this.vida > this.VIDA_MAXIMA) {
            this.vida = this.VIDA_MAXIMA;
        }
        System.out.println("O mago " + this.nome + " usou uma poção de cura arcana! Vida restaurada para " + this.vida + ".");
    }

    @Override
    public void fireBall(Personagem alvo) {
        System.out.println("O mago " + this.nome + " conjurou uma bola de fogo massiva contra " + alvo.getNome() + "!");
        alvo.receberDano(this.pontosAtaque);
    }
}
