package model;

public enum Prioridade {
    ALTA("🔴 Alta", 3),
    MEDIA("🟡 Média", 2),
    BAIXA("🟢 Baixa", 1);

    private final String descricaoFormatada;
    private final int nivel;

    Prioridade(String descricaoFormatada, int nivel) {
        this.descricaoFormatada = descricaoFormatada;
        this.nivel = nivel;
    }

    public String getDescricaoFormatada() {
        return descricaoFormatada;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return descricaoFormatada;
    }
}
