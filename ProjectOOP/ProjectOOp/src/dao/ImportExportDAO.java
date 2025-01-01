package dao;

import bean.ImportExport;

import java.sql.*;
import java.util.ArrayList;

public class ImportExportDAO extends DBconnect {

    // Lấy danh sách tất cả các bản ghi nhập/xuất kho
    public ArrayList<ImportExport> getAllImportExports() throws SQLException {
        connect();

        String sql = "SELECT * FROM ImportExport";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<ImportExport> importExports = new ArrayList<>();
        while (rs.next()) {
            ImportExport importExport = new ImportExport();
            importExport.setOrderID(rs.getString("OrderID"));
            importExport.setWarehouseID(rs.getString("WarehouseID"));
            importExport.setInboundDate(rs.getDate("InboundDate"));
            importExport.setOutboundDate(rs.getDate("OutboundDate"));
            importExports.add(importExport);
        }
        return importExports;
    }

    // Thêm bản ghi nhập kho mới
    public void addImport(ImportExport importExport) throws SQLException {
        connect();

        String sql = "INSERT INTO ImportExport (OrderID, WarehouseID, InboundDate, OutboundDate) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        // Kiểm tra nếu OutboundDate là null, nếu null thì không set giá trị
        stmt.setString(1, importExport.getOrderID());
        stmt.setString(2, importExport.getWarehouseID());
        stmt.setDate(3, new java.sql.Date(importExport.getInboundDate().getTime()));

        if (importExport.getOutboundDate() != null) {
            stmt.setDate(4, new java.sql.Date(importExport.getOutboundDate().getTime()));
        } else {
            stmt.setNull(4, Types.DATE);  // Nếu outboundDate là null, thì set giá trị là NULL
        }

        stmt.executeUpdate();
    }
    
    DBconnect d = new DBconnect();
    
    
    
    public boolean recordExists(String orderID, String warehouseID) {
    	if (connection == null) {
            connect(); // Đảm bảo gọi connect() nếu connection chưa được khởi tạo
        }

        String query = "SELECT COUNT(*) FROM importexport WHERE orderID = ? AND warehouseID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderID);
            statement.setString(2, warehouseID);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
 // Thêm bản ghi nhập kho
    public boolean addImportExport(String orderID, String warehouseID, Date inboundDate) {
        String query = "INSERT INTO importexport (orderID, warehouseID, inboundDate) VALUES (?, ?, ?)";
        try (Connection connection = d.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderID);
            stmt.setString(2, warehouseID);
            stmt.setDate(3, inboundDate);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được chèn vào
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu không thể thêm bản ghi
    }


    // Cập nhật thông tin xuất kho
    public void updateExport(ImportExport importExport) throws SQLException {
        connect();

        String sql = "UPDATE ImportExport SET OutboundDate = ? WHERE OrderID = ? AND WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(importExport.getOutboundDate().getTime()));
        stmt.setString(2, importExport.getOrderID());
        stmt.setString(3, importExport.getWarehouseID());

        stmt.executeUpdate();
    }

    // Xóa bản ghi nhập xuất kho
    public void deleteImportExport(String orderID, String warehouseID) throws SQLException {
        connect();

        String sql = "DELETE FROM ImportExport WHERE OrderID = ? AND WarehouseID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderID);
        stmt.setString(2, warehouseID);

        stmt.executeUpdate();
    }
}