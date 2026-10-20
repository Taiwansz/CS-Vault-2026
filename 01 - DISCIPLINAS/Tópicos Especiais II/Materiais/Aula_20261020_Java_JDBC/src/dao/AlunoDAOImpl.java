package dao;

import factory.ConnectionFactory;
import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacao concreta de AlunoDAO utilizando JDBC puro e PreparedStatement.
 * Aplica gerenciamento de recursos via try-with-resources e tratamento defensivo de NULLs.
 */
public class AlunoDAOImpl implements AlunoDAO {

    private static final String SQL_INSERT = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, nome, idade FROM alunos ORDER BY id ASC";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nome, idade FROM alunos WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE alunos SET nome = ?, idade = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM alunos WHERE id = ?";

    @Override
    public Aluno salvar(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Entidade Aluno nao pode ser nula.");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, aluno.getNome());
            if (aluno.getIdade() != null) {
                ps.setInt(2, aluno.getIdade());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha na persistencia: nenhuma linha foi afetada.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha na persistencia: ID auto-increment nao retornado.");
                }
            }

            return aluno;

        } catch (SQLException e) {
            throw new RuntimeException("Erro operacional ao persistir aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro operacional ao consultar lista de alunos: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Aluno> buscarPorId(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearResultSet(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro operacional ao buscar aluno por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean atualizar(Aluno aluno) {
        if (aluno == null || aluno.getId() == null) {
            throw new IllegalArgumentException("Aluno ou ID nao podem ser nulos para atualizacao.");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, aluno.getNome());
            if (aluno.getIdade() != null) {
                ps.setInt(2, aluno.getIdade());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, aluno.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro operacional ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deletarPorId(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro operacional ao deletar aluno: " + e.getMessage(), e);
        }
    }

    private Aluno mapearResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        
        // Tratamento correto de SQL NULL para wrapper Integer
        int idadePrimitiva = rs.getInt("idade");
        Integer idade = rs.wasNull() ? null : idadePrimitiva;

        return new Aluno(id, nome, idade);
    }
}
