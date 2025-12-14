import java.sql.*;
import java.util.Scanner;

public class LibraryManagement {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("❌ Database connection failed. Exiting...");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n======= LIBRARY MANAGEMENT SYSTEM =======");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = safeIntInput(sc);

            switch (choice) {
                case 1 -> addBook(con, sc);
                case 2 -> viewBooks(con);
                case 3 -> searchBook(con, sc);
                case 4 -> deleteBook(con, sc);
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        try {
            con.close();
            sc.close();
        } catch (Exception ignored) {}
    }

    // SAFE INTEGER INPUT
    public static int safeIntInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    // ADD BOOK
    public static void addBook(Connection con, Scanner sc) {
        try {
            System.out.print("Book ID: ");
            int id = safeIntInput(sc);
            sc.nextLine();

            System.out.print("Title: ");
            String title = sc.nextLine();

            System.out.print("Author: ");
            String author = sc.nextLine();

            System.out.print("Quantity: ");
            int qty = safeIntInput(sc);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO books (book_id, title, author, quantity) VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setInt(4, qty);

            int row = ps.executeUpdate();
            System.out.println(row > 0 ? "✔ Book added successfully!" : "❌ Failed to add book.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Book ID already exists!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW BOOKS
    public static void viewBooks(Connection con) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books ORDER BY book_id");

            System.out.println("\nID | Title | Author | Qty");
            System.out.println("-------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No books found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // SEARCH BOOK
    public static void searchBook(Connection con, Scanner sc) {
        try {
            sc.nextLine();
            System.out.print("Enter title/author to search: ");
            String key = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? ORDER BY book_id"
            );

            ps.setString(1, "%" + key.toLowerCase() + "%");
            ps.setString(2, "%" + key.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();

            System.out.println("\nID | Title | Author | Qty");
            System.out.println("-------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No matching books found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DELETE BOOK
    public static void deleteBook(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Book ID to delete: ");
            int id = safeIntInput(sc);

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM books WHERE book_id = ?"
            );
            ps.setInt(1, id);

            int row = ps.executeUpdate();
            System.out.println(row > 0 ? "✔ Book deleted!" : "❌ Book ID not found.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
