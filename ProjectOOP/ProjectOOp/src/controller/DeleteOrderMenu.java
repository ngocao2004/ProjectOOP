package controller;

import bo.OrderCreateBO;
import bo.OrderDetailsBO;
import bo.ProductBO;

import java.util.Scanner;

public class DeleteOrderMenu {
    private static OrderCreateBO orderCreateBO = new OrderCreateBO();
    private static OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private static ProductBO productBO = new ProductBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== XÓA ĐƠN HÀNG ===");
            System.out.println("1. Xóa đơn hàng");
            System.out.println("2. Xóa sản phẩm trong đơn hàng");
            System.out.println("3. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    deleteOrder();
                    break;
                case 2:
                    deleteOrderDetails();
                    break;
                case 3:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    // Xóa đơn hàng
    private static void deleteOrder() {
        System.out.print("Nhập OrderID của đơn hàng muốn xóa: ");
        String orderID = scanner.nextLine();

        boolean isDeleted = orderCreateBO.deleteOrder(orderID);
        if (isDeleted) {
            System.out.println("Đơn hàng đã được xóa thành công.");
        } else {
            System.out.println("Lỗi khi xóa đơn hàng.");
        }
    }
    // Xóa thông tin sản phẩm trong đơn hàng
    private static void deleteOrderDetails() {
        System.out.print("Nhập ItemID cần xóa: ");
        String itemID = scanner.nextLine();

        orderDetailsBO.deleteOrderDetails(itemID);
        System.out.println("Xóa sản phẩm thành công!");
    }
}