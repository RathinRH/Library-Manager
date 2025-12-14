import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
    private static final String USER = "LuckyBoy";
    private static final String PASSWORD = "123rathin";

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("DB Connection failed: " + e.getMessage());
            return null;
        }
    }
}
