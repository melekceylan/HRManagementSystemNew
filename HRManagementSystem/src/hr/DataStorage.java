package hr;

import java.sql.*;
import java.util.HashMap;

public class DataStorage {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hr_system"; // Veritabanı URL'si
    private static final String USER = "root"; // MySQL kullanıcı adı
    private static final String PASS = "2323"; // MySQL şifresi

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void saveEmployee(Employee employee) {
        try (Connection conn = connect()) {
            String query = "INSERT INTO employees (id, firstName, lastName, department, phoneNumber, password, leaveDays) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setString(5, employee.getPhoneNumber());
            pstmt.setString(6, employee.getPassword());
            pstmt.setInt(7, employee.getLeaveDays());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Employee> loadEmployees() {
        HashMap<Integer, Employee> employees = new HashMap<>();
        try (Connection conn = connect()) {
            String query = "SELECT * FROM employees";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("department"),
                        rs.getString("phoneNumber"),
                        rs.getString("password")
                );
                employee.setLeaveDays(rs.getInt("leaveDays"));
                employees.put(employee.getId(), employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void updateLeaveDays(int employeeId, int leaveDays) {
        try (Connection conn = connect()) {
            String query = "UPDATE employees SET leaveDays = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, leaveDays);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        try (Connection conn = connect()) {
            String query = "DELETE FROM employees WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxEmployeeId() {
        int maxId = 0;
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS max_id FROM employees");
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

}


