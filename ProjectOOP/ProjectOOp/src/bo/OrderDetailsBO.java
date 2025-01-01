package bo;

import bean.OrderDetails;
import dao.OrderDetailsDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBO {
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

    // Lấy danh sách chi tiết đơn hàng theo OrderID
    public ArrayList<OrderDetails> getOrderDetailsByOrderID(String orderID) {
        try {
            return orderDetailsDAO.getOrderDetailsByOrderID(orderID);
        } catch (Exception e) {
            System.err.println("Lỗi trong BO khi lấy chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Trả về danh sách rỗng nếu xảy ra lỗi
        }
    }

    // Thêm OrderDetails
    public void addOrderDetails(OrderDetails orderDetails) {
        try {
            orderDetailsDAO.addOrderDetails(orderDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách OrderDetails
    public ArrayList<OrderDetails> getListOrderDetails() {
        try {
            return orderDetailsDAO.getListOrderDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật OrderDetails
    public void updateOrderDetails(OrderDetails orderDetails) {
        try {
            orderDetailsDAO.updateOrderDetails(orderDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa OrderDetails
    public void deleteOrderDetails(String itemID) {
        try {
            orderDetailsDAO.deleteOrderDetails(itemID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}