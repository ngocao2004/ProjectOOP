package controller;

import bean.OrderCreate;
import bean.OrderDetails;
import bo.OrderCreateBO;
import bo.OrderDetailsBO;
import bo.ProductBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UpdateOrderMenu {
    private static OrderCreateBO orderCreateBO = new OrderCreateBO();
    private static OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private static ProductBO productBO = new ProductBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== CẬP NHẬT ĐƠN HÀNG ===");
            System.out.println("1. Cập nhật đơn hàng");
            System.out.println("2. Cập nhật chi tiết đơn hàng");
            System.out.println("3. Cập nhật sản phẩm cho đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    updateOrder();
                    break;
                case 2:

                    break;
                case 3:
                    updateOrderDetails();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    // Cập nhật đơn hàng
    private static void updateOrder() {
        System.out.print("Nhập OrderID của đơn hàng muốn cập nhật: ");
        String orderID = scanner.nextLine();

        // Lấy thông tin của đơn hàng cũ
        ArrayList<OrderCreate> orderList = orderCreateBO.getAllOrders();
        OrderCreate orderToUpdate = null;
        for (OrderCreate order : orderList) {
            if (order.getOrderID().equals(orderID)) {
                orderToUpdate = order;
                break;
            }
        }

        if (orderToUpdate != null) {
            System.out.println("Cập nhật đơn hàng" + orderToUpdate);

            System.out.print("Nhập GiverID: ");
            String giverID = scanner.nextLine();
            System.out.print("Nhập ReceiverID: ");
            String receiverID = scanner.nextLine();
            System.out.print("Nhập OrderDate: ");
            String orderDateString = scanner.nextLine();
            // Sử dụng SimpleDateFormat để chuyển chuỗi thành java.util.Date
            Date orderDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                orderDate = dateFormat.parse(orderDateString);  // Chuyển chuỗi thành java.util.Date
            } catch (ParseException e) {
                System.out.println("Lỗi: Định dạng ngày không hợp lệ. Vui lòng nhập lại theo định dạng yyyy-MM-dd.");
                return;  // Kết thúc nếu có lỗi
            }
            // Cập nhật thông tin đơn hàng
            orderToUpdate.setGiverID(giverID);
            orderToUpdate.setReceiverID(receiverID);
            orderToUpdate.setOrderDate(orderDate);

            boolean isUpdated = orderCreateBO.updateOrder(orderToUpdate);
            if (isUpdated) {
                System.out.println("Đơn hàng đã được cập nhật thành công.");
            } else {
                System.out.println("Lỗi khi cập nhật đơn hàng.");
            }
        } else {
            System.out.println("Đơn hàng không tồn tại.");
        }
    }

    // Cập nhật sản phẩm đơn hàng
    private static void updateOrderDetails() {
        System.out.print("Nhập ItemID cần cập nhật: ");
        String itemID = scanner.nextLine();

        System.out.println("Nhập thông tin mới");
        System.out.print("ItemName: ");
        String itemName = scanner.nextLine();

        System.out.print("SurchargeID: ");
        String surchargeID = scanner.nextLine();

        System.out.print("Weight: ");
        double weight = Double.parseDouble(scanner.nextLine());

        System.out.print("ItemPrice: ");
        double itemPrice = Double.parseDouble(scanner.nextLine());

        OrderDetails updatedDetails = new OrderDetails();
        updatedDetails.setItemID(itemID);
        updatedDetails.setItemName(itemName);
        updatedDetails.setSurchargeID(surchargeID);
        updatedDetails.setWeight(weight);
        updatedDetails.setItemPrice(itemPrice);

        orderDetailsBO.updateOrderDetails(updatedDetails);
        System.out.println("Cập nhật thông sản phẩm thành công!");
    }
}