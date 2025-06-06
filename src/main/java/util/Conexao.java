package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe para pegar a conexão com o banco de dados MySQL
public class Conexao {

    // Dados para conexão (ajuste se mudar o banco, usuário ou senha)
    private static final String URL = "jdbc:mysql://localhost:3306/estoque";
    private static final String USER = "root";
    private static final String PASSWORD = "32327229@Edu";

    // Método para conseguir a conexão
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}