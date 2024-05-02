import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseTracker {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = "Your_Password";

    public static void main(String[] args) {
        // Add a new expense
        addExpense(Date.valueOf("2024-05-01"), "Mechanic", 500000, "Cars");

        // View all expenses
        viewExpenses();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private static void addExpense(Date date, String description, double amount, String category) {
        String sql = "INSERT INTO expenses (date, description, amount, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            stmt.setString(2, description);
            stmt.setDouble(3, amount);
            stmt.setString(4, category);
            stmt.executeUpdate();
            System.out.println("Expense added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewExpenses() {
        String sql = "SELECT * FROM expenses";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("------- Expense List -------");
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");

                System.out.println("ID: " + id + ", Date: " + date + ", Description: " + description + ", Amount: $" + amount + ", Category: " + category);
            }
            System.out.println("----------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
