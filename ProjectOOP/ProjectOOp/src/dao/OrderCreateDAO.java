package dao;

import bean.OrderCreate;

import java.sql.*;
import java.util.ArrayList;

public class OrderCreateDAO extends DBconnect {

    // Lấy danh sách tất cả đơn hàng
    public ArrayList<OrderCreate> getAllOrders() throws SQLException {
        connect();
        String sql = "SELECT * FROM OrderCreate";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<OrderCreate> orders = new ArrayList<>();
        while (rs.next()) {
            OrderCreate order = new OrderCreate();
            order.setOrderID(rs.getString("OrderID"));
            order.setGiverID(rs.getString("GiverID"));
            order.setReceiverID(rs.getString("ReciverID"));
            order.setOrderDate(rs.getDate("OrderDate"));
            orders.add(order);
        }
        return orders;
    }

    // Thêm đơn hàng mới
    public void addOrder(OrderCreate order) throws SQLException {
        connect();
        String sql = "INSERT INTO OrderCreate (OrderID, GiverID, ReciverID, OrderDate) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, order.getOrderID());
        stmt.setString(2, order.getGiverID());
        stmt.setString(3, order.getReceiverID());
        stmt.setDate(4, new java.sql.Date(order.getOrderDate().getTime()));
    //xu ly phan trigger before_insert_OrderCreate
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                throw new SQLException("Error: " + e.getMessage());  // Đưa thông báo lỗi về người dùng
            } else {
                throw e;  // Đẩy các lỗi khác lên phía trên
            }
        }
    }

    // Cập nhật thông tin đơn hàng
    public void updateOrder(OrderCreate order) throws SQLException {
        connect();
        String sql = "UPDATE OrderCreate SET GiverID = ?, ReciverID = ?, OrderDate = ? WHERE OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, order.getGiverID());
        stmt.setString(2, order.getReceiverID());
        stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
        stmt.setString(4, order.getOrderID());
        //xu ly phan trigger before_update_OrderCreate
        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                throw new SQLException("Error: " + e.getMessage());  // Đưa thông báo lỗi về người dùng
            } else {
                throw e;  // Đẩy các lỗi khác lên phía trên
            }
        }
    }

    // Xóa đơn hàng
    public void deleteOrder(String orderID) throws SQLException {
        connect();
        String sql = "DELETE FROM OrderCreate WHERE OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderID);

        stmt.executeUpdate();
    }
    private DBconnect dbConnect = new DBconnect();
        // Lấy danh sách các đơn hàng theo UserID
        public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
            ArrayList<OrderCreate> orders = new ArrayList<>();
            String sql = "SELECT * FROM OrderCreate WHERE GiverID = ? OR ReciverID = ?";

            try {
                // Kết nối CSDL
                dbConnect.connect();
                Connection connection = dbConnect.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, userID);
                    stmt.setString(2, userID);

                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            OrderCreate order = new OrderCreate();
                            order.setOrderID(rs.getString("OrderID"));
                            order.setGiverID(rs.getString("GiverID"));
                            order.setReceiverID(rs.getString("ReciverID"));
                            order.setOrderDate(rs.getDate("OrderDate"));
                            orders.add(order);
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi truy vấn đơn hàng: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    // Đóng kết nối
                    if (dbConnect.getConnection() != null) {
                        dbConnect.getConnection().close();
                    }
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
                }
            }
            return orders;
        }
}