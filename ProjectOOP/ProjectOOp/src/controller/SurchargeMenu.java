package controller;
import bo.SurchargeBO;
import dao.SurchargeDAO;
import model.bean.Surcharge;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SurchargeMenu {
    private static SurchargeBO surchargeBO = new SurchargeBO();  // Đối tượng SurchargeBO để gọi các phương thức
    private static Scanner scanner = new Scanner(System.in);    // Scanner để nhập từ bàn phím
    // Menu chính
    public static void showMenu() {
        while (true) {
            System.out.println("\n=== Menu Quản Lý Phụ Phí ===");
            System.out.println("1. Hiển thị tất cả phụ phí");
            System.out.println("2. Thêm phụ phí");
            System.out.println("3. Cập nhật phụ phí");
            System.out.println("4. Xóa phụ phí");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllSurcharges();
                    break;
                case 2:
                    addSurcharge();
                    break;
                case 3:
                    updateSurcharge();
                    break;
                case 4:
                    deleteSurcharge();
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

    // Hiển thị danh sách phụ phí
    public static void showAllSurcharges() {
        try {
            ArrayList<Surcharge> surcharges = surchargeBO.getAllSurcharges();
            if (surcharges != null && !surcharges.isEmpty()) {
                System.out.println("Danh sách các phụ phí:");
                for (Surcharge surcharge : surcharges) {
                    System.out.println(surcharge.toString());
                }
            } else {
                System.out.println("Không có phụ phí nào.");
            }
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra khi hiển thị danh sách phụ phí.");
            e.printStackTrace();
        }
    }

    // Thêm phụ phí mới
    
    public static void addSurcharge() throws SQLException {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập SurchargeID: ");
        String surchargeID = scanner.nextLine();
        System.out.print("Nhập Surcharge Name: ");
        String surchargeName = scanner.nextLine();
        System.out.print("Nhập Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Đọc ký tự dòng mới

        Surcharge surcharge = new Surcharge(surchargeID, surchargeName, price);
            // Kiểm tra xem phụ phí đã tồn tại chưa
        SurchargeDAO src=new SurchargeDAO();
        boolean exist;
        if ( exist = src.exists(surcharge.getSurchargeID())) {
            System.out.println("Phụ phí với ID " + surcharge.getSurchargeID() + " đã tồn tại!");
        boolean result = surchargeBO.addSurcharge(surcharge);
        if (result) {
            System.out.println("Thêm phụ phí thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi thêm phụ phí.");
        }
        }
        }

    // Cập nhật phụ phí
    public static void updateSurcharge() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập SurchargeID cần cập nhật: ");
        String surchargeID = scanner.nextLine();
        System.out.print("Nhập Surcharge Name mới: ");
        String surchargeName = scanner.nextLine();
        System.out.print("Nhập Price mới: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Đọc ký tự dòng mới

        Surcharge surcharge = new Surcharge(surchargeID, surchargeName, price);
        boolean result = surchargeBO.updateSurcharge(surcharge);
        if (result) {
            System.out.println("Cập nhật phụ phí thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi cập nhật phụ phí.");
        }
    }

    // Xóa phụ phí
    public static void deleteSurcharge() {
        System.out.println("--Đây là chức năng của người quản lý--");
        System.out.print("Nhập SurchargeID cần xóa: ");
        String surchargeID = scanner.nextLine();

        boolean result = surchargeBO.deleteSurcharge(surchargeID);
        if (result) {
            System.out.println("Xóa phụ phí thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi xóa phụ phí.");
        }
    }
}