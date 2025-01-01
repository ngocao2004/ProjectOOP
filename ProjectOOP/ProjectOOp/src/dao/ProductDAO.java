package dao;

import bean.Product;
import java.sql.*;
import java.util.ArrayList;

// Lớp ProductDAO thực hiện các thao tác CRUD trên bảng Product trong cơ sở dữ liệu.
public class ProductDAO extends DBconnect {
    private Connection connection;

    // Constructor: Kết nối cơ sở dữ liệu khi khởi tạo ProductDAO.
    public ProductDAO() {
        connect(); // Kết nối cơ sở dữ liệu.
		this.connection = getConnection();
    }

    // Đảm bảo rằng kết nối luôn tồn tại.
    private Connection ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect(); // Kết nối lại nếu kết nối hiện tại không hợp lệ.
            connection = getConnection();
        }
        return connection;
    }

    // Đóng kết nối sau khi sử dụng.
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả các sản phẩm.
     * @return Danh sách tất cả các sản phẩm.
     */
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setOrderID(rs.getString("OrderID"));
                product.setPayer(rs.getString("Payer"));
                product.setPickupCity(rs.getString("PickupCity"));
                product.setPickupDistrict(rs.getString("PickupDistrict"));
                product.setPickupWard(rs.getString("PickupWard"));
                product.setPickupAddress(rs.getString("PickupAddress"));
                product.setRecipientName(rs.getString("RecipientName"));
                product.setPhoneRecipient(rs.getString("PhoneRecipient"));
                product.setDeliveryCity(rs.getString("DeliveryCity"));
                product.setDeliveryDistrict(rs.getString("DeliveryDistrict"));
                product.setDeliveryWard(rs.getString("DeliveryWard"));
                product.setDeliveryAddress(rs.getString("DeliveryAddress"));
                product.setServiceID(rs.getString("ServiceID"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn sản phẩm: " + e.getMessage());
        }
        return products;
    }

    /**
     * Lấy danh sách các sản phẩm theo UserID (GiverID hoặc ReceiverID).
     * @param userID ID của người dùng.
     * @return Danh sách sản phẩm liên quan đến UserID.
     */
    public ArrayList<Product> getProductsByUserID(String userID) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE OrderID IN (" +
                     "SELECT OrderID FROM OrderCreate WHERE GiverID = ? OR ReciverID = ?)";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setOrderID(rs.getString("OrderID"));
                    product.setPayer(rs.getString("Payer"));
                    product.setPickupCity(rs.getString("PickupCity"));
                    product.setPickupDistrict(rs.getString("PickupDistrict"));
                    product.setPickupWard(rs.getString("PickupWard"));
                    product.setPickupAddress(rs.getString("PickupAddress"));
                    product.setRecipientName(rs.getString("RecipientName"));
                    product.setPhoneRecipient(rs.getString("PhoneRecipient"));
                    product.setDeliveryCity(rs.getString("DeliveryCity"));
                    product.setDeliveryDistrict(rs.getString("DeliveryDistrict"));
                    product.setDeliveryWard(rs.getString("DeliveryWard"));
                    product.setDeliveryAddress(rs.getString("DeliveryAddress"));
                    product.setServiceID(rs.getString("ServiceID"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn sản phẩm theo UserID: " + e.getMessage());
        }
        return products;
    }

    /**
     * Thêm chi tiết đơn hàng mới vào bảng Product.
     * @param product Đối tượng sản phẩm cần thêm.
     */
    public void addProduct(Product product) {
        String sql = "INSERT INTO Product (OrderID, Payer, PickupCity, PickupDistrict, PickupWard, PickupAddress, " +
                     "RecipientName, PhoneRecipient, DeliveryCity, DeliveryDistrict, DeliveryWard, DeliveryAddress, ServiceID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getOrderID());
            stmt.setString(2, product.getPayer());
            stmt.setString(3, product.getPickupCity());
            stmt.setString(4, product.getPickupDistrict());
            stmt.setString(5, product.getPickupWard());
            stmt.setString(6, product.getPickupAddress());
            stmt.setString(7, product.getRecipientName());
            stmt.setString(8, product.getPhoneRecipient());
            stmt.setString(9, product.getDeliveryCity());
            stmt.setString(10, product.getDeliveryDistrict());
            stmt.setString(11, product.getDeliveryWard());
            stmt.setString(12, product.getDeliveryAddress());
            stmt.setString(13, product.getServiceID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin sản phẩm trong bảng Product.
     * @param product Đối tượng sản phẩm cần cập nhật.
     */
    public void updateProduct(Product product) {
        String sql = "UPDATE Product SET Payer = ?, PickupCity = ?, PickupDistrict = ?, PickupWard = ?, PickupAddress = ?, " +
                     "RecipientName = ?, PhoneRecipient = ?, DeliveryCity = ?, DeliveryDistrict = ?, DeliveryWard = ?, " +
                     "DeliveryAddress = ?, ServiceID = ? WHERE OrderID = ?";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getPayer());
            stmt.setString(2, product.getPickupCity());
            stmt.setString(3, product.getPickupDistrict());
            stmt.setString(4, product.getPickupWard());
            stmt.setString(5, product.getPickupAddress());
            stmt.setString(6, product.getRecipientName());
            stmt.setString(7, product.getPhoneRecipient());
            stmt.setString(8, product.getDeliveryCity());
            stmt.setString(9, product.getDeliveryDistrict());
            stmt.setString(10, product.getDeliveryWard());
            stmt.setString(11, product.getDeliveryAddress());
            stmt.setString(12, product.getServiceID());
            stmt.setString(13, product.getOrderID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    /**
     * Xóa sản phẩm theo OrderID.
     * @param orderID Mã đơn hàng cần xóa.
     */
    public void deleteProduct(String orderID) {
        String sql = "DELETE FROM Product WHERE OrderID = ?";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql)) {
            stmt.setString(1, orderID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }
    
 // Trong lớp ProductDAO
    public ArrayList<String> getAllOrderIDs() {
        ArrayList<String> orderIDs = new ArrayList<>();
        String sql = "SELECT OrderID FROM Product";

        try (PreparedStatement stmt = ensureConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orderIDs.add(rs.getString("OrderID")); // Thêm OrderID vào danh sách
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách OrderID: " + e.getMessage());
        }

        return orderIDs; // Trả về danh sách OrderID
    }

    
    
}
