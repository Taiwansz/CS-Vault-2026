public class Esteira extends Maquina {
    private double velocidade;

    public Esteira(String nome, int potencia, double velocidade) {
        super(nome, potencia);
        this.velocidade = velocidade;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    @Override
    public void operar() {
        System.out.println(getNome() + " movimentando esteira a " + velocidade + " m/s!");
    }
}
