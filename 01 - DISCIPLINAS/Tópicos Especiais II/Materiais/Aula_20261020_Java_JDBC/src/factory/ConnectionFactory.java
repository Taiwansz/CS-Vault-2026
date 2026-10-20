package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory centralizada para criacao e provisionamento de conexoes JDBC.
 * Isola credenciais e strings de conexao da camada de negocio.
 */
public final class ConnectionFactory {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "aula_java";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "root753";

    private static final String URL = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8",
            DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DATABASE
    );

    private ConnectionFactory() {
        // Construtor privado para impedir instanciacao
    }

    /**
     * Abre e retorna uma conexao ativa com o banco MySQL.
     *
     * @return Conexao JDBC ativa
     * @throws SQLException caso ocorra falha de autenticacao ou rede
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }
}
