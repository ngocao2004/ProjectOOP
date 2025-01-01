package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
    private final String url = "jdbc:mysql://localhost:3306/gvproject";
    private final String userName = "caongo2004";
    private final String password = "ngovancao2004";
    protected Connection connection;

    // Phương thức connect() cần public để có thể truy cập từ bên ngoài
    public void connect() {
        try {
            // Sử dụng driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.err.println("Kết nối thất bại: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver không được tìm thấy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getter để truy cập connection từ bên ngoài
    public Connection getConnection() {
        return connection;
    }
}