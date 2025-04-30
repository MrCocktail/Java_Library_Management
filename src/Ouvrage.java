import java.sql.*;
import java.util.Scanner;
public class Ouvrage {
    private Connection con;
    Statement st;
    ResultSet rs;
    Scanner sc, sc1;
    String edition; 
    int publish_year;
    String request;
    String format = "| %-5s | %-25s | %-6s |\n";

    public Ouvrage() {
        // Initialize the connection here if needed
        try {
            con = Connexion.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initBiblio() {
        sc = new Scanner(System.in);
        try {
            con = Connexion.getConnection();
            Statement st = con.createStatement();

            // st.executeUpdate("DROP TABLE IF EXISTS Student");
            // st.executeUpdate("DROP TABLE IF EXISTS Book");
            // st.executeUpdate("DROP TABLE IF EXISTS Loan");

            // Create Etudiant Table
            st.executeUpdate("CREATE TABLE Student (" +
            "numeroEtudiant AUTOINCREMENT PRIMARY KEY, " +
            "prenom TEXT, " +
            "nom TEXT, " +
            "filiere TEXT, " +
            "sexe TEXT, " +
            "frais DOUBLE)"
            );

            // Create Ouvrage Table
            st.executeUpdate("CREATE TABLE Book (" +
            "numeroOuvrage AUTOINCREMENT PRIMARY KEY, " +
            "edition TEXT, " +
            "publish_year INTEGER)"
            );

            // Create Pret Table
            st.executeUpdate("CREATE TABLE Loan (" +
            "numeroPret AUTOINCREMENT PRIMARY KEY, " +
            "numeroEtudiant INTEGER, " +
            "numeroOuvrage INTEGER, " +
            "datePret DATE, " +
            "dateRemise DATE, " +
            "FOREIGN KEY (numeroEtudiant) REFERENCES Student(numeroEtudiant), " +
            "FOREIGN KEY (numeroOuvrage) REFERENCES Book(numeroOuvrage)" +
            ")");

            System.out.println("Tables created successfully");
            System.out.println("Press Enter to continue");
            sc.nextLine();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void printBooks() {
        sc = new Scanner(System.in);
        String request = "SELECT * FROM Book";
    
        System.out.println("\nListe des ouvrages :\n");
        System.out.printf(format, "Ref", "Edition", "Année");
        System.out.println("----------------------------------------------");
    
        try {
            st = con.createStatement();
            rs = st.executeQuery(request);
            while (rs.next()) {
                System.out.printf(format,
                    rs.getInt("numeroOuvrage"),
                    rs.getString("edition"),
                    rs.getInt("publish_year")
                );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        System.out.println("\nAppuyez sur Entrée pour continuer");
        sc.nextLine();
    }
    
    void addBook() {
        sc = new Scanner(System.in);
        sc1 = new Scanner(System.in);
        // con = Connexion.getConnection();
        System.out.println("Enter the book's edition: ");
        edition = sc1.nextLine();        
        System.out.println("Enter the book's publish year: ");
        publish_year = sc.nextInt();
        sc.nextLine(); 
        request = "INSERT INTO Book (edition, publish_year) VALUES ('" + edition + "', '" + publish_year + "')";
        try {
            st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Insertion successful");
            System.out.println("Press Enter to continue");
            sc.nextLine();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    String searchBook() {
        boolean found = false;
        sc = new Scanner(System.in);
        // con = Connexion.getConnection();
        System.out.println("Enter the book's edition: ");
        edition = sc.nextLine();
        // sc.nextLine();
        request = "SELECT * FROM Book WHERE edition = '" + edition + "'";
        System.out.println("\n L'ouvrage :\n");
        System.out.printf(format, "Ref", "Edition", "Année");
        System.out.println("----------------------------------------------");
        try {
            st = con.createStatement();
            rs = st.executeQuery(request);
            if (rs.next()) {
                found = true;
                System.out.printf(format,
                    rs.getInt("numeroOuvrage"),
                    rs.getString("edition"),
                    rs.getInt("publish_year")
                );
            } else {
                System.out.println("Book not found.");
            }
            rs.close();
            // con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
        if (found) return edition;
        else return null;
    }

    void updateBook() {
        String found;
        sc = new Scanner(System.in);
        sc1 = new Scanner(System.in);
        // con = Connexion.getConnection();
        found = searchBook();
        if (found == null) {
            System.out.println("Book not found.");
            System.out.println("Press Enter to continue");
            sc.nextLine();
            return;
        }
        System.out.println("Enter the new book's edition: ");
        edition = sc1.nextLine();
        System.out.println("Enter the new book's publish year: ");
        publish_year = sc.nextInt();
        sc.nextLine();
        request = "UPDATE Book SET edition = '" + edition + "', publish_year = '" + publish_year + "' WHERE edition = '" + found + "'";
        try {
            st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Update successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    void deleteBook() {
        String found;
        sc = new Scanner(System.in);
        // con = Connexion.getConnection();
        found = searchBook();
        if (found == null) {
            System.out.println("Book not found.");
            System.out.println("Press Enter to continue");
            sc.nextLine();
            return;
        }
        System.out.println("Are you sure you want to delete the book with edition " + found + "? (y/n)");
        String confirmation = sc.nextLine();
        // sc.nextLine();
        if (!confirmation.equals("y") && !confirmation.equals("Y")) {
            System.out.println("Deletion cancelled.");
            System.out.println("Press Enter to continue");
            sc.nextLine();
            return;
        }
        request = "DELETE FROM Book WHERE edition = '" + found + "'";
        try {
            st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Deletion successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    void menu() {
        // initBiblio();
        Library library = new Library();
        String choice;
        sc = new Scanner(System.in);
        do {
            library.clearConsole();
            System.out.println("Module Ouvrage");
            System.out.println("\n[1]. Print books\n[2]. Add book\n[3]. Search book\n[4]. Update book\n[5]. Delete book\n[0]. Retour au menu principal");
            System.out.println("\nEnter your choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    printBooks();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    searchBook();
                    break;
                case "4":
                    updateBook();
                    break;
                case "5":
                    deleteBook();
                    break;
                case "0":
                    System.out.println("Returning to the main menu.");
                    library.mainMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    sc.nextLine(); // Clear the input buffer();
                    break;
            }
        } while (!choice.equals("0"));
    }

    // public static void main(String[] args) {
    //     try {
    //         con = Connexion.getConnection(); // Initialize the connection here
    //         Library library = new Library();
    //         library.mainMenu();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (con != null && !con.isClosed()) {
    //                 con.close(); // Close the connection when done
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
    // // Close the connection in the main method to ensure it's closed when the program ends
}

