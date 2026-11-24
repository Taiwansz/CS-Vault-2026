import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; //para ver o que tem dentro do banco


public class VerDados {
    public static void main(String[] args) {
        //a url de conexao do bd com o back
        String url = "jdbc:mysql://localhost:3306/aula_java";
        String user = "root";
        String password = "root753";

        String sql = "select * from alunos";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery()){

                System.out.println("Dados da tabela: ");
                while(rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    System.out.println(id + " " + nome + " "+ idade);
                }

        } catch (SQLException e){
                System.out.println("ERRO: "+e.getMessage());
        }
    }
}
