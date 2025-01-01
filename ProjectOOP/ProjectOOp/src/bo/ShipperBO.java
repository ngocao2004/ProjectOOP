package bo;

import dao.ShipperDAO;
import bean.Shipper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShipperBO {
    private ShipperDAO shipperDAO = new ShipperDAO();

    // Lấy danh sách tất cả Shipper
    public ArrayList<Shipper> getAllShippers() throws SQLException {
        try {
            return shipperDAO.getAllShippers();
        } catch (SQLException e) {
            throw e;  // Ném lại SQLException để có thể xử lý ở nơi gọi
        }
    }

    // Thêm shipper mới
    public boolean addShipper(Shipper shipper) {
        try {
            shipperDAO.addShipper(shipper);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin shipper
    public boolean updateShipper(Shipper shipper) {
        try {
            shipperDAO.updateShipper(shipper);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa shipper
    public boolean deleteShipper(String employeeID) {
        try {
            shipperDAO.deleteShipper(employeeID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sắp xếp shipper theo ID (ascending or descending)
    public ArrayList<Shipper> getShippersSortedById(boolean ascending) {
        try {
            ArrayList<Shipper> shippers = getAllShippers();
            // Sắp xếp theo employeeID
            Collections.sort(shippers, (s1, s2) -> {
                if (ascending) {
                    return s1.getEmployeeID().compareTo(s2.getEmployeeID());
                } else {
                    return s2.getEmployeeID().compareTo(s1.getEmployeeID());
                }
            });
            return shippers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Sắp xếp shipper theo tên (ascending or descending)
    public ArrayList<Shipper> getShippersSortedByName(boolean ascending) {
        try {
            ArrayList<Shipper> shippers = getAllShippers();
            // Sắp xếp theo full name (firstName + middleName + lastName)
            Collections.sort(shippers, (s1, s2) -> {
                String fullName1 = s1.getFirstName() + " " + s1.getMiddleName() + " " + s1.getLastName();
                String fullName2 = s2.getFirstName() + " " + s2.getMiddleName() + " " + s2.getLastName();
                if (ascending) {
                    return fullName1.compareTo(fullName2);
                } else {
                    return fullName2.compareTo(fullName1);
                }
            });
            return shippers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Tìm kiếm shipper theo ID hoặc tên (firstName, middleName, lastName)
    public ArrayList<Shipper> searchShippers(String searchTerm) {
        try {
            // Lấy tất cả danh sách shipper
            ArrayList<Shipper> allShippers = getAllShippers();
            ArrayList<Shipper> result = new ArrayList<>();

            for (Shipper shipper : allShippers) {
                // Kiểm tra nếu searchTerm khớp với employeeID hoặc tên
                if (shipper.getEmployeeID().contains(searchTerm) || 
                    shipper.getFirstName().contains(searchTerm) || 
                    shipper.getMiddleName().contains(searchTerm) || 
                    shipper.getLastName().contains(searchTerm)) {
                    result.add(shipper);  // Thêm vào danh sách kết quả
                }
            }

            return result;  // Trả về danh sách shipper khớp với từ khóa tìm kiếm
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
 // Trong lớp ShipperBO
    public ArrayList<Shipper> searchShippersByIdOrName(String searchTerm) {
        ArrayList<Shipper> result = new ArrayList<>();
        
        // Lấy tất cả các shipper (có thể là từ cơ sở dữ liệu hoặc một danh sách tạm)
        try {
            ArrayList<Shipper> allShippers = getAllShippers(); // Giả sử getAllShippers() trả về danh sách tất cả các shipper

            for (Shipper shipper : allShippers) {
                // Kiểm tra nếu searchTerm có trong Shipper ID, First Name hoặc Last Name
                if (shipper.getEmployeeID().contains(searchTerm) || 
                    shipper.getFirstName().contains(searchTerm) || 
                    shipper.getLastName().contains(searchTerm)) {
                    result.add(shipper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Có thể thêm một thông báo lỗi hoặc xử lý ngoại lệ ở đây
        }

        return result;
    }
    
    public List<Shipper> searchShippersByName(String firstName, String middleName, String lastName) {
        try {
            return shipperDAO.searchShippersByName(firstName, middleName, lastName); // Gọi phương thức từ DAO
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu có lỗi
    }
    
    public Shipper getShipperById(String shipperId) {
        try {
            ArrayList<Shipper> shippers = getAllShippers();  // Lấy tất cả shipper từ cơ sở dữ liệu
            for (Shipper shipper : shippers) {
                if (shipper.getEmployeeID().equals(shipperId)) {
                    return shipper;  // Trả về shipper nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu không tìm thấy shipper
    }
    
    public List<String> getAllEmployeeIDs() {
        return shipperDAO.getAllEmployeeIDs();
    }


}
