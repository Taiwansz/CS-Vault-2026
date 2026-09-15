public interface Magia {
    /**
     * Permite ao personagem recuperar pontos de vida.
     */
    void curar();

    /**
     * Permite conjurar feitiço ofensivo sobre um alvo.
     * @param alvo Personagem que receberá o dano mágico.
     */
    void fireBall(Personagem alvo);
}
