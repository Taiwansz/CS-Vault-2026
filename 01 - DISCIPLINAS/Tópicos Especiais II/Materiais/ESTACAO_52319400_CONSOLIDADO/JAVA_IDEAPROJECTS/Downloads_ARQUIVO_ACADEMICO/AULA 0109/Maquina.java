// MISSÃO 1 — Gerador Central
// TODO: 1) declare os atributos PRIVADOS: String nome, int potencia
//       2) crie getters e setters públicos para os dois
public class Maquina {

    private String nome;
    private int potencia;


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

    public Maquina(String nome, int potencia) {
        this.nome = nome;
        this.potencia = potencia;
    }
}