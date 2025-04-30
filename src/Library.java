import java.sql.*;
import java.util.Scanner;
public class Library {
    private static Connection con;
    Statement st;
    ResultSet rs;
    Scanner sc, sc1;
    String edition; 
    int publish_year;
    String request;

    public void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    

    void mainMenu() {
        Ouvrage ouvrage = new Ouvrage();
        Etudiant etudiant = new Etudiant();
        Pret pret = new Pret();
        String choice;
        sc = new Scanner(System.in);
        // System.out.print("\033c"); // Clear the console
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        clearConsole();
        do {
            // System.getProperty("clear");
            System.out.println("Welcome to the Library Management System\n");
            System.out.println("Main Menu");
            System.out.println("\n[1]. Module Etudiant\n[2]. Module Ouvrage\n[3]. Module Pret\n[4]. Exit");
            System.out.println("\nEnter your choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    etudiant.menu();
                    break;
                case "2":
                    ouvrage.menu();
                    break;
                case "3":
                    pret.menu();
                    break;
                case "4":
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    sc.nextLine(); // Clear the input buffer();
                    break;
            }
        } while (!choice.equals("0"));
    }

    public static void main(String[] args) {
        try {
            con = Connexion.getConnection(); // Initialize the connection here
            Library library = new Library();
            library.mainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close(); // Close the connection when done
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Close the connection in the main method to ensure it's closed when the program ends
}

