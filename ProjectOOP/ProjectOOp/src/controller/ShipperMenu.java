package controller;

import bo.ShipperBO;
import model.bean.Shipper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShipperMenu {
    private static ShipperBO shipperBO = new ShipperBO();  // Đối tượng ShipperBO để gọi các phương thức
    private static Scanner scanner = new Scanner(System.in); // Scanner để nhập từ bàn phím
    // Menu chính
    public static void showMenu() {
        while (true) {
            System.out.println("\n=== Menu Quản Lý Shipper ===");
            System.out.println("1. Hiển thị tất cả shipper");
            System.out.println("2. Thêm shipper mới");
            System.out.println("3. Cập nhật shipper");
            System.out.println("4. Xóa shipper");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Đọc ký tự dòng mới sau khi nhập số

            switch (choice) {
                case 1:
                    showAllShippers();
                    break;
                case 2:
                    addShipper();
                    break;
                case 3:
                    updateShipper();
                    break;
                case 4:
                    deleteShipper();
                    break;
                case 5:
                    System.out.println("Thoát khỏi chương trình.");
                    return;  // Thoát khỏi vòng lặp và kết thúc chương trình
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    public static void main(String[] args) {
        // Hiển thị menu cho người dùng
        showMenu();
    }
    // Hiển thị danh sách tất cả các shipper
    public static void showAllShippers() {
        System.out.println("--Đây là chức năng của người quản lý--");
        try {
            ArrayList<Shipper> shippers = shipperBO.getAllShippers();
            if (shippers != null && !shippers.isEmpty()) {
                System.out.println("Danh sách các shipper:");
                for (Shipper shipper : shippers) {
                    System.out.println(shipper.toString());
                }
            } else {
                System.out.println("Không có shipper nào.");
            }
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra khi hiển thị danh sách shipper.");
            e.printStackTrace();
        }
    }

    // Thêm shipper mới
    public static void addShipper() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.println("Nhập thông tin shipper mới:");
        System.out.print("EmployeeID: ");
        String employeeID = scanner.nextLine();
        System.out.print("LastName: ");
        String lastName = scanner.nextLine();
        System.out.print("MiddleName: ");
        String middleName = scanner.nextLine();
        System.out.print("FirstName: ");
        String firstName = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Birthday (yyyy-mm-dd): ");
        String birthdayStr = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("HomeTown: ");
        String homeTown = scanner.nextLine();

        // Tạo đối tượng Shipper từ thông tin người dùng nhập
        Shipper shipper = new Shipper(employeeID, lastName, middleName, firstName, gender, java.sql.Date.valueOf(birthdayStr), phone, homeTown);

        if (shipperBO.addShipper(shipper)) {
            System.out.println("Thêm shipper thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi thêm shipper.");
        }
    }

    // Cập nhật thông tin shipper
    public static void updateShipper() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập EmployeeID của shipper muốn cập nhật: ");
        String employeeID = scanner.nextLine();

        // Bạn có thể gọi phương thức để lấy thông tin của shipper và hiển thị các trường cần cập nhật
        // Sau đó yêu cầu người dùng nhập thông tin mới

        System.out.print("Nhập Họ mới: ");
        String lastName = scanner.nextLine();
        System.out.print("Nhập Tên Đệm mới: ");
        String middleName = scanner.nextLine();
        System.out.print("Nhập Tên mới: ");
        String firstName = scanner.nextLine();
        System.out.print("Nhập Giới tính: ");
        String gender = scanner.nextLine();
        System.out.print("Nhập Ngày sinh (yyyy-mm-dd): ");
        String birthdayStr = scanner.nextLine();
        System.out.print("Nhập SĐT mới: ");
        String phone = scanner.nextLine();
        System.out.print("Nhập Địa chỉ mới: ");
        String homeTown = scanner.nextLine();

        Shipper shipper = new Shipper(employeeID, lastName, middleName, firstName, gender, java.sql.Date.valueOf(birthdayStr), phone, homeTown);
        if (shipperBO.updateShipper(shipper)) {
            System.out.println("Cập nhật shipper thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi cập nhật shipper.");
        }
    }

    // Xóa shipper
    public static void deleteShipper() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập EmployeeID cần xóa: ");
        String employeeID = scanner.nextLine();

        if (shipperBO.deleteShipper(employeeID)) {
            System.out.println("Xóa shipper thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi xóa shipper.");
        }
    }
}