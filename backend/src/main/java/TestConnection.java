
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/padel_reservation?createDatabaseIfNotExist=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
        String user = "root";
        String password = "Root";

        System.out.println("Attempting connection to: " + url);
        System.out.println("User: " + user);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ CONNECTION SUCCESSFUL!");
        } catch (SQLException e) {
            System.out.println("❌ CONNECTION FAILED");
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
