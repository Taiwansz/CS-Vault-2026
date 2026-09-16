public class Lutador {
    private String nome;
    private String modalidade;
    private int vitoria;

    public Lutador(String nome, String modalidade, int vitoria) {
        this.nome = nome;
        this.modalidade = modalidade;
        this.vitoria = vitoria;
    }

    public void exibirDados(){
        System.out.println("Atleta: "+nome+"| Modalidade: "+modalidade+"|Vitórias: "+vitoria);
    }

    public String salvarDados(){
        return this.nome+";"+this.modalidade+";"+this.vitoria;
    }
}