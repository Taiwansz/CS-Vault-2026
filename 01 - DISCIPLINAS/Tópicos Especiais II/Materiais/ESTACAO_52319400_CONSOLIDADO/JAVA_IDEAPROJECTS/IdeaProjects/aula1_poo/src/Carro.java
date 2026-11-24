public class Carro {
    // caracteristicas - atributos
    private String modelo;
    private String cor;
    private int ano;
    private int velocidadeAtual = 0;
    private String placa;

    // método construtor
    public Carro(String modelo, String cor, int ano, String placa){
        // this.atributo = parâmetro
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

    // ações - métodos
    public void acelerar(){
        velocidadeAtual = velocidadeAtual + 10;
        System.out.println("O "+ modelo +" acelerou!");

    }
}
