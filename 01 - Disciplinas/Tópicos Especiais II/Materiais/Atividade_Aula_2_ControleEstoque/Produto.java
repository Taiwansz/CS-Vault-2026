public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

    // Construtor: obriga a inicializar nome e preco, estoque inicia sempre com zero
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEmEstoque += quantidade;
            System.out.println("➕ " + quantidade + " unidade(s) de \"" + nome + "\" adicionada(s) ao estoque.");
        } else {
            System.out.println("⚠️ Quantidade inválida para adição.");
        }
    }

    public void removerEstoque(int quantidade) {
        if (quantidade > 0 && quantidade <= this.quantidadeEmEstoque) {
            this.quantidadeEmEstoque -= quantidade;
            System.out.println("➖ " + quantidade + " unidade(s) de \"" + nome + "\" removida(s) do estoque.");
        } else {
            System.out.println("⚠️ Quantidade insuficiente em estoque ou valor inválido.");
        }
    }

    public void exibirInformacoes() {
        System.out.printf("📦 Produto: %-32s | Preço: R$ %8.2f | Estoque: %d unidade(s)%n", 
                          nome, preco, quantidadeEmEstoque);
    }
}