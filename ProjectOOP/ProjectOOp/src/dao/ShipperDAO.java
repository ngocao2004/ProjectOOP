package dao;

import bean.Shipper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipperDAO extends DBconnect {

    // Lấy danh sách tất cả các shipper
    public ArrayList<Shipper> getAllShippers() throws SQLException {
        connect();

        String sql = "SELECT * FROM Shipper";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Shipper> shippers = new ArrayList<>();
        while (rs.next()) {
            Shipper shipper = new Shipper();
            shipper.setEmployeeID(rs.getString("EmployeeID"));
            shipper.setLastName(rs.getString("LastName"));
            shipper.setMiddleName(rs.getString("MiddleName"));
            shipper.setFirstName(rs.getString("FirstName"));
            shipper.setGender(rs.getString("Gender"));
            shipper.setBirthday(rs.getDate("Birthday"));
            shipper.setPhone(rs.getString("Phone"));
            shipper.setHomeTown(rs.getString("HomeTown"));
            shippers.add(shipper);
        }
        return shippers;
    }
    
    public List<Shipper> searchShippersByName(String firstName, String middleName, String lastName) throws SQLException {
        List<Shipper> result = new ArrayList<>();

        String sql = "SELECT * FROM shipper WHERE FirstName LIKE ? AND MiddleName LIKE ? AND LastName LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + firstName + "%");
            stmt.setString(2, "%" + middleName + "%");
            stmt.setString(3, "%" + lastName + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng Shipper từ dữ liệu trong bảng
                Shipper shipper = new Shipper(
                    rs.getString("EmployeeID"),
                    rs.getString("LastName"),
                    rs.getString("MiddleName"),
                    rs.getString("FirstName"),
                    rs.getString("Gender"),
                    rs.getDate("Birthday"),
                    rs.getString("Phone"),
                    rs.getString("HomeTown")
                );
                result.add(shipper);
            }
        }
        return result;
    }


    // Thêm shipper mới
    public void addShipper(Shipper shipper) throws SQLException {
        connect();

        String sql = "INSERT INTO Shipper (EmployeeID, LastName, MiddleName, FirstName, Gender, Birthday, Phone, HomeTown) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, shipper.getEmployeeID());
        stmt.setString(2, shipper.getLastName());
        stmt.setString(3, shipper.getMiddleName());
        stmt.setString(4, shipper.getFirstName());
        stmt.setString(5, shipper.getGender());
        stmt.setDate(6, new java.sql.Date(shipper.getBirthday().getTime()));
        stmt.setString(7, shipper.getPhone());
        stmt.setString(8, shipper.getHomeTown());

        stmt.executeUpdate();
    }

    // Cập nhật thông tin shipper
    public void updateShipper(Shipper shipper) throws SQLException {
        connect();

        String sql = "UPDATE Shipper SET LastName = ?, MiddleName = ?, FirstName = ?, Gender = ?, Birthday = ?, Phone = ?, HomeTown = ? " +
                "WHERE EmployeeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, shipper.getLastName());
        stmt.setString(2, shipper.getMiddleName());
        stmt.setString(3, shipper.getFirstName());
        stmt.setString(4, shipper.getGender());
        stmt.setDate(5, new java.sql.Date(shipper.getBirthday().getTime()));
        stmt.setString(6, shipper.getPhone());
        stmt.setString(7, shipper.getHomeTown());
        stmt.setString(8, shipper.getEmployeeID());

        stmt.executeUpdate();
    }

    // Xóa shipper
    public void deleteShipper(String employeeID) throws SQLException {
        connect();

        String sql = "DELETE FROM Shipper WHERE EmployeeID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, employeeID);

        stmt.executeUpdate();
    }
    
 // Đảm bảo kết nối với cơ sở dữ liệu
    private Connection ensureConnection() throws SQLException {
        // Đảm bảo rằng kết nối không bị null
        if (connection == null || connection.isClosed()) {
            connect();  // Gọi phương thức connect() trong DBconnect để thiết lập kết nối
        }
        return connection;  // Trả về kết nối hiện tại
    }
    
 // Lấy tất cả các employeeID từ bảng Shipper
    public ArrayList<String> getAllEmployeeIDs() {
        ArrayList<String> employeeIDs = new ArrayList<>();
        String sql = "SELECT EmployeeID FROM Shipper";  // Câu lệnh SQL để lấy tất cả EmployeeID từ bảng Shipper

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql);  // Đảm bảo có kết nối với cơ sở dữ liệu
             ResultSet rs = stmt.executeQuery()) {  // Thực thi câu lệnh SQL và lấy kết quả

            while (rs.next()) {
                employeeIDs.add(rs.getString("EmployeeID"));  // Thêm EmployeeID vào danh sách
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách EmployeeID: " + e.getMessage());
        }

        return employeeIDs;  // Trả về danh sách EmployeeID
    }



}