package bo;

import bean.Product;
import dao.DBconnect;
import dao.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ProductBO {
    private ProductDAO productDAO = new ProductDAO();

   /* // Lấy danh sách các sản phẩm theo UserID
    public ArrayList<Product> getProductsByUserID(String userID) throws SQLException {
        try {
            return productDAO.getProductsByUserID(userID); // Gọi phương thức từ ProductDAO
        } catch (SQLException e) {
            // Xử lý lỗi nếu cần thiết
            System.err.println("Error while fetching products for UserID " + userID + ": " + e.getMessage());
            // Ném lại ngoại lệ để có thể xử lý tại nơi gọi
            throw e;
        }
    } */
   // Lấy danh sách các sản phẩm theo UserID
   public ArrayList<Product> getProductsByUserID(String userID) {
       try {
           return productDAO.getProductsByUserID(userID);
       } catch (Exception e) {
           System.err.println("Lỗi trong BO khi lấy danh sách sản phẩm theo UserID: " + e.getMessage());
           e.printStackTrace();
           return new ArrayList<>(); // Trả về danh sách rỗng nếu xảy ra lỗi
       }
   }

   // Lấy danh sách tất cả chi tiết đơn hàng
    public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();

    }

    // Thêm chi tiết đơn hàng mới
    public boolean addProduct(Product product) {
        productDAO.addProduct(product);
		return true;
    }
    
    public boolean updateProduct(Product product) {
        productDAO.updateProduct(product);  // Call the DAO method to update the product
		return true;
    }

    // Xóa sản phẩm
    public boolean deleteProduct(String orderID) {
        productDAO.deleteProduct(orderID);  // Call the DAO method to delete the product
		return true;
    }
    
 
    
 // Trong lớp ProductBO
    public ArrayList<String> getAllOrderIDs() {
        return productDAO.getAllOrderIDs(); // Gọi hàm từ lớp ProductDAO
    }

    




}
