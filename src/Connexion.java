import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Connexion {
    static Connection con;
    static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    static String url = "jdbc:ucanaccess://src//Biblio.mdb";

    static {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
            System.out.println("Connection established successfully."); 
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return con;
    }

    public static void closeConnection() {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
