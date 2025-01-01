package dao;

import bean.Send;
import dao.DBconnect;

import java.sql.*;
import java.util.ArrayList;

public class SendDAO extends DBconnect {

    // Lấy tất cả các bản ghi trong bảng Send
    public ArrayList<Send> getAllSends() throws SQLException {
        connect();
        String sql = "SELECT * FROM Send";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Send> sends = new ArrayList<>();
        while (rs.next()) {
            Send send = new Send();
            send.setEmployeeID(rs.getString("EmployeeID"));
            send.setOrderID(rs.getString("OrderID"));
            send.setReceiptDate(rs.getDate("ReceiptDate"));
            send.setEstimatedDate(rs.getDate("EstimatedDate"));
            send.setActualDate(rs.getDate("ActualDate"));
            send.setSendStatus(rs.getString("SendStatus"));
            sends.add(send);
        }
        return sends;
    }

    // Thêm bản ghi giao hàng vào bảng Send
    public void addSend(Send send) throws SQLException {
        connect();

        String sql = "INSERT INTO Send (EmployeeID, OrderID, ReceiptDate, EstimatedDate, ActualDate, SendStatus) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, send.getEmployeeID());
        stmt.setString(2, send.getOrderID());
        stmt.setDate(3, new java.sql.Date(send.getReceiptDate().getTime()));
        stmt.setDate(4, new java.sql.Date(send.getEstimatedDate().getTime()));
        stmt.setDate(5, send.getActualDate() != null ? new java.sql.Date(send.getActualDate().getTime()) : null);
        stmt.setString(6, send.getSendStatus());

        stmt.executeUpdate();
    }

    // Cập nhật trạng thái giao hàng
    public void updateSend(Send send) throws SQLException {
        connect();

        String sql = "UPDATE Send SET ReceiptDate = ?, EstimatedDate = ?, ActualDate = ?, SendStatus = ? WHERE EmployeeID = ? AND OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(send.getReceiptDate().getTime()));
        stmt.setDate(2, new java.sql.Date(send.getEstimatedDate().getTime()));
        stmt.setDate(3, send.getActualDate() != null ? new java.sql.Date(send.getActualDate().getTime()) : null);
        stmt.setString(4, send.getSendStatus());
        stmt.setString(5, send.getEmployeeID());
        stmt.setString(6, send.getOrderID());

        stmt.executeUpdate();
    }

    // Xóa bản ghi giao hàng
    public void deleteSend(String employeeID, String orderID) throws SQLException {
        connect();

        String sql = "DELETE FROM Send WHERE EmployeeID = ? AND OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, employeeID);
        stmt.setString(2, orderID);

        stmt.executeUpdate();
    }
    // Lấy thông tin giao hàng theo OrderID
    public ArrayList<Send> getSendByOrderID(String orderID) throws SQLException {
        connect();
        String sql = "SELECT * FROM Send WHERE OrderID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, orderID);

        ResultSet rs = stmt.executeQuery();
        ArrayList<Send> sends = new ArrayList<>();
        while (rs.next()) {
            Send send = new Send();
            send.setEmployeeID(rs.getString("EmployeeID"));
            send.setOrderID(rs.getString("OrderID"));
            send.setReceiptDate(rs.getDate("ReceiptDate"));
            send.setEstimatedDate(rs.getDate("EstimatedDate"));
            send.setActualDate(rs.getDate("ActualDate"));
            send.setSendStatus(rs.getString("SendStatus"));
            sends.add(send);
        }
        return sends;
    }
    
    public boolean existsOrderID(String orderID) throws SQLException {
        String query = "SELECT COUNT(*) FROM send WHERE orderID = ?";
        
        
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, orderID);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Nếu count > 0 thì orderID đã tồn tại
            }
    
        return false;  // Nếu không có đơn hàng nào thì trả về false
    }



}