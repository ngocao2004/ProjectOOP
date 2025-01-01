package dao;

import bean.AccUser;
import dao.DBconnect;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;



public class AccUserDAO extends DBconnect {
    // Dang Nhap
    public boolean CheckLogin(String userID, String passwordd) throws SQLException {
        connect();
        String sql = "SELECT userID, passwordd FROM AccUser WHERE userID = ? AND passwordd = ?";

        // Sử dụng PreparedStatement để thay thế String.format
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, userID); // Gán giá trị cho tham số đầu tiên (userID)
        stmt.setString(2, passwordd); // Gán giá trị cho tham số thứ hai (password)

        ResultSet rs = stmt.executeQuery(); // Sử dụng executeQuery để thực thi câu truy vấn

        if (rs.next()) {
            return true;
        }
        return false;
    }
    // Thêm người dùng mới vào bảng AccUser
    public void addAccUser(String userID, String passwordd, String lastName, String middleName, String firstName,
                           java.util.Date birthday, String gender, String phone, String city,
                           String district, String ware, String address) {
        connect();  // Kết nối đến cơ sở dữ liệu

        // Câu lệnh SQL để thêm người dùng mới vào bảng AccUser
        String insertSQL = "INSERT INTO AccUser (userID, passwordd, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            // Thiết lập các giá trị cho câu lệnh PreparedStatement
            pstmt.setString(1, userID);
            pstmt.setString(2, passwordd);
            pstmt.setString(3, lastName);
            pstmt.setString(4, middleName);
            pstmt.setString(5, firstName);
            pstmt.setDate(6, new java.sql.Date(birthday.getTime()));
            pstmt.setString(7, gender);
            pstmt.setString(8, phone);
            pstmt.setString(9, city);
            pstmt.setString(10, district);
            pstmt.setString(11, ware);
            pstmt.setString(12, address);

            // Thực thi câu lệnh chèn dữ liệu vào cơ sở dữ liệu
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Sửa thông tin profile
        public void editProfile(String userID, String lastName, String middleName, String firstName, java.util.Date birthday, String gender, String phone, String city, String district, String ware, String address) {
            connect();
            // Câu lệnh SQL
            String sql = "UPDATE AccUser SET lastName = ?, middleName = ?, firstName = ?, birthday = ?, gender = ?, phone = ?, city = ?, district = ?, ware = ?, address = ? WHERE userID = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                // Thiết lập giá trị cho câu lệnh PreparedStatement
                stmt.setString(1, lastName);
                stmt.setString(2, middleName);
                stmt.setString(3, firstName);
                stmt.setDate(4, new java.sql.Date(birthday.getTime()));
                stmt.setString(5, gender);
                stmt.setString(6, phone);
                stmt.setString(7, city);
                stmt.setString(8, district);
                stmt.setString(9, ware);
                stmt.setString(10, address);
                stmt.setString(11, userID);

                // Thực thi câu lệnh
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    //thay doi mat khau
    public void changePassword(String userID, String newpassword) {
        connect();
    String sql = "UPDATE AccUser SET passwordd = ? WHERE userID = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        // Thiết lập giá trị cho câu lệnh PreparedStatement
        stmt.setString(1, newpassword); // Thiết lập mật khẩu mới
        // Thực thi câu lệnh
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý lỗi tại đây nếu cần thiết
    }
}
    public void deleteAccUser(String userID, String passwordd) throws SQLException {
        connect();  // Kết nối đến cơ sở dữ liệu
        String sql = "DELETE FROM AccUser WHERE userID = ? AND passwordd=?";  // Câu lệnh SQL để xóa người dùng theo userID

        // Sử dụng PreparedStatement để thay thế String.format và tránh SQL Injection
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userID);  // Gán giá trị cho tham số đầu tiên (userID)
            stmt.setString(2, passwordd);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi tại đây nếu cần thiết
        }
    }
//hien thi danh sach nguoi dung
    public void showAllUsers() throws SQLException {
        connect();  // Kết nối đến cơ sở dữ liệu
        String sql = "SELECT * FROM AccUser";  // Câu lệnh SQL để lấy toàn bộ người dùng

        // Sử dụng PreparedStatement để tránh SQL Injection (dù không có tham số, nhưng vẫn dùng PreparedStatement cho tính an toàn)
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {  // Thực thi câu lệnh và lấy kết quả từ ResultSet

            // Duyệt qua các dòng kết quả
            while (rs.next()) {
                // Tạo đối tượng AccUser từ dữ liệu trong ResultSet
                String userID = rs.getString("userID");
                String passwordd = rs.getString("passwordd");
                String lastName = rs.getString("lastName");
                String middleName = rs.getString("middleName");
                String firstName = rs.getString("firstName");
                java.sql.Date sqlDate = rs.getDate("birthday");  // Lấy giá trị từ ResultSet
                Date birthday = new Date(sqlDate.getTime());  // Chuyển đổi sang java.util.Date
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String city = rs.getString("city");
                String district = rs.getString("district");
                String ware = rs.getString("ware");
                String address = rs.getString("address");

                // Tạo đối tượng AccUser và gán các giá trị
                AccUser user = new AccUser(userID, passwordd, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);

                // In thông tin người dùng ra màn hình
                System.out.println(user.toString());  // Sử dụng phương thức toString() để hiển thị thông tin người dùng
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý lỗi tại đây nếu cần thiết
        }
    }
//hien thi thong tin nguoi dung
public void showUserByID(String userID) throws SQLException {
    connect(); // Kết nối đến cơ sở dữ liệu
    String sql = "SELECT * FROM AccUser WHERE userID = ?";  // Câu lệnh SQL để tìm người dùng theo userID

    // Sử dụng PreparedStatement để tránh SQL Injection
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, userID); // Gán giá trị cho tham số userID

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {  // Kiểm tra xem có kết quả không
                // Lấy thông tin người dùng từ ResultSet
                String passwordd = rs.getString("passwordd");
                String lastName = rs.getString("lastName");
                String middleName = rs.getString("middleName");
                String firstName = rs.getString("firstName");
                Date birthday = rs.getDate("birthday");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String city = rs.getString("city");
                String district = rs.getString("district");
                String ware = rs.getString("ware");
                String address = rs.getString("address");

                // Tạo đối tượng AccUser
                AccUser user = new AccUser(userID, passwordd, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);

                // In thông tin người dùng
                System.out.println(user.toString());
            } else {
                System.out.println("Không tìm thấy người dùng với userID: " + userID);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý lỗi tại đây nếu cần thiết
    }
}
public static AccUser getUserByID(String userID) throws SQLException {
    AccUser user = null;
    String sql = "SELECT * FROM AccUser WHERE userID = ?";
    
    // Kết nối cơ sở dữ liệu
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gvproject", "caongo2004", "ngovancao2004");
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, userID);
        ResultSet rs = stmt.executeQuery();
        
        // Nếu có người dùng tương ứng, tạo đối tượng User từ dữ liệu trả về
        if (rs.next()) {
            user = new AccUser(
                    rs.getString("userID"),
                    rs.getString("password"),
                    rs.getString("lastName"),
                    rs.getString("middleName"),
                    rs.getString("firstName"),
                    rs.getDate("birthday"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("city"),
                    rs.getString("district"),
                    rs.getString("ware"),
                    rs.getString("address")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Đảm bảo bắt lỗi SQL
    }
    
    return user;
}


}