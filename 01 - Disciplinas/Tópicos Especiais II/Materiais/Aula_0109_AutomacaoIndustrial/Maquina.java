public class Maquina {
    private String nome;
    private int potencia;

    public Maquina(String nome, int potencia) {
        this.nome = nome;
        this.potencia = potencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public void operar() {
        System.out.println(nome + " operando em modo padrão.");
    }
}
