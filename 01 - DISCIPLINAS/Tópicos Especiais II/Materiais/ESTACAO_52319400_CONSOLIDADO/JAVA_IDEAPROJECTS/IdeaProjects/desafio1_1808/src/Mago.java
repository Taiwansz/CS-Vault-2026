public class Mago extends Personagem implements Magia {
    private final int VIDA_MAXIMA;

    public Mago(String nome, int vida, int pontosAtaque){
        super(nome, vida, pontosAtaque);
        this.VIDA_MAXIMA = vida;
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("o mago tacou um livro no oponente");
        System.out.println("o oponente era "+ alvo.getNome());
    }

    @Override
    public void curar() {
        this.vida += 20;
        if(this.vida > this.VIDA_MAXIMA){
            this.vida = this.VIDA_MAXIMA;
        }
        System.out.println(this.nome + "usou uma poção");
    }

    @Override
    public void fireBall(Personagem alvo) {
        System.out.println("o mago usou bola de fogo no ");
        System.out.println(alvo.getNome());
        alvo.receberDano(this.pontosAtaque);
    }
}
