package controller;

import bean.OrderCreate;
import bean.OrderDetails;
import bo.OrderCreateBO;
import bo.OrderDetailsBO;
import bean.Product;
import bo.ProductBO;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOrder{

    private static OrderCreateBO orderCreateBO = new OrderCreateBO();
    private static OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private static ProductBO productBO = new ProductBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MENU QUẢN LÝ ĐƠN HÀNG ===");
            System.out.println("1. Xem thông tin đơn hàng theo UserID");
            System.out.println("2. Xem chi tiết đơn hàng");
            System.out.println("3. Xem thông tin sản phẩm trong đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showAllOrders();
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    displayOrderDetailsList();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }
        //1. Hiển thị tất cả đơn hàng
        private static void showAllOrders() {
            ArrayList<OrderCreate> orders = orderCreateBO.getAllOrders();
            if (orders != null && !orders.isEmpty()) {
                System.out.println("Danh sách tất cả đơn hàng:");
                for (OrderCreate order : orders) {
                    System.out.println(order);
                }
            } else {
                System.out.println("Không có đơn hàng nào trong cơ sở dữ liệu.");
            }
        }
    // Hiển thị tất cả đơn hàng
    private static void showAllProducts() {
        ArrayList<Product> products = productBO.getAllProducts();
        if (products != null && !products.isEmpty()) {
            System.out.println("Danh sách tất cả đơn hàng:");
            for (Product product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("Không có đơn hàng nào trong cơ sở dữ liệu.");
        }
    }

        // 3. Hiển thị danh sách chi tiết đơn hàng
        private static void displayOrderDetailsList() {
            ArrayList<OrderDetails> list = orderDetailsBO.getListOrderDetails();

            if (list != null && !list.isEmpty()) {
                System.out.println("\nDanh sách chi tiết đơn hàng:");
                for (OrderDetails orderDetails : list) {
                    System.out.println(orderDetails);
                }
            } else {
                System.out.println("Không có thông tin chi tiết đơn hàng nào trong danh sách.");
            }
        }

    }