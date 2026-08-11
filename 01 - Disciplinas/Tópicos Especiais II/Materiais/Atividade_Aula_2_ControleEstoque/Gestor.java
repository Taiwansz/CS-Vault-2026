// Subclasse de Funcionario responsável pela gestão do galpão
public class Gestor extends Funcionario {

    public Gestor(String nome, double salario) {
        super(nome, salario);
    }

    public void atribuirTarefas() {
        System.out.println("📋 [GESTÃO] O Gestor " + nome + " está distribuindo a lista de tarefas e organizando a escala do galpão.");
    }

    @Override
    public void trabalhar() {
        System.out.println("📊 [TRABALHAR] O Gestor " + nome + " está realizando a auditoria do inventário e emitindo relatórios.");
    }
}