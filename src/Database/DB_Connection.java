package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB_Connection {

    private static final String url = "jdbc:mysql://localhost";
    private static final String databaseName = "HY360_EVOL";
    private static final int port = 3306;
    private static final String username = "root";
    private static final String password = "";

    public static Connection getInitialConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(url + ":" + port, username, password);
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(url + ":" + port + "/" + databaseName, username, password);
    }
}
