package controller;

import bean.Send;
import bo.SendBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SendMenu {
    private static SendBO sendBO = new SendBO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("==== QUẢN LÝ GIAO HÀNG ====");
            System.out.println("1. Hiển thị danh sách giao hàng");
            System.out.println("2. Thêm giao hàng mới");
            System.out.println("3. Cập nhật giao hàng");
            System.out.println("4. Xóa giao hàng");
            System.out.println("5. Xem thông tin giao hàng theo OderID");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayAllSends();
                    break;
                case 2:
                    addSend();
                    break;
                case 3:
                    updateSend();
                    break;
                case 4:
                    deleteSend();
                    break;
                case 5:
                    displaySendByOrderID();
                    break;
                case 6:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
            }
        }
    }

    private static void displayAllSends() {
        ArrayList<Send> sends = sendBO.getAllSends();
        if (sends != null && !sends.isEmpty()) {
            System.out.println("Danh sách giao hàng:");
            for (Send send : sends) {
                System.out.println(send);
            }
        } else {
            System.out.println("Không có bản ghi nào.");
        }
    }

    private static void addSend() {
        try {
            System.out.print("Nhập mã nhân viên: ");
            String employeeID = scanner.nextLine();

            System.out.print("Nhập mã đơn hàng: ");
            String orderID = scanner.nextLine();

            System.out.print("Nhập ngày nhận (dd/MM/yyyy): ");
            Date receiptDate = parseDate(scanner.nextLine());

            System.out.print("Nhập ngày dự kiến giao (dd/MM/yyyy): ");
            Date estimatedDate = parseDate(scanner.nextLine());

            System.out.print("Nhập ngày giao thực tế (dd/MM/yyyy) (có thể để trống): ");
            String actualDateInput = scanner.nextLine();
            Date actualDate = actualDateInput.isEmpty() ? null : parseDate(actualDateInput);

            System.out.print("Nhập trạng thái giao hàng: ");
            String sendStatus = scanner.nextLine();

            Send send = new Send(employeeID, orderID, receiptDate, estimatedDate, actualDate, sendStatus);
            if (sendBO.addSend(send)) {
                System.out.println("Thêm giao hàng thành công.");
            } else {
                System.out.println("Thêm giao hàng thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Dữ liệu không hợp lệ! Vui lòng thử lại.");
        }
    }

    private static void updateSend() {
        try {
            System.out.print("Nhập mã nhân viên: ");
            String employeeID = scanner.nextLine();

            System.out.print("Nhập mã đơn hàng: ");
            String orderID = scanner.nextLine();

            System.out.print("Nhập ngày nhận (dd/MM/yyyy): ");
            Date receiptDate = parseDate(scanner.nextLine());

            System.out.print("Nhập ngày dự kiến giao (dd/MM/yyyy): ");
            Date estimatedDate = parseDate(scanner.nextLine());

            System.out.print("Nhập ngày giao thực tế (dd/MM/yyyy) (có thể để trống): ");
            String actualDateInput = scanner.nextLine();
            Date actualDate = actualDateInput.isEmpty() ? null : parseDate(actualDateInput);

            System.out.print("Nhập trạng thái giao hàng: ");
            String sendStatus = scanner.nextLine();

            Send send = new Send(employeeID, orderID, receiptDate, estimatedDate, actualDate, sendStatus);
            if (sendBO.updateSend(send)) {
                System.out.println("Cập nhật giao hàng thành công.");
            } else {
                System.out.println("Cập nhật giao hàng thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Dữ liệu không hợp lệ! Vui lòng thử lại.");
        }
    }

    private static void deleteSend() {
        System.out.print("Nhập mã nhân viên: ");
        String employeeID = scanner.nextLine();

        System.out.print("Nhập mã đơn hàng: ");
        String orderID = scanner.nextLine();

        if (sendBO.deleteSend(employeeID, orderID)) {
            System.out.println("Xóa giao hàng thành công.");
        } else {
            System.out.println("Xóa giao hàng thất bại.");
        }
    }

    private static void displaySendByOrderID() {
        System.out.print("Nhập mã đơn hàng (OrderID): ");
        String orderID = scanner.nextLine();

        ArrayList<Send> sends = sendBO.getSendByOrderID(orderID);
        if (sends != null && !sends.isEmpty()) {
            System.out.println("Thông tin giao hàng:");
            for (Send send : sends) {
                System.out.println(send);
            }
        } else {
            System.out.println("Không tìm thấy thông tin giao hàng cho OrderID: " + orderID);
        }
    }

    private static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(dateStr);
    }
}