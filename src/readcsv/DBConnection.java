package readcsv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Crea la Conexion con la Base de Datos MYSQL
 */
public class DBConnection {
    
    public static final String CONNECTION_STR= "jdbc:mysql://localhost:3306/test";
    public static final String USER = "root";
    public static final String PASS = "adminadmin";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASS);
        return connection;
    }

    public static void main(String[] args) {
        try {
            getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
