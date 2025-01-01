//cần update thêm ầnàn product
package controller;

import bo.AccUserBO;
import dao.DBconnect;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class AccUserMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccUserBO accUserBO = new AccUserBO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MENU QUẢN LÝ NGƯỜI DÙNG ===");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Thêm người dùng mới");
            System.out.println("3. Sửa thông tin người dùng");
            System.out.println("4. Đổi mật khẩu");
            System.out.println("5. Xóa người dùng");
            System.out.println("6.Xem thông tin người dùng");
            System.out.println("7.Xem danh sách người dùng");
            System.out.println("8. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    editProfile();
                    break;
                case 4:
                    changePassword();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    showUser();
                    break;
                case 7:
                    showUserList();
                    break;
                case 8:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void login() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();
        System.out.print("Nhập Password: ");
        String passwordd = scanner.nextLine();
        // Kiểm tra đăng nhập
        try {
            if (accUserBO.CheckLogin(userID, passwordd)) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("ID hoặc mật khẩu không đúng. Vui lòng kiểm tra lại thông tin.");
            }
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra khi đăng nhập. Vui lòng thử lại sau.");
            e.printStackTrace();
        }
    }

    private static void addUser() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();
        // Kiểm tra sự tồn tại của UserID
        if (!checkUserIDExists1(userID)) {
            return; // Dừng lại nếu không tồn tại UserID
        }
        System.out.print("Nhập Password: ");
        String passwordd = scanner.nextLine();
        System.out.print("Nhập Họ: ");
        String lastName = scanner.nextLine();
        System.out.print("Nhập Tên đệm: ");
        String middleName = scanner.nextLine();
        System.out.print("Nhập Tên: ");
        String firstName = scanner.nextLine();
        System.out.print("Nhập Ngày sinh (yyyy-MM-dd): ");
        Date birthday = parseDate(scanner.nextLine());
        System.out.print("Nhập Giới tính: ");
        String gender = scanner.nextLine();
        System.out.print("Nhập Số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.print("Nhập Tỉnh/Thành phố: ");
        String city = scanner.nextLine();
        System.out.print("Nhập Quận/Huyện: ");
        String district = scanner.nextLine();
        System.out.print("Nhập Xã/Phường: ");
        String ware = scanner.nextLine();
        System.out.print("Nhập Địa chỉ: ");
        String address = scanner.nextLine();

        accUserBO.addAccUser(userID, passwordd, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);
        System.out.println("Thêm người dùng thành công!");
    }
    //Sua thong tin nguoi dung
    private static void editProfile() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();

        // Kiểm tra sự tồn tại của UserID
        if (!checkUserIDExists(userID)) {
            return; // Dừng lại nếu không tồn tại UserID
        }

        // Nhập thông tin cập nhật
        System.out.print("Nhập Họ: ");
        String lastName = scanner.nextLine();
        System.out.print("Nhập Tên đệm: ");
        String middleName = scanner.nextLine();
        System.out.print("Nhập Tên: ");
        String firstName = scanner.nextLine();
        System.out.print("Nhập Ngày sinh (yyyy-MM-dd): ");
        Date birthday = parseDate(scanner.nextLine());
        System.out.print("Nhập Giới tính: ");
        String gender = scanner.nextLine();
        System.out.print("Nhập Số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.print("Nhập Thành phố: ");
        String city = scanner.nextLine();
        System.out.print("Nhập Quận/Huyện: ");
        String district = scanner.nextLine();
        System.out.print("Nhập Xã/Phường: ");
        String ware = scanner.nextLine();
        System.out.print("Nhập Địa chỉ: ");
        String address = scanner.nextLine();

        // Cập nhật thông tin người dùng
        accUserBO.editProfile(userID, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);
        System.out.println("Cập nhật thông tin người dùng thành công!");
    }

    private static void changePassword() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();
        // Kiểm tra sự tồn tại của UserID
        if (!checkUserIDExists1(userID)) {
            return; // Dừng lại nếu không tồn tại UserID
        }
        System.out.print("Nhập mật khẩu mới: ");
        String newPassword = scanner.nextLine();

        accUserBO.changePassword(userID, newPassword);
        System.out.println("Đổi mật khẩu thành công!");
    }
//xoa nguoi dung
private static void deleteUser() {
    // Nhập thông tin người dùng
    System.out.print("Nhập UserID: ");
    String userID = scanner.nextLine();
    System.out.print("Nhập Password: ");
    String passwordd = scanner.nextLine();

    // Kiểm tra thông tin trước khi xóa người dùng
    try {
        // Kiểm tra xem thông tin đăng nhập có hợp lệ không
        if (accUserBO.CheckLogin(userID, passwordd)) {
            // Nếu đăng nhập thành công, gọi phương thức xóa người dùng
            accUserBO.deleteAccUser(userID, passwordd);
            System.out.println("Xóa người dùng thành công!");
        } else {
            // Nếu thông tin không hợp lệ
            System.out.println("Thông tin đăng nhập không chính xác. Không thể xóa người dùng.");
        }
    } catch (SQLException e) {
        System.out.println("Có lỗi xảy ra khi xóa người dùng.");
        e.printStackTrace();
    }
}
// Phương thức hiển thị thông tin người dùng theo userID
public static void showUser() {
    System.out.print("Xem thông tin của userID: ");
    String userID = scanner.nextLine();

    try {
        // Gọi phương thức tìm người dùng theo userID từ AccUserBO
        accUserBO.showUserByID(userID);
    } catch (SQLException e) {
        System.out.println("Có lỗi xảy ra khi tìm kiếm người dùng.");
        e.printStackTrace();
    }
}
// Phương thức hiển thị danh sách người dùng
public static void showUserList() {
    System.out.println("--Đây là chức năng của người quản lý--");
    try {
        // Gọi phương thức showAllUsers từ AccUserBO
        accUserBO.showAllUsers();  // In ra danh sách người dùng
    } catch (SQLException e) {
        System.out.println("Có lỗi xảy ra khi hiển thị danh sách người dùng.");
        e.printStackTrace();
    }
}
    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Ngày không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
            return null;
        }
    }

    // Kiểm tra sự tồn tại của UserID trong cơ sở dữ liệu
    private static DBconnect dbConnect = new DBconnect(); // Khởi tạo đối tượng DBconnect
    private static java.sql.Connection connection;
    // Kiểm tra sự tồn tại của UserID trong cơ sở dữ liệu
    public static boolean checkUserIDExists(String userID) {
        dbConnect.connect(); // Thiết lập kết nối
        connection = dbConnect.getConnection(); // Lấy kết nối từ DBconnect

        String checkSQL = "SELECT userID FROM AccUser WHERE userID = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
            checkStmt.setString(1, userID);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("UserID không tồn tại trong hệ thống!");
                    return false; // Nếu không tìm thấy UserID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true; // Nếu tìm thấy UserID
    }
    // Kiểm tra sự tồn tại của UserID trong cơ sở dữ liệu
    public static boolean checkUserIDExists1(String userID) {
        dbConnect.connect(); // Thiết lập kết nối
        connection = dbConnect.getConnection(); // Lấy kết nối từ DBconnect

        String checkSQL = "SELECT userID FROM AccUser WHERE userID = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
            checkStmt.setString(1, userID);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("UserID đã tồn tại trong hệ thống!");
                    return false; // Nếu không tìm thấy UserID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true; // Nếu tìm thấy UserID
    }
}