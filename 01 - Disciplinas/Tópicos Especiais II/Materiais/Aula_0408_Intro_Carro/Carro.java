public class Carro {
    // Atributos de estado do objeto
    private String modelo;
    private String cor;
    private int ano;
    private int velocidadeAtual = 0;
    private String placa;

    // Construtor
    public Carro(String modelo, String cor, int ano, String placa) {
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.placa = placa;
        this.velocidadeAtual = 0;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public void setVelocidadeAtual(int velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    // Ações e comportamentos
    public void acelerar() {
        this.velocidadeAtual += 10;
        System.out.println("O " + modelo + " acelerou para " + velocidadeAtual + " km/h!");
    }

    public void frear() {
        if (this.velocidadeAtual >= 10) {
            this.velocidadeAtual -= 10;
        } else {
            this.velocidadeAtual = 0;
        }
        System.out.println("O " + modelo + " reduziu para " + velocidadeAtual + " km/h.");
    }
}
