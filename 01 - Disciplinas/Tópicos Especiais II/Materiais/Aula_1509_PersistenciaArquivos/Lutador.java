public class Lutador {
    private String nome;
    private String modalidade;
    private int vitoria;

    public Lutador(String nome, String modalidade, int vitoria) {
        this.nome = nome;
        this.modalidade = modalidade;
        this.vitoria = vitoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public int getVitoria() {
        return vitoria;
    }

    public void setVitoria(int vitoria) {
        this.vitoria = vitoria;
    }

    public void exibirDados() {
        System.out.println("Atleta: " + nome + " | Modalidade: " + modalidade + " | Vitórias: " + vitoria);
    }

    public String salvarDados() {
        return this.nome + ";" + this.modalidade + ";" + this.vitoria;
    }

    public static Lutador fromCsv(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 3) {
            String nome = partes[0].trim();
            String modalidade = partes[1].trim();
            int vitoria = Integer.parseInt(partes[2].trim());
            return new Lutador(nome, modalidade, vitoria);
        }
        return null;
    }
}
