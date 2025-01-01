package bo;

import bean.Send;
import dao.SendDAO;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class SendBO {
    private SendDAO sendDAO = new SendDAO();

    // Lấy tất cả các bản ghi giao hàng
    public ArrayList<Send> getAllSends() {
        try {
            return sendDAO.getAllSends();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm giao hàng mới
    public boolean addSend(Send send) throws SQLException {
        // Kiểm tra nếu orderID đã tồn tại
        if (sendDAO.existsOrderID(send.getOrderID())) {
            // Nếu orderID đã tồn tại, hiển thị thông báo lỗi và không thêm vào
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đơn hàng với Order ID này đã tồn tại trong hệ thống! Không thể thêm mới.");
            alert.showAndWait();
            return false;  // Dừng thêm đơn hàng
        } else {
            // Nếu orderID chưa tồn tại, thực hiện thêm giao hàng vào cơ sở dữ liệu
            sendDAO.addSend(send);
            return true;
        }
    }
    // Cập nhật trạng thái giao hàng
    public boolean updateSend(Send send) {
        try {
            sendDAO.updateSend(send);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa giao hàng
    public boolean deleteSend(String employeeID, String orderID) {
        try {
            sendDAO.deleteSend(employeeID, orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Lấy thông tin giao hàng theo OrderID
    public ArrayList<Send> getSendByOrderID(String orderID) {
        try {
            return sendDAO.getSendByOrderID(orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

}