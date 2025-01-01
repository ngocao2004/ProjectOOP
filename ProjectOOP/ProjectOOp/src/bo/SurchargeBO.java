package bo;

import bean.Surcharge;
import dao.SurchargeDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurchargeBO {
    private SurchargeDAO surchargeDAO = new SurchargeDAO();

    // Lấy danh sách phụ phí
    public ArrayList<Surcharge> getAllSurcharges() throws SQLException {
        try {
            return surchargeDAO.getAllSurcharges(); // Gọi phương thức từ ServiceDAO
        } catch (SQLException e) {
            // Xử lý lỗi tại đây nếu cần thiết, sau đó ném lại ngoại lệ
            throw e; // Ném lại SQLException để có thể xử lý ở nơi gọi
        }
    }


    // Thêm phụ phí mới
    public boolean addSurcharge(Surcharge surcharge) {
        try {
            surchargeDAO.addSurcharge(surcharge);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật phụ phí
    public boolean updateSurcharge(Surcharge surcharge) {
        try {
            surchargeDAO.updateSurcharge(surcharge);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa phụ phí
    public boolean deleteSurcharge(String surchargeID) {
        try {
            surchargeDAO.deleteSurcharge(surchargeID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // Kiểm tra sự tồn tại của phụ phí
    public boolean surchargeExists(String surchargeID) throws SQLException {
        return surchargeDAO.exists(surchargeID);
    }
    
    public Surcharge getSurchargeByID(String surchargeID) throws SQLException {
        try {
            return surchargeDAO.getSurchargeByID(surchargeID);  // Gọi phương thức từ SurchargeDAO
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Ném lại ngoại lệ để xử lý ở nơi gọi
        }
    }
    
 
    
    public List<Surcharge> searchSurcharge(String searchQuery) throws SQLException {
        return surchargeDAO.searchSurcharge(searchQuery);  // Gọi phương thức tìm kiếm trong DAO
    }
}