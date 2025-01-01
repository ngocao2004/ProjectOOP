package controller;

import bo.WarehouseBO;
import model.bean.Warehouse;

import java.sql.SQLException;
import java.util.Scanner;

public class WarehouseMenu {
    private static WarehouseBO warehouseBO = new WarehouseBO();
    private static Scanner scanner = new Scanner(System.in);

    // Hiển thị menu cho người dùng chọn chức năng
    public static void showMenu() {
        while (true) {
            System.out.println("\n=== MENU KHO ===");
            System.out.println("1. Hiển thị tất cả các kho");
            System.out.println("2. Thêm kho");
            System.out.println("3. Cập nhật thông tin kho");
            System.out.println("4. Lấy thông tin kho theo WarehouseID");
            System.out.println("5. Kiểm tra nhật ký thay đổi kho");
            System.out.println("6. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showAllWarehouses();
                    break;
                case 2:
                    addWarehouse();
                    break;
                case 3:
                    updateWarehouse();
                    break;
                case 4:
                    getWarehouseByID();
                    break;
                case 5:
                    checkWarehouseLogs();
                    break;
                case 6:
                    System.out.println("Thoát khỏi chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
    // Phương thức main để bắt đầu chương trình
    public static void main(String[] args) {
        showMenu();  // Hiển thị menu kho
    }

    // Hiển thị tất cả các kho
    private static void showAllWarehouses() {
        try {
            var warehouses = warehouseBO.getAllWarehouses();
            if (warehouses != null && !warehouses.isEmpty()) {
                System.out.println("Danh sách các kho:");
                for (Warehouse warehouse : warehouses) {
                    System.out.println(warehouse);
                }
            } else {
                System.out.println("Không có kho nào trong cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra khi lấy danh sách kho: " + e.getMessage());
        }
    }

    // Thêm kho
    private static void addWarehouse() {
        System.out.print("Nhập WarehouseID của kho mới: ");
        String warehouseID = scanner.nextLine();
        System.out.print("Nhập tên kho: ");
        String wareName = scanner.nextLine();
        System.out.print("Nhập thành phố: ");
        String city = scanner.nextLine();
        System.out.print("Nhập quận: ");
        String district = scanner.nextLine();
        System.out.print("Nhập phường/xã: ");
        String ward = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String address = scanner.nextLine();

        Warehouse warehouse = new Warehouse(warehouseID, wareName, city, district, ward, address);
        warehouseBO.updateWarehouse(warehouse);
        System.out.println("Kho đã được thêm thành công.");
    }

    // Cập nhật thông tin kho
    private static void updateWarehouse() {
        System.out.print("Nhập WarehouseID của kho cần cập nhật: ");
        String warehouseID = scanner.nextLine();

        Warehouse warehouse = warehouseBO.getWarehouseByID(warehouseID);
        if (warehouse != null) {
            System.out.println("Cập nhật thông tin kho: " + warehouse);
            System.out.print("Nhập tên kho mới: ");
            String wareName = scanner.nextLine();
            System.out.print("Nhập thành phố mới: ");
            String city = scanner.nextLine();
            System.out.print("Nhập quận mới: ");
            String district = scanner.nextLine();
            System.out.print("Nhập phường/xã mới: ");
            String ward = scanner.nextLine();
            System.out.print("Nhập địa chỉ mới: ");
            String address = scanner.nextLine();

            warehouse.setWareName(wareName);
            warehouse.setCity(city);
            warehouse.setDistrict(district);
            warehouse.setWard(ward);
            warehouse.setAddress(address);

            warehouseBO.updateWarehouse(warehouse);
            System.out.println("Thông tin kho đã được cập nhật.");
        } else {
            System.out.println("Không tìm thấy kho với WarehouseID: " + warehouseID);
        }
    }

    // Lấy thông tin kho theo WarehouseID
    private static void getWarehouseByID() {
        System.out.print("Nhập WarehouseID cần tìm: ");
        String warehouseID = scanner.nextLine();

        Warehouse warehouse = warehouseBO.getWarehouseByID(warehouseID);
        if (warehouse != null) {
            System.out.println("Thông tin kho: " + warehouse);
        } else {
            System.out.println("Không tìm thấy kho với WarehouseID: " + warehouseID);
        }
    }

    // Kiểm tra nhật ký thay đổi kho
    private static void checkWarehouseLogs() {
        try {
            warehouseBO.checkWarehouseLogs();
        } catch (SQLException e) {
            System.out.println("Có lỗi khi kiểm tra nhật ký thay đổi kho: " + e.getMessage());
        }
    }
}