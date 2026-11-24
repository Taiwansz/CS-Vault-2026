import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO {
    private Connection conn;

    public AtletaDAO(Connection conn) {
        this.conn = conn;
    }

    // MISSÃO 3 — Mesa de Contratações (Create)
    // 1) Preencha os 5 parâmetros no PreparedStatement (nome, posicao, idade, salario, status)
    // 2) Execute o comando com pstmt.executeUpdate()
    public void cadastrarAtleta(Atleta atleta) throws SQLException {
        String sql = "INSERT INTO atletas (nome, posicao, idade, salario, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, atleta.getNome());
            pstmt.setString(2, atleta.getPosicao());
            pstmt.setInt(3, atleta.getIdade());
            pstmt.setDouble(4, atleta.getSalario());
            pstmt.setString(5, atleta.getStatus());
            pstmt.executeUpdate();
        }
    }

    // MISSÃO 5 — Prancheta do Elenco (Java Read)
    // 1) No laço while (rs.next()), instancie Atleta a = new Atleta(...)
    // 2) Adicione na lista: elenco.add(a)
    // 3) Retorne elenco
    public List<Atleta> listarElenco() throws SQLException {
        List<Atleta> elenco = new ArrayList<>();
        String sql = "SELECT * FROM atletas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Atleta a = new Atleta(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("posicao"),
                        rs.getInt("idade"),
                        rs.getDouble("salario"),
                        rs.getString("status")
                );
                elenco.add(a);
            }
        }
        return elenco;
    }

    // MISSÃO 6 — Renovação Salarial (Update)
    // 1) Defina o parâmetro 1: novoSalario (double)
    // 2) Defina o parâmetro 2: novoStatus (String)
    // 3) Defina o parâmetro 3: id (int)
    // 4) Execute com pstmt.executeUpdate()
    public void atualizarSalarioEStatus(int id, double novoSalario, String novoStatus) throws SQLException {
        String sql = "UPDATE atletas SET salario = ?, status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, novoSalario);
            pstmt.setString(2, novoStatus);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    // MISSÃO 7 — Rescisão & Compliance (Delete)
    // 1) Preencha o parâmetro 1 com o id: pstmt.setInt(1, id)
    // 2) Guarde o retorno de pstmt.executeUpdate() na variável int linhas
    // 3) Retorne linhas > 0
    public boolean dispensarAtleta(int id) throws SQLException {
        String sql = "DELETE FROM atletas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int linhas = pstmt.executeUpdate();
            return linhas > 0;
        }
    }
}
