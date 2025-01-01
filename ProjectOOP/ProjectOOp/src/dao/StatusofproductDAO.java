package dao;

import bean.Statusofproduct;
import dao.DBconnect;

import java.sql.*;
import java.util.ArrayList;

public class StatusofproductDAO extends DBconnect {

    // Lấy tất cả các bản ghi trong bảng Statusofproduct
    public ArrayList<Statusofproduct> getAllStatusofproduct() throws SQLException {
        connect();

        String sql = "SELECT * FROM Statusofproduct";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Statusofproduct> statusList = new ArrayList<>();
        while (rs.next()) {
            Statusofproduct status = new Statusofproduct();
            status.setOrderID(rs.getString("OrderID"));
            status.setCurrentWarehouseID(rs.getString("CurrentWarehouseID"));
            status.setOrderStatus(rs.getString("OrderStatus"));
            statusList.add(status);
        }
        return statusList;
    }

    // Thêm bản ghi vào bảng Statusofproduct
    public void addStatus(Statusofproduct status) throws SQLException {
        connect();

        String sql = "INSERT INTO Statusofproduct (OrderID, CurrentWarehouseID, OrderStatus) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, status.getOrderID());
        stmt.setString(2, status.getCurrentWarehouseID());
        stmt.setString(3, status.getOrderStatus());

        stmt.executeUpdate();
    }

    // Cập nhật trạng thái đơn hàng trong bảng Statusofproduct
    public void updateStatus(Statusofproduct status) throws SQLException {
        connect();

        String sql = "UPDATE Statusofproduct SET CurrentWarehouseID = ?, OrderStatus = ? WHERE OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, status.getCurrentWarehouseID());
        stmt.setString(2, status.getOrderStatus());
        stmt.setString(3, status.getOrderID());

        stmt.executeUpdate();
    }

    // Xóa bản ghi trong bảng Statusofproduct
    public void deleteStatus(String orderID) throws SQLException {
        connect();

        String sql = "DELETE FROM Statusofproduct WHERE OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderID);

        stmt.executeUpdate();
    }
}