import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Pret {
    private int numeroPret;
    private int numeroEtudiant;
    private int numeroOuvrage;
    private LocalDate datePret;
    private LocalDate dateRemise;
    private Connection con;
    Scanner sc, sc1;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Pret() {
        try {
            con = Connexion.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printPret() {
        sc = new Scanner(System.in);
        System.out.println("\nListe des prêts :\n");
    
        String format = "| %-5s | %-10s | %-10s | %-12s | %-12s |\n";
        System.out.printf(format, "ID", "Etudiant", "Ouvrage", "Date prêt", "Date remise");
        System.out.println("---------------------------------------------------------------");
    
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Loan");
    
            while (rs.next()) {
                System.out.printf(format,
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        System.out.println("\nAppuyez sur Entrée pour continuer");
        sc.nextLine();
    }
    
    public void addPret() {
        sc = new Scanner(System.in);
        System.out.println("Enter the student number: ");
        numeroEtudiant = sc.nextInt();
        System.out.println("Enter the book number: ");
        numeroOuvrage = sc.nextInt();
        // System.out.println("Enter the loan date (YYYY-MM-DD): ");
        datePret = LocalDate.now(); // Use current date as loan date
        // datePret = sc.next(); // Uncomment this line if you want to input the loan
        // date manually
        System.out.println("Entrez le nombre de jours avant la date de retour: ");
        dateRemise = LocalDate.now().plusDays(sc.nextInt());
        // String formattedDatePret = datePret.format(formatter);
        // String formattedDateRemise = dateRemise.format(formatter);
        String request = "INSERT INTO Loan (numeroEtudiant, numeroOuvrage, datePret, dateRemise) VALUES ("
                + numeroEtudiant + ", " + numeroOuvrage + ", #" + Date.valueOf(datePret) + "#, #" + Date.valueOf(dateRemise)
                + "#)";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Loan added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    public void deletePret() {
        sc = new Scanner(System.in);
        System.out.println("Enter the loan number to delete: ");
        numeroPret = sc.nextInt();
        String request = "DELETE FROM Loan WHERE numeroPret = " + numeroPret;
        try {
            Statement st = con.createStatement();
            st.executeUpdate(request);
            System.out.println("Loan deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    int searchPret() {
        boolean found = false;
        sc = new Scanner(System.in);
        System.out.println("Enter the loan number: ");
        numeroPret = sc.nextInt();
        String request = "SELECT * FROM Loan WHERE numeroPret = " + numeroPret;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(request);
            if (rs.next()) {
                found = true;
                System.out.println(rs.getInt(1) + " - " + rs.getInt(2) + " - " + rs.getInt(3) + " - " + rs.getString(4)
                        + " - " + rs.getString(5));
            } else {
                System.out.println("Loan not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
        if (found)
            return numeroPret;
        else
            return -1;
    }

    public void updatePret() {
        sc = new Scanner(System.in);
        System.out.println("Enter the loan number to update: ");
        numeroPret = sc.nextInt();
        String request = "SELECT * FROM Loan WHERE numeroPret = " + numeroPret;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(request);
            if (rs.next()) {
                System.out.println("Enter the new student number: ");
                numeroEtudiant = sc.nextInt();
                System.out.println("Enter the new book number: ");
                numeroOuvrage = sc.nextInt();
                // System.out.println("Enter the new loan date (YYYY-MM-DD): ");
                datePret = LocalDate.now(); // Use current date as loan date
                System.out.println("Entrez le nouveau nombre de jours avant la date de retour: ");
                dateRemise = LocalDate.now().plusDays(sc.nextInt()); // Use current date plus days as return date
                request = "UPDATE Loan SET numeroEtudiant = ?, numeroOuvrage = ?, datePret = ?, dateRemise = ? WHERE numeroPret = ?";
                PreparedStatement ps = con.prepareStatement(request);
                ps.setInt(1, numeroEtudiant);
                ps.setInt(2, numeroOuvrage);
                ps.setDate(3, Date.valueOf(datePret));
                ps.setDate(4, Date.valueOf(dateRemise));
                ps.setInt(5, numeroPret);
                ps.executeUpdate();
                // st.executeUpdate(request);
                System.out.println("Loan updated successfully");
            } else {
                System.out.println("Loan not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Press Enter to continue");
        sc.nextLine();
    }

    void getAll() {
        try {ResultSet rs = con.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
        while (rs.next()) {
            System.out.println("TABLE: " + rs.getString("TABLE_NAME"));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void menu() {
        Library library = new Library();
        String choice;
        sc = new Scanner(System.in);
        do {
            // library.clearConsole();
            System.out.println("Module Pret");
            System.out.println(
                    "\n[1]. Add Loan\n[2]. Delete Loan\n[3]. Update Loan\n[4]. Search Loan\n[5]. List Loans\n[6]. Retour au menu principal\n");
            System.out.println("Enter your choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    addPret();
                    // getAll();
                    break;
                case "2":
                    deletePret();
                    break;
                case "3":
                    updatePret();
                    break;
                case "4":
                    searchPret();
                    break;
                case "5":
                    printPret();
                    break;
                case "6":
                    System.out.println("Retour au menu principal.");
                    library.mainMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    sc.nextLine(); // Clear the input buffer
                    break;
            }
        } while (!choice.equals("0"));
    }

}
