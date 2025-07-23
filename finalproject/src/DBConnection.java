import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/taskmanagerdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // set your DB username
    private static final String PASSWORD = "Aa112233456!"; // set your DB password

    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
