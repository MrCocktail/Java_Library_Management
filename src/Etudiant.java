import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Etudiant {
    private static Connection con;
    Statement st;
    ResultSet rs;
    Scanner sc, sc1;
    String request;
    private int numeroEtudiant;
    private String prenom;
    private String nom;
    private String filiere;
    private String sexe;
    private double frais;

    public Etudiant() {
        // Initialize the connection here if needed
        try {
            con = Connexion.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void printEtudiants() {
        sc = new Scanner(System.in);
        System.out.println("\nListe des étudiants :\n");
    
        String format = "| %-4s | %-10s | %-10s | %-10s | %-5s | %-7s |\n";
        System.out.printf(format, "ID", "Prénom", "Nom", "Filière", "Sexe", "Frais");
        System.out.println("-----------------------------------------------------------------");
    
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Student");
    
            while (rs.next()) {
                int id = rs.getInt("numeroEtudiant");
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String filiere = rs.getString("filiere");
                String sexe = rs.getString("sexe");
                double frais = rs.getDouble("frais");
    
                System.out.printf(format, id, prenom, nom, filiere, sexe, frais);
            }
    
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des étudiants :");
            e.printStackTrace();
        }
    
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        sc.nextLine();
    }
    
    void addEtudiant() {
        sc = new Scanner(System.in);
        System.out.println("Enter the first name of the student: ");
        prenom = sc.nextLine();
        System.out.println("Enter the last name of the student: ");
        nom = sc.nextLine();
        System.out.println("Enter the field of study: ");
        filiere = sc.nextLine();
        System.out.println("Enter the gender: ");
        sexe = sc.nextLine();
        System.out.println("Enter the tuition fees: ");
        frais = sc.nextDouble();
        request = "INSERT INTO Student (prenom, nom, filiere, sexe, frais) VALUES ('" + prenom + "', '" + nom + "', '" + filiere + "', '" + sexe + "', " + frais + ")";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Student added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    void deleteEtudiant() {
        sc = new Scanner(System.in);
        // System.out.println("Enter the student number to delete: ");
        numeroEtudiant = searchEtudiant();
        if (numeroEtudiant == -1) {
            return;
        }
        System.out.println("Are you sure you want to delete the student with code " + numeroEtudiant + "? (yes/no)");
        String confirmation = sc.nextLine();
        if (!confirmation.equals("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }
        request = "DELETE FROM Student WHERE numeroEtudiant = " + numeroEtudiant;
        try {
            Statement st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Student deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }
    int searchEtudiant() {
        boolean found = false;
        sc = new Scanner(System.in);
        System.out.println("Enter the student's code: ");
        numeroEtudiant = sc.nextInt();
        request = "SELECT * FROM Student WHERE numeroEtudiant = '" + numeroEtudiant + "'";
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(request);
            if (rs.next()) {
                found = true;
                System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4) + " - " + rs.getString(5) + " - " + rs.getDouble(6));
            } else {
                System.out.println("Student not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
        if (found) return numeroEtudiant;
        else return -1;
    }

    void updateEtudiant() {
        int found;
        sc = new Scanner(System.in);
        sc1 = new Scanner(System.in);
        found = searchEtudiant();
        if (found == -1) {
            return;
        }
        System.out.println("found: "+ found);
        System.out.println("Enter the new first name: ");
        prenom = sc.nextLine();
        System.out.println("Enter the new last name of the student: ");
        nom = sc.nextLine();
        System.out.println("Enter the new field of study: ");
        filiere = sc.nextLine();
        System.out.println("Enter the new gender: ");
        sexe = sc.nextLine();
        System.out.println("Enter the new tuition fees: ");
        frais = sc.nextDouble();
        request = "UPDATE Student SET prenom = '"+ prenom +"', nom = '"+ nom +"', filiere = '"+ filiere +"', sexe = '"+ sexe +"', frais = '"+ frais +"' WHERE numeroEtudiant = '"+ found +"'";
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

    void menu() {
        Library library = new Library();
        String choice;
        sc = new Scanner(System.in);
        // System.out.print("\033c"); // Clear the console
        do {
            library.clearConsole();
            System.out.println("Module Etudiant");
            System.out.println("\n[1]. Print etudiants\n[2]. Add etudiant\n[3]. Search etudiant\n[4]. Update etudiant\n[5]. Delete etudiant\n[0]. Retour au menu principal");
            System.out.println("\nEnter your choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    printEtudiants();
                    break;
                case "2":
                    addEtudiant();
                    break;
                case "3":
                    searchEtudiant();
                    break;
                case "4":
                    updateEtudiant();
                    break;
                case "5":
                    deleteEtudiant();
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
    //         con = Connexion.getConnection();
    //         Etudiant etudiant = new Etudiant();
    //         etudiant.printEtudiants();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (con != null) {
    //                 con.close();
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // } 

}
