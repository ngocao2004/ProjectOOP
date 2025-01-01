package bo;

import dao.WarehouseDAO;
import bean.Warehouse;
import dao.DBconnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class WarehouseBO {
    private WarehouseDAO warehouseDAO = new WarehouseDAO();
    

    public ArrayList<Warehouse> getAllWarehouses() throws SQLException {
        return warehouseDAO.getAllWarehouses(); // Ném SQLException để phương thức gọi xử lý
    }
    // Cập nhật thông tin Warehouse
    public void updateWarehouse(Warehouse warehouse) {
        try {
            warehouseDAO.updateWarehouse(warehouse);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin Warehouse theo WarehouseID
    public Warehouse getWarehouseByID(String warehouseID) {
        try {
            return warehouseDAO.getWarehouseByID(warehouseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Warehouse> searchWarehouses(String searchTerm) {
        try {
            return warehouseDAO.searchWarehouses(searchTerm); // Tìm kiếm trong cơ sở dữ liệu với searchTerm
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        // Thực hiện kiểm tra nếu các thông tin kho hợp lệ (ví dụ: kiểm tra trống)
        if (warehouse.getWarehouseID().isEmpty() || warehouse.getWareName().isEmpty()) {
            throw new SQLException("ID kho và tên kho không được để trống.");
        }

        // Gọi phương thức từ WarehouseDAO để chèn kho vào cơ sở dữ liệu
        warehouseDAO.insertWarehouse(warehouse);
    }
    
    public boolean deleteWarehouse(String warehouseID) throws SQLException {
        // Gọi phương thức xóa kho trong WarehouseDAO và trả về kết quả
        return warehouseDAO.deleteWarehouse(warehouseID);
    }
    private Connection connection;
    
    public ArrayList<Warehouse> searchWarehousesByID(String searchID) {
        try {
            return warehouseDAO.searchWarehousesByID(searchID); // Gọi phương thức từ DAO
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Phương thức tìm kiếm kho theo tên
    public ArrayList<Warehouse> searchWarehousesByName(String searchName) throws SQLException {
        String sql = "SELECT * FROM Warehouse WHERE WareName LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchName + "%");  // Sử dụng LIKE để tìm kiếm kho có tên chứa chuỗi searchName
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
    
 // Trong lớp WarehouseBO
    public ArrayList<String> getAllWarehouseIDs() {
        WarehouseDAO warehouseDAO = new WarehouseDAO(); // Tạo đối tượng DAO
        return warehouseDAO.getAllWarehouseIDs(); // Gọi hàm từ lớp DAO
    }

    
   

    // Kiểm tra nhật ký thay đổi kho
    public void checkWarehouseLogs() throws SQLException {
        warehouseDAO.getWarehouseLogs();  // Gọi phương thức trong WarehouseDAO và ném ngoại lệ nếu có lỗi
    }
}