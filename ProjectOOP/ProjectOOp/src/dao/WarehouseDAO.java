package dao;

import bean.Warehouse;
import dao.DBconnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;

public class WarehouseDAO extends DBconnect {
    // Lấy danh sách tất cả các kho
	public ArrayList<Warehouse> getAllWarehouses() throws SQLException {
	    // Kết nối cơ sở dữ liệu
	    connect();  

	    String sql = "SELECT WarehouseID, WareName, City, District, Ward, Address FROM Warehouse";
	    Statement stmt = null;
	    ResultSet rs = null;
	    ArrayList<Warehouse> warehouses = new ArrayList<>();

	    try {
	        // Tạo câu lệnh truy vấn
	        stmt = connection.createStatement();
	        
	        // Thực hiện truy vấn
	        rs = stmt.executeQuery(sql);
	        
	        // Duyệt qua các kết quả trả về
	        while (rs.next()) {
	            Warehouse warehouse = new Warehouse();
	            warehouse.setWarehouseID(rs.getString("WarehouseID"));
	            warehouse.setWareName(rs.getString("WareName"));
	            warehouse.setCity(rs.getString("City"));
	            warehouse.setDistrict(rs.getString("District"));
	            warehouse.setWard(rs.getString("Ward"));
	            warehouse.setAddress(rs.getString("Address"));
	            warehouses.add(warehouse);
	        }
	    } catch (SQLException e) {
	        // Xử lý lỗi nếu có
	        System.out.println("Error executing query: " + e.getMessage());
	        throw e;  // Ném lại ngoại lệ để người gọi có thể xử lý
	    } finally {
	        // Đảm bảo rằng kết nối, Statement và ResultSet được đóng
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                System.out.println("Error closing ResultSet: " + e.getMessage());
	            }
	        }
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                System.out.println("Error closing Statement: " + e.getMessage());
	            }
	        }
	    }

	    return warehouses;  // Trả về danh sách kho
	}

	
	
    
    public boolean isWarehouseIDExists(String warehouseID) throws SQLException {
        String query = "SELECT COUNT(*) FROM warehouse WHERE warehouseID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, warehouseID);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }

    
    public void insertWarehouse(Warehouse warehouse) throws SQLException {
    	connect();
        String sql = "INSERT INTO warehouse (WarehouseID, WareName, City, District, Ward, Address) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, warehouse.getWarehouseID());
            ps.setString(2, warehouse.getWareName());
            ps.setString(3, warehouse.getCity());
            ps.setString(4, warehouse.getDistrict());
            ps.setString(5, warehouse.getWard());
            ps.setString(6, warehouse.getAddress());
            ps.executeUpdate();  // Thực hiện câu lệnh chèn
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting new warehouse");
        }
    }


    // Cập nhật thông tin Warehouse
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
        connect();
        String sql = "UPDATE Warehouse SET WareName = ?, City = ?, District = ?, Ward = ?, Address = ? WHERE WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, warehouse.getWareName());
        stmt.setString(2, warehouse.getCity());
        stmt.setString(3, warehouse.getDistrict());
        stmt.setString(4, warehouse.getWard());
        stmt.setString(5, warehouse.getAddress());
        stmt.setString(6, warehouse.getWarehouseID());

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Warehouse updated successfully.");
        }
    }

    // Lấy thông tin Warehouse theo WarehouseID
    public Warehouse getWarehouseByID(String warehouseID) throws SQLException {
        connect();
        String sql = "SELECT * FROM Warehouse WHERE WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, warehouseID);
        ResultSet rs = stmt.executeQuery();

        Warehouse warehouse = null;
        if (rs.next()) {
            warehouse = new Warehouse();
            warehouse.setWarehouseID(rs.getString("WarehouseID"));
            warehouse.setWareName(rs.getString("WareName"));
            warehouse.setCity(rs.getString("City"));
            warehouse.setDistrict(rs.getString("District"));
            warehouse.setWard(rs.getString("Ward"));
            warehouse.setAddress(rs.getString("Address"));
        }

        return warehouse;
    }
    
    private Connection ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect(); // Kết nối lại nếu kết nối hiện tại không hợp lệ
            connection = getConnection();
        }
        return connection;
    }
    
 // Trong lớp WarehouseDAO
    public ArrayList<String> getAllWarehouseIDs() {
        ArrayList<String> warehouseIDs = new ArrayList<>();
        String sql = "SELECT WarehouseID FROM Warehouse"; // Truy vấn SQL để lấy WarehouseID

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                warehouseIDs.add(rs.getString("WarehouseID")); // Thêm WarehouseID vào danh sách
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách WarehouseID: " + e.getMessage());
        }

        return warehouseIDs; // Trả về danh sách WarehouseID
    }

    public boolean deleteWarehouse(String warehouseID) throws SQLException {
        connect();  // Kết nối cơ sở dữ liệu

        String sql = "DELETE FROM Warehouse WHERE WarehouseID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, warehouseID); // Thiết lập giá trị cho tham số ID kho

            int rowsAffected = stmt.executeUpdate(); // Thực thi câu lệnh SQL
            return rowsAffected > 0; // Trả về true nếu có ít nhất một bản ghi bị xóa
        }
    }
    
 // Phương thức tìm kiếm kho hàng theo mã kho (WarehouseID) hoặc tên kho (WareName)
    public ArrayList<Warehouse> searchWarehouses(String searchTerm) throws SQLException {
        ArrayList<Warehouse> result = new ArrayList<>();
        String query = "SELECT * FROM warehouse WHERE WarehouseID LIKE ? OR WareName LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thực hiện tìm kiếm theo WarehouseID hoặc WareName
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String warehouseID = rs.getString("WarehouseID");
                String wareName = rs.getString("WareName");
                String city = rs.getString("City");
                String district = rs.getString("District");
                String ward = rs.getString("Ward");
                String address = rs.getString("Address");
                result.add(new Warehouse(warehouseID, wareName, city, district, ward, address));
            }
        }
        return result;
    }

    // Lấy thông tin WarehouseLog
    public void getWarehouseLogs() throws SQLException {
        connect();
        String sql = "SELECT * FROM WarehouseLog";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        if (!rs.isBeforeFirst()) {
            System.out.println("Không có dữ liệu nhật ký thay đổi kho.");
            return;
        }

        while (rs.next()) {
            System.out.println("LogID: " + rs.getInt("LogID") +
                    ", WarehouseID: " + rs.getString("WarehouseID") +
                    ", OldWareName: " + rs.getString("OldWareName") +
                    ", NewWareName: " + rs.getString("NewWareName") +
                    ", ChangeTime: " + rs.getTimestamp("ChangeTime"));
        }
    }
    
    public ArrayList<Warehouse> searchWarehousesByID(String searchID) throws SQLException {
        String sql = "SELECT * FROM Warehouse WHERE WarehouseID LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchID + "%");
            ResultSet rs = stmt.executeQuery();
            ArrayList<Warehouse> warehouses = new ArrayList<>();
            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseID(rs.getString("WarehouseID"));
                warehouse.setWareName(rs.getString("WareName"));
                warehouse.setCity(rs.getString("City"));
                warehouse.setDistrict(rs.getString("District"));
                warehouse.setWard(rs.getString("Ward"));
                warehouse.setAddress(rs.getString("Address"));
                warehouses.add(warehouse);
            }
            return warehouses;
        }
    }
}
    
    


