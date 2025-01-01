package dao;

import bean.OrderDetails;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAO extends DBconnect {
    private DBconnect dbConnect = new DBconnect();
    // Lấy danh sách chi tiết đơn hàng theo OrderID
    public ArrayList<OrderDetails> getOrderDetailsByOrderID(String orderID) {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        try {
            dbConnect.connect();
            Connection connection = dbConnect.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, orderID);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        OrderDetails details = new OrderDetails();
                        details.setItemID(rs.getString("ItemID"));
                        details.setOrderID(rs.getString("OrderID"));
                        details.setItemName(rs.getString("ItemName"));
                        details.setSurchargeID(rs.getString("SurchargeID"));
                        details.setWeight(rs.getDouble("Weight"));
                        details.setItemPrice(rs.getDouble("ItemPrice"));
                        orderDetailsList.add(details);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (dbConnect.getConnection() != null) {
                    dbConnect.getConnection().close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
        return orderDetailsList;
    }

    // Thêm OrderDetails vào cơ sở dữ liệu
    public void addOrderDetails(OrderDetails orderDetails) throws SQLException {
        connect();
        String sql = "INSERT INTO OrderDetails (ItemID, OrderID, ItemName, SurchargeID, Weight, itemprice) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderDetails.getItemID());
        stmt.setString(2, orderDetails.getOrderID());
        stmt.setString(3, orderDetails.getItemName());
        stmt.setString(4, orderDetails.getSurchargeID());
        stmt.setDouble(5, orderDetails.getWeight());
        stmt.setDouble(6, orderDetails.getItemPrice());
        stmt.executeUpdate();
    }

    // Lấy danh sách OrderDetails
    public ArrayList<OrderDetails> getListOrderDetails() throws SQLException {
        connect();
        String sql = "SELECT * FROM OrderDetails";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<OrderDetails> list = new ArrayList<>();
        while (rs.next()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setItemID(rs.getString("ItemID"));
            orderDetails.setOrderID(rs.getString("OrderID"));
            orderDetails.setItemName(rs.getString("ItemName"));
            orderDetails.setSurchargeID(rs.getString("SurchargeID"));
            orderDetails.setWeight(rs.getDouble("Weight"));
            orderDetails.setItemPrice(rs.getDouble("itemprice"));
            list.add(orderDetails);
        }
        return list;
    }

    // Cập nhật thông tin OrderDetails
    public void updateOrderDetails(OrderDetails orderDetails) throws SQLException {
        connect();
        String sql = "UPDATE OrderDetails SET ItemName = ?, SurchargeID = ?, Weight = ?, itemprice = ? WHERE ItemID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderDetails.getItemName());
        stmt.setString(2, orderDetails.getSurchargeID());
        stmt.setDouble(3, orderDetails.getWeight());
        stmt.setDouble(4, orderDetails.getItemPrice());
        stmt.setString(5, orderDetails.getItemID());
        stmt.executeUpdate();
    }
    // Xóa OrderDetails
    public void deleteOrderDetails(String itemID) throws SQLException {
        connect();
        String sql = "DELETE FROM OrderDetails WHERE ItemID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, itemID);
        stmt.executeUpdate();
    }
}