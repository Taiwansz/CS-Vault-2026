package dao;

import model.Aluno;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de persistencia e acesso a dados para a entidade Aluno.
 * Implementa o padrao DAO (Data Access Object).
 */
public interface AlunoDAO {

    /**
     * Insere um novo aluno na tabela alunos e atribui o ID gerado automaticamente.
     *
     * @param aluno Objeto contendo os dados a serem persistidos
     * @return Aluno persistido com seu respectivo ID preenchido
     */
    Aluno salvar(Aluno aluno);

    /**
     * Retorna todos os alunos registrados na base de dados.
     *
     * @return Lista de alunos cadastrados
     */
    List<Aluno> listarTodos();

    /**
     * Busca um aluno pelo seu identificador primario.
     *
     * @param id Identificador unico do aluno
     * @return Optional contendo o aluno se localizado, ou vazio caso contrario
     */
    Optional<Aluno> buscarPorId(int id);

    /**
     * Atualiza os dados de um aluno ja existente.
     *
     * @param aluno Objeto com dados atualizados
     * @return true se atualizado com sucesso, false caso o registro nao exista
     */
    boolean atualizar(Aluno aluno);

    /**
     * Remove um aluno pelo seu ID.
     *
     * @param id Identificador do aluno a ser removido
     * @return true se a remocao afetou 1 linha, false caso contrario
     */
    boolean deletarPorId(int id);
}
