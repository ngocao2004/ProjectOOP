package bo;

import bean.Service;
import dao.ServiceDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceBO {
    private ServiceDAO serviceDAO = new ServiceDAO(); // Tạo đối tượng ServiceDAO để làm việc với cơ sở dữ liệu

    // Lấy danh sách tất cả các dịch vụ
    public ArrayList<Service> getAllServices() throws SQLException {
        try {
            return serviceDAO.getAllServices(); // Gọi phương thức từ ServiceDAO
        } catch (SQLException e) {
            // Xử lý lỗi tại đây nếu cần thiết, sau đó ném lại ngoại lệ
            throw e; // Ném lại SQLException để có thể xử lý ở nơi gọi
        }
    }

    // Thêm dịch vụ mới
    public boolean addService(Service service) {
        try {
            serviceDAO.addService(service); // Gọi phương thức từ DAO để thêm dịch vụ
            return true; // Thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Thêm thất bại
    }

    // Cập nhật thông tin dịch vụ
    public boolean updateService(Service service) {
        try {
            serviceDAO.updateService(service); // Gọi phương thức từ DAO để cập nhật dịch vụ
            return true; // Cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Cập nhật thất bại
    }

    // Xóa dịch vụ
    public boolean deleteService(String serviceID) {
        try {
            serviceDAO.deleteService(serviceID); // Gọi phương thức từ DAO để xóa dịch vụ
            return true; // Xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Xóa thất bại
    }
}