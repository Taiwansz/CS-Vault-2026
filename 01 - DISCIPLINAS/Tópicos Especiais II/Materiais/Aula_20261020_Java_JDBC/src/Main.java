import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args){
        //a url de conexao do bd com o back
        String url = "jdbc:mysql://localhost:3306/aula_java";
        String user = "root";
        String password = "root753";

        String sql = "insert into alunos(nome, idade) values(?,?)";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = conn.prepareStatement(sql)) {

            //vamos informar os dados que vão para o banco de dados
            psmt.setString(1,"Matheus");
            psmt.setInt(2,21);

            // manda o codigo

            int linhasAfetadas = psmt.executeUpdate();
            System.out.println("Cadastrado");

        }catch (SQLException e){
            System.out.println("Erro ao cadastrar aluno" + e.getMessage() );
        }
    }
}