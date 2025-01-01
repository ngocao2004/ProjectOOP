package controller;

import bean.OrderCreate;
import bean.OrderDetails;
import bo.OrderCreateBO;
import bo.OrderDetailsBO;
import bean.Product;
import bo.ProductBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AddOrderMenu {
    private static OrderCreateBO orderCreateBO = new OrderCreateBO();
    private static OrderDetailsBO orderDetailsBO = new OrderDetailsBO();
    private static ProductBO productBO = new ProductBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== TẠO ĐƠN HÀNG ===");
            System.out.println("1. Tạo đơn hàng mới");
            System.out.println("2. Thêm chi tiết đơn hàng");
            System.out.println("3. Thêm sản phẩm cho đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    addOrderDetails();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }


    // Thêm đơn hàng mới
    private static void addOrder() {
        System.out.print("Nhập OrderID: ");
        String orderID = scanner.nextLine();
        System.out.print("Nhập GiverID: ");
        String giverID = scanner.nextLine();
        System.out.print("Nhập ReceiverID: ");
        String receiverID = scanner.nextLine();
        System.out.print("Nhập OrderDate (dd/MM/yyyy): ");
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

        // Tạo đối tượng OrderCreate và thêm vào cơ sở dữ liệu
        OrderCreate order = new OrderCreate(orderID, giverID, receiverID, orderDate);

        boolean isAdded = orderCreateBO.addOrder(order);
        if (isAdded) {
            System.out.println("Đơn hàng đã được thêm thành công.");
        } else {
            System.out.println("Lỗi khi thêm đơn hàng.");
        }
    }
    // Thêm chi tiết đơn hàng
    private static void addProduct() {
        System.out.print("Nhập OrderID: ");
        String orderID = scanner.nextLine();
        System.out.print("Nhập Payer: ");
        String payer = scanner.nextLine();
        System.out.print("Nhập PickupCity: ");
        String pickupCity = scanner.nextLine();
        System.out.print("Nhập PickupDistrict: ");
        String pickupDistrict = scanner.nextLine();
        System.out.print("Nhập PickupWard: ");
        String pickupWard = scanner.nextLine();
        System.out.print("Nhập PickupAddress: ");
        String pickupAddress = scanner.nextLine();
        System.out.print("Nhập RecipientName: ");
        String recipientName = scanner.nextLine();
        System.out.print("Nhập PhoneRecipient: ");
        String phoneRecipient = scanner.nextLine();
        System.out.print("Nhập DeliveryCity: ");
        String deliveryCity = scanner.nextLine();
        System.out.print("Nhập DeliveryDistrict: ");
        String deliveryDistrict = scanner.nextLine();
        System.out.print("Nhập DeliveryWard: ");
        String deliveryWard = scanner.nextLine();
        System.out.print("Nhập DeliveryAddress: ");
        String deliveryAddress = scanner.nextLine();
        System.out.print("Nhập ServiceID: ");
        String serviceID = scanner.nextLine();

        // Tạo đối tượng Product và thêm vào cơ sở dữ liệu
        Product product = new Product(orderID, payer, pickupCity, pickupDistrict, pickupWard, pickupAddress,
                recipientName, phoneRecipient, deliveryCity, deliveryDistrict, deliveryWard,
                deliveryAddress, serviceID);

        boolean isAdded = productBO.addProduct(product);
        if (isAdded) {
            System.out.println("Tạo đơn hàng thành công.");
        } else {
            System.out.println("Lỗi khi thêm đơn hàng.");
        }
    }
    //Them san pham trong don hang
    private static void addOrderDetails() {
        System.out.println("Nhập sản phẩm cho đơn hàng");

        System.out.print("ItemID: ");
        String itemID = scanner.nextLine();

        System.out.print("OrderID: ");
        String orderID = scanner.nextLine();

        System.out.print("ItemName: ");
        String itemName = scanner.nextLine();

        System.out.print("SurchargeID: ");
        String surchargeID = scanner.nextLine();

        System.out.print("Weight: ");
        double weight = Double.parseDouble(scanner.nextLine());

        System.out.print("ItemPrice: ");
        double itemPrice = Double.parseDouble(scanner.nextLine());

        OrderDetails orderDetails = new OrderDetails(itemID, orderID, itemName, surchargeID, weight, itemPrice);
        orderDetailsBO.addOrderDetails(orderDetails);
        System.out.println("Thêm thông tin chi tiết đơn hàng thành công!");
    }
}