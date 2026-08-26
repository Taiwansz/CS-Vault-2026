package model;

public enum Categoria {
    ESTUDOS("📚 Estudos"),
    TRABALHO("💼 Trabalho"),
    PESSOAL("🏠 Pessoal"),
    SAUDE("💪 Saúde"),
    OUTROS("📌 Outros");

    private final String descricaoFormatada;

    Categoria(String descricaoFormatada) {
        this.descricaoFormatada = descricaoFormatada;
    }

    public String getDescricaoFormatada() {
        return descricaoFormatada;
    }

    @Override
    public String toString() {
        return descricaoFormatada;
    }
}
