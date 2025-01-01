package bo;

import bean.OrderCreate;
import bean.OrderDetails;
import dao.OrderCreateDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCreateBO {
    private OrderCreateDAO orderCreateDAO = new OrderCreateDAO();

    // Lấy danh sách tất cả đơn hàng
    public ArrayList<OrderCreate> getAllOrders() {
        try {
            return orderCreateDAO.getAllOrders(); // Gọi phương thức từ DAO
        } catch (SQLException e) {
            // Xử lý lỗi tại đây, ghi log hoặc thông báo lỗi
            System.err.println("Error while fetching orders: " + e.getMessage());
            // Trả về danh sách rỗng nếu xảy ra lỗi
            return new ArrayList<>();
        }
    }

   // Thêm đơn hàng mới
    public boolean addOrder(OrderCreate order) {
        try {
            orderCreateDAO.addOrder(order);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Nguoi gui khong the tu dat don hang cua minh!")) {
                System.out.println("Loi: Nguoi gui khong the la nguoi nhan");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Cập nhật đơn hàng
    public boolean updateOrder(OrderCreate order) {
        try {
            orderCreateDAO.updateOrder(order);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Nguoi gui khong the tu dat don hang cua minh!")) {
                System.out.println("Loi: Nguoi gui khong the la nguoi nhan");
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Xóa đơn hàng
    public boolean deleteOrder(String orderID) {
        try {
            orderCreateDAO.deleteOrder(orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Lấy danh sách đơn hàng theo UserID
    public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
        try {
            return orderCreateDAO.getOrdersByUserID(userID);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách đơn hàng trong BO: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /*
   // Lấy danh sách đơn hàng theo UserID (gửi hoặc nhận)
   public ArrayList<OrderCreate> getOrdersByUserID(String userID) {
       ArrayList<OrderCreate> orders = new ArrayList<>();
       try {
           orders = orderCreateDAO.getOrdersByUserID(userID);
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage());
       }
       return orders;
   }
     */
}