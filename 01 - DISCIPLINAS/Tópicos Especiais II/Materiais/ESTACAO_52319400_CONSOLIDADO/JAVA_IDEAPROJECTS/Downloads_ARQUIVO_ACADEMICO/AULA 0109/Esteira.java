// MISSÃO 3 — Esteira Linha A
// A classe Maquina (com construtor) já existe no projeto.
// TODO: 1) faça Esteira HERDAR de Maquina
//       2) atributo privado: double velocidade
//       3) construtor (String nome, int potencia, double velocidade)
//          chamando super(nome, potencia)
//       4) getter getVelocidade()
public class Esteira extends Maquina{
    private double velocidade

    public Esteira(String nome, int potencia, double velocidade) {
        super(nome, potencia);
        this.velocidade = velocidade;
    }

    public double getVelocidade() {
        return velocidade;
    }
}