package NFLDataApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class to connect to database
public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost/NFLData?";
    private static final String USER = "root";
    private static final String PASSWORD = "pass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
