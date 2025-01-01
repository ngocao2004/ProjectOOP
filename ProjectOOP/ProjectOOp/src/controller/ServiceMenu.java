package controller;

import bo.ServiceBO;
import model.bean.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceMenu {
    // Hiển thị menu cho người dùng chọn chức năng
    public static void showMenu() {
        while (true) {
            System.out.println("\n=== MENU DỊCH VỤ ===");
            System.out.println("1. Hiển thị tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Cập nhật dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showAllServices();
                    break;
                case 2:
                    addService();
                    break;
                case 3:
                    updateService();
                    break;
                case 4:
                    deleteService();
                    break;
                case 5:
                    System.out.println("Thoát khỏi chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
    public static void main(String[] args) {
        // Hiển thị menu dịch vụ
        showMenu();
    }

    private static Scanner scanner = new Scanner(System.in);
    private static ServiceBO serviceBO = new ServiceBO();
    // Hiển thị tất cả dịch vụ
    public static void showAllServices() {
        try {
            ArrayList<Service> services = serviceBO.getAllServices();
            if (services != null && !services.isEmpty()) {
                System.out.println("Danh sách các dịch vụ:");
                for (Service service : services) {
                    System.out.println(service.toString());
                }
            } else {
                System.out.println("Không có dịch vụ nào.");
            }
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra khi hiển thị danh sách dịch vụ.");
            e.printStackTrace();
        }
    }
    // Thêm dịch vụ mới
    public static void addService() {
        System.out.println("--Đây là chức năng của người quản lý--");

        System.out.print("ServiceID: ");
        String serviceID = scanner.nextLine();

        System.out.print("ServiceName: ");
        String serviceName = scanner.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("MaxDistance: ");
        String maxDistance = scanner.nextLine();

        // Tạo đối tượng Service
        Service service = new Service(serviceID, serviceName, price, maxDistance);

        // Thêm dịch vụ
        if (serviceBO.addService(service)) {
            System.out.println("Thêm dịch vụ thành công!");
        } else {
            System.out.println("Thêm dịch vụ thất bại.");
        }
    }

    // Cập nhật dịch vụ
    public static void updateService() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập ServiceID cần cập nhập: ");
        String serviceID = scanner.nextLine();

        // Kiểm tra xem dịch vụ có tồn tại không (bạn có thể gọi phương thức để kiểm tra từ ServiceBO nếu cần)
        // Giả sử kiểm tra đã được thực hiện trước

        System.out.print("ServiceName mới: ");
        String serviceName = scanner.nextLine();

        System.out.print("Price mới: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("MaxDistance mới: ");
        String maxDistance = scanner.nextLine();

        // Tạo đối tượng Service
        Service service = new Service(serviceID, serviceName, price, maxDistance);

        // Cập nhật dịch vụ
        if (serviceBO.updateService(service)) {
            System.out.println("Cập nhật dịch vụ thành công!");
        } else {
            System.out.println("Cập nhật dịch vụ thất bại.");
        }
    }

    // Xóa dịch vụ
    public static void deleteService() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập ServiceID cần xóa: ");
        String serviceID = scanner.nextLine();

        // Xóa dịch vụ
        if (serviceBO.deleteService(serviceID)) {
            System.out.println("Xóa dịch vụ thành công!");
        } else {
            System.out.println("Xóa dịch vụ thất bại.");
        }
    }

}