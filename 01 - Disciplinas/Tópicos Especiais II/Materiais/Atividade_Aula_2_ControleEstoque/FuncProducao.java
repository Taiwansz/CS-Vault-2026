// Subclasse de Funcionario voltada para a produção no galpão
public class FuncProducao extends Funcionario {
    private String turno;

    public FuncProducao(String nome, double salario, String turno) {
        super(nome, salario);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void operar() {
        System.out.println("🏭 [PRODUÇÃO] " + nome + " está operando as máquinas e empilhadeiras do galpão no turno da " + turno + ".");
    }

    @Override
    public void trabalhar() {
        System.out.println("⚙️ [TRABALHAR] " + nome + " (Produção - Turno: " + turno + ") está organizando paletes e separando cargas.");
    }
}