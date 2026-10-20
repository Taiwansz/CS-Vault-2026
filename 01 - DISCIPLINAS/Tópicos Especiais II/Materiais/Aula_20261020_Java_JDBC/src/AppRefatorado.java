import dao.AlunoDAO;
import dao.AlunoDAOImpl;
import model.Aluno;

import java.util.List;

/**
 * Ponto de entrada demonstrando a utilizacao do padrao DAO e ConnectionFactory.
 */
public class AppRefatorado {

    public static void main(String[] args) {
        System.out.println("=== THSYR // EXECUCAO ARQUITETURALMENTE ROBUSTA (PADRAO DAO) ===");
        AlunoDAO alunoDAO = new AlunoDAOImpl();

        // 1. Insercao controlada
        Aluno novoAluno = new Aluno("Matheus Sousa", 21);
        alunoDAO.salvar(novoAluno);
        System.out.println("Aluno persistido com sucesso: " + novoAluno);

        // 2. Consulta de todos os registros
        System.out.println("\n--- Lista Consolidada no Banco ---");
        List<Aluno> alunos = alunoDAO.listarTodos();
        for (Aluno a : alunos) {
            System.out.println(" -> " + a);
        }

        // 3. Busca pontual
        if (novoAluno.getId() != null) {
            alunoDAO.buscarPorId(novoAluno.getId()).ifPresent(a -> 
                System.out.println("\nBusca por ID (" + a.getId() + "): " + a)
            );
        }
    }
}
