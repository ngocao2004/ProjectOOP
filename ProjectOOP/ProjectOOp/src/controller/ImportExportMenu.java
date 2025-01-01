package controller;

import bean.ImportExport;
import bo.ImportExportBO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ImportExportMenu {
    private static ImportExportBO importExportBO = new ImportExportBO();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            System.out.println("==== QUẢN LÝ NHẬP XUẤT KHO ====");
            System.out.println("1. Hiển thị danh sách nhập/xuất kho");
            System.out.println("2. Thêm bản ghi nhập kho");
            System.out.println("3. Cập nhật thông tin xuất kho");
            System.out.println("4. Xóa bản ghi nhập/xuất kho");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayAllImportExports();
                    break;
                case 2:
                    addImport();
                    break;
                case 3:
                    updateExport();
                    break;
                case 4:
                    deleteImportExport();
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
            }
        }
    }

    private static void displayAllImportExports() {
        ArrayList<ImportExport> importExports = importExportBO.getAllImportExports();
        if (importExports != null && !importExports.isEmpty()) {
            System.out.println("Danh sách nhập/xuất kho:");
            for (ImportExport importExport : importExports) {
                System.out.println(importExport);
            }
        } else {
            System.out.println("Không có bản ghi nhập/xuất kho nào.");
        }
    }

    private static void addImport() {
        try {
            System.out.print("Nhập OrderID: ");
            String orderID = scanner.nextLine();
            System.out.print("Nhập WarehouseID: ");
            String warehouseID = scanner.nextLine();
            System.out.print("Nhập ngày nhập kho (yyyy-MM-dd): ");
            Date inboundDate = dateFormat.parse(scanner.nextLine());

            ImportExport importExport = new ImportExport();
            importExport.setOrderID(orderID);
            importExport.setWarehouseID(warehouseID);
            importExport.setInboundDate(inboundDate);

            if (importExportBO.addImport(importExport)) {
                System.out.println("Thêm bản ghi nhập kho thành công.");
            } else {
                System.out.println("Thêm bản ghi nhập kho thất bại.");
            }
        } catch (ParseException e) {
            System.out.println("Ngày nhập không hợp lệ. Vui lòng thử lại.");
        }
    }

    private static void updateExport() {
        try {
            System.out.print("Nhập OrderID: ");
            String orderID = scanner.nextLine();
            System.out.print("Nhập WarehouseID: ");
            String warehouseID = scanner.nextLine();
            System.out.print("Nhập ngày xuất kho (yyyy-MM-dd): ");
            Date outboundDate = dateFormat.parse(scanner.nextLine());

            ImportExport importExport = new ImportExport();
            importExport.setOrderID(orderID);
            importExport.setWarehouseID(warehouseID);
            importExport.setOutboundDate(outboundDate);

            if (importExportBO.updateExport(importExport)) {
                System.out.println("Cập nhật thông tin xuất kho thành công.");
            } else {
                System.out.println("Cập nhật thông tin xuất kho thất bại.");
            }
        } catch (ParseException e) {
            System.out.println("Ngày nhập không hợp lệ. Vui lòng thử lại.");
        }
    }

    private static void deleteImportExport() {
        System.out.print("Nhập OrderID: ");
        String orderID = scanner.nextLine();
        System.out.print("Nhập WarehouseID: ");
        String warehouseID = scanner.nextLine();

        if (importExportBO.deleteImportExport(orderID, warehouseID)) {
            System.out.println("Xóa bản ghi nhập/xuất kho thành công.");
        } else {
            System.out.println("Xóa bản ghi nhập/xuất kho thất bại.");
        }
    }
}