package controller;

import bean.OrderCreate;
import bean.OrderDetails;
import bo.OrderCreateBO;
import bo.OrderDetailsBO;
import bean.Product;
import bo.ProductBO;

import java.util.List;
import java.util.Scanner;

public class ViewOrderByID {

    private static OrderCreateBO orderCreateBO = new OrderCreateBO();
    private static OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private static ProductBO productBO = new ProductBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== XEM THÔNG TIN ĐƠN HÀNG THEO UserID ===");
            System.out.println("1. Xem thông tin đơn hàng theo UserID");
            System.out.println("2. Xem chi tiết đơn hàng");
            System.out.println("3. Xem thông tin sản phẩm trong đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewOrdersByUserID();
                    break;
                case 2:
                    viewProductsByUserID();
                    break;
                case 3:
                    viewOrderDetailsByOrderID();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    // Xem thông tin đơn hàng theo UserID
    private static void viewOrdersByUserID() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();

        List<OrderCreate> orders = orderCreateBO.getOrdersByUserID(userID);

        if (orders != null && !orders.isEmpty()) {
            System.out.println("\nDanh sách đơn hàng của UserID: " + userID);
            for (OrderCreate order : orders) {
                System.out.println(order);
            }
        } else {
            System.out.println("Không tìm thấy đơn hàng nào cho UserID: " + userID);
        }
    }

    // Xem sản phẩm trong đơn hàng
    private static void viewOrderDetailsByOrderID() {
        System.out.print("Nhập OrderID: ");
        String orderID = scanner.nextLine();

        List<OrderDetails> orderDetailsList = orderDetailsBO.getOrderDetailsByOrderID(orderID);

        if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
            System.out.println("\nChi tiết đơn hàng cho OrderID: " + orderID);
            for (OrderDetails details : orderDetailsList) {
                System.out.println(details);
            }
        } else {
            System.out.println("Không tìm thấy chi tiết đơn hàng nào cho OrderID: " + orderID);
        }
    }
    //Xem chi tiết đơn hàng
    public static void viewProductsByUserID() {
        System.out.print("Nhập UserID: ");
        String userID = scanner.nextLine();

        // Lấy danh sách sản phẩm theo UserID
        List<Product> productList = productBO.getProductsByUserID(userID);

        if (productList != null && !productList.isEmpty()) {
            System.out.println("\nDanh sách sản phẩm của UserID: " + userID);
            for (Product product : productList) {
                System.out.println(product);
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm nào cho UserID: " + userID);
        }
    }

}