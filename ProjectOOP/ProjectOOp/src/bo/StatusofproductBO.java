package bo;

import bean.Statusofproduct;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.StatusofproductDAO;

public class StatusofproductBO {
    private StatusofproductDAO statusofproductDAO = new StatusofproductDAO();

    // Lấy tất cả trạng thái của đơn hàng
    public ArrayList<Statusofproduct> getAllStatusofproduct() {
        try {
            return statusofproductDAO.getAllStatusofproduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm trạng thái đơn hàng mới
    public boolean addStatus(Statusofproduct status) {
        try {
            statusofproductDAO.addStatus(status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateStatus(Statusofproduct status) {
        try {
            statusofproductDAO.updateStatus(status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa trạng thái đơn hàng
    public boolean deleteStatus(String orderID) {
        try {
            statusofproductDAO.deleteStatus(orderID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}