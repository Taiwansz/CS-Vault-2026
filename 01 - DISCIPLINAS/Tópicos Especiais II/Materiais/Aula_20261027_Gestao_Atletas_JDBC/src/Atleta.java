// MISSÃO 2 — Ficha Técnica (Model)
// 1) Declare atributos privados: id (int), nome (String), posicao (String), idade (int), salario (double), status (String)
// 2) Crie o construtor completo Atleta(int id, String nome, String posicao, int idade, double salario, String status)
// 3) Crie os getters públicos getNome() e getSalario()

public class Atleta {
    // 1) Atributos privados
    private int id;
    private String nome;
    private String posicao;
    private int idade;
    private double salario;
    private String status;

    // 2) Construtor completo
    public Atleta(int id, String nome, String posicao, int idade, double salario, String status) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.idade = idade;
        this.salario = salario;
        this.status = status;
    }

    // 3) Getters publicos requeridos
    public String getNome() {
        return nome;
    }

    public double getSalario() {
        return salario;
    }

    // Getters complementares para persistencia relacional / DAO
    public int getId() {
        return id;
    }

    public String getPosicao() {
        return posicao;
    }

    public int getIdade() {
        return idade;
    }

    public String getStatus() {
        return status;
    }

    // Setters caso necessarios na hidratacao de objetos do ResultSet
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", posicao='" + posicao + '\'' +
                ", idade=" + idade +
                ", salario=" + salario +
                ", status='" + status + '\'' +
                '}';
    }
}
