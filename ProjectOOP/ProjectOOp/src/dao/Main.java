/*package dao;

import bean.Surcharge;
import bo.SurchargeBO;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        // Khởi tạo đối tượng SurchargeBO
        SurchargeBO surchargeBO = new SurchargeBO();

        // 1. Kiểm tra kết nối bằng cách lấy danh sách phụ phí
        System.out.println("=== KIỂM TRA KẾT NỐI VÀ LẤY DANH SÁCH PHỤ PHÍ ===");
        try {
            ArrayList<Surcharge> surchargeList = surchargeBO.getAllSurcharges();
            if (surchargeList != null) {
                for (Surcharge surcharge : surchargeList) {
                    System.out.println(surcharge);
                }
            } else {
                System.out.println("Không thể lấy danh sách phụ phí.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách phụ phí:");
            e.printStackTrace();
        }

        // 2. Thêm phụ phí mới
        System.out.println("\n=== THÊM PHỤ PHÍ MỚI ===");
        try {
            Surcharge newSurcharge = new Surcharge("SU000", "Hang de chay", 0.15);
            boolean isAdded = surchargeBO.addSurcharge(newSurcharge);
            if (isAdded) {
                System.out.println("Thêm phụ phí mới thành công: " + newSurcharge);
            } else {
                System.out.println("Thêm phụ phí mới thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm phụ phí mới:");
            e.printStackTrace();
        }

        // 3. Sửa thông tin phụ phí
        System.out.println("\n=== CẬP NHẬT THÔNG TIN PHỤ PHÍ ===");
        try {
            Surcharge updateSurcharge = new Surcharge("SU999", "Hàng dễ cháy (cập nhật)", 0.18);
            boolean isUpdated = surchargeBO.updateSurcharge(updateSurcharge);
            if (isUpdated) {
                System.out.println("Cập nhật phụ phí thành công: " + updateSurcharge);
            } else {
                System.out.println("Cập nhật phụ phí thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật phụ phí:");
            e.printStackTrace();
        }

        // 4. Xóa phụ phí
        System.out.println("\n=== XÓA PHỤ PHÍ ===");
        try {
            boolean isDeleted = surchargeBO.deleteSurcharge("SU000");
            if (isDeleted) {
                System.out.println("Xóa phụ phí thành công: SU000");
            } else {
                System.out.println("Xóa phụ phí thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa phụ phí:");
            e.printStackTrace();
        }

        // 5. Kiểm tra lại danh sách sau các thao tác
        System.out.println("\n=== DANH SÁCH PHỤ PHÍ SAU KIỂM TRA ===");
        try {
            ArrayList<Surcharge> surchargeList = surchargeBO.getAllSurcharges();
            if (surchargeList != null) {
                for (Surcharge surcharge : surchargeList) {
                    System.out.println(surcharge);
                }
            } else {
                System.out.println("Không thể lấy danh sách phụ phí.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách phụ phí:");
            e.printStackTrace();
        }
    }
}*/
/*package dao;

import bean.Shipper;
import bo.ShipperBO;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ShipperBO shipperBO = new ShipperBO();

        // 1. Lấy danh sách shipper
        try {
            ArrayList<Shipper> shippers = shipperBO.getAllShippers();
            if (shippers != null) {
                for (Shipper shipper : shippers) {
                    System.out.println(shipper);
                }
            } else {
                System.out.println("Không tìm thấy shipper.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách shipper:");
            e.printStackTrace();
        }

        // 2. Thêm shipper mới
        try {
            Shipper newShipper = new Shipper("S1235", "Nguyen", "Van", "An", "Male", new Date(), "0901234567", "Ha Noi");
            boolean added = shipperBO.addShipper(newShipper);
            if (added) {
                System.out.println("Thêm shipper mới thành công: " + newShipper);
            } else {
                System.out.println("Thêm shipper thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm shipper:");
            e.printStackTrace();
        }

        // 3. Cập nhật thông tin shipper
        try {
            Shipper updatedShipper = new Shipper("S1235", "Nguyen", "Van", "An", "Male", new Date(), "0987654321", "Ha Noi");
            boolean updated = shipperBO.updateShipper(updatedShipper);
            if (updated) {
                System.out.println("Cập nhật shipper thành công: " + updatedShipper);
            } else {
                System.out.println("Cập nhật shipper thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật shipper:");
            e.printStackTrace();
        }

        // 4. Xóa shipper
        try {
            boolean deleted = shipperBO.deleteShipper("S1234");
            if (deleted) {
                System.out.println("Xóa shipper thành công.");
            } else {
                System.out.println("Xóa shipper thất bại.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa shipper:");
            e.printStackTrace();
        }
    }
}

/*
package model;

import model.bean.Product;
import model.bo.ProductBO;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        ProductBO productBO = new ProductBO();

        // 2. Thêm đơn hàng mới
        Product newProduct = new Product("P100", "John Doe", "Hanoi", "Cau Giay", "Ward 1", "123 Main St",
                "Alice", "0987654321", "Hanoi", "Hoan Kiem", "Ward 2", "456 Another St", "S101");
        productBO.addProduct(newProduct);
        // 1. Lấy danh sách tất cả các đơn hàng
        ArrayList<Product> products = productBO.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
*/

/*
package model;

import model.bean.OrderCreate;
import model.bo.OrderCreateBO;

import java.util.ArrayList;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        OrderCreateBO orderCreateBO = new OrderCreateBO();

        // 2. Thêm đơn hàng mới
        OrderCreate newOrder = new OrderCreate("P001", "U001", "U001", new Date());
        orderCreateBO.addOrder(newOrder);

        // 3. Cập nhật đơn hàng
        newOrder.setReceiverID("U003");
        orderCreateBO.updateOrder(newOrder);

        // 4. Xóa đơn hàng
        orderCreateBO.deleteOrder("P001");

        // 1. Lấy danh sách tất cả đơn hàng
        ArrayList<OrderCreate> orders = orderCreateBO.getAllOrders();
        for (OrderCreate order : orders) {
            System.out.println(order);
        }
    }
}
 */

/*
package model;

import model.bean.ImportExport;
import model.bo.ImportExportBO;

import java.util.ArrayList;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        ImportExportBO importExportBO = new ImportExportBO();

        // 2. Thêm bản ghi nhập kho mới
        ImportExport newImport = new ImportExport("P001", "K001", new Date(), null);
        importExportBO.addImport(newImport);

        // 3. Cập nhật thông tin xuất kho
        newImport.setOutboundDate(new Date()); // Cập nhật ngày xuất kho
        importExportBO.updateExport(newImport);

        // 1. Lấy danh sách tất cả bản ghi nhập/xuất kho
        ArrayList<ImportExport> importExports = importExportBO.getAllImportExports();
        for (ImportExport importExport : importExports) {
            System.out.println(importExport);
        }
    }
}
 */
/*
package model;

import model.bean.Statusofproduct;
import model.bo.StatusofproductBO;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        StatusofproductBO statusofproductBO = new StatusofproductBO();

        // 1. Lấy danh sách tất cả trạng thái đơn hàng
        ArrayList<Statusofproduct> statusList = statusofproductBO.getAllStatusofproduct();
        for (Statusofproduct status : statusList) {
            System.out.println(status);
        }

        // 2. Thêm trạng thái đơn hàng mới
        Statusofproduct newStatus = new Statusofproduct("P001", "K001", "Dang trong kho");
        boolean success = statusofproductBO.addStatus(newStatus);
        if (success) {
            System.out.println("Đã thêm trạng thái đơn hàng thành công!");
        } else {
            System.out.println("Thêm trạng thái đơn hàng thất bại.");
        }

        // 3. Cập nhật trạng thái đơn hàng
        newStatus.setOrderStatus("Da roi kho");
        success = statusofproductBO.updateStatus(newStatus);
        if (success) {
            System.out.println("Đã cập nhật trạng thái đơn hàng thành công!");
        } else {
            System.out.println("Cập nhật trạng thái đơn hàng thất bại.");
        }

        // 4. Xóa trạng thái đơn hàng
        success = statusofproductBO.deleteStatus("P001");
        if (success) {
            System.out.println("Đã xóa trạng thái đơn hàng thành công!");
        } else {
            System.out.println("Xóa trạng thái đơn hàng thất bại.");
        }
    }
}
 */
/*
package model;

import model.bean.Send;
import model.bo.SendBO;

import java.util.ArrayList;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        SendBO sendBO = new SendBO();

        // 1. Lấy danh sách tất cả giao hàng
        ArrayList<Send> sends = sendBO.getAllSends();
        for (Send send : sends) {
            System.out.println(send);
        }

        // 2. Thêm giao hàng mới
        Send newSend = new Send("SP001", "P001", new Date(), new Date(), null, "Dang giao");
        boolean success = sendBO.addSend(newSend);
        if (success) {
            System.out.println("Đã thêm giao hàng mới!");
        } else {
            System.out.println("Thêm giao hàng thất bại.");
        }

        // 3. Cập nhật trạng thái giao hàng
        newSend.setSendStatus("Da giao");
        newSend.setActualDate(new Date());  // Cập nhật ngày giao thực tế
        success = sendBO.updateSend(newSend);
        if (success) {
            System.out.println("Cập nhật trạng thái giao hàng thành công!");
        } else {
            System.out.println("Cập nhật trạng thái giao hàng thất bại.");
        }

        // 4. Xóa giao hàng
        success = sendBO.deleteSend("SP001", "P001");
        if (success) {
            System.out.println("Đã xóa giao hàng!");
        } else {
            System.out.println("Xóa giao hàng thất bại.");
        }
    }
}
 */

/*
package model;

import model.bean.OrderDetails;
import model.bo.OrderDetailsBO;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        OrderDetailsBO orderDetailsBO = new OrderDetailsBO();

        // Thêm OrderDetails
        OrderDetails newOrderDetails = new OrderDetails("I001", "P001", "Item1", "SU001", 10.5, 500.0);
        orderDetailsBO.addOrderDetails(newOrderDetails);

        // Cập nhật OrderDetails
        newOrderDetails.setItemPrice(550.0);  // Cập nhật giá
        orderDetailsBO.updateOrderDetails(newOrderDetails);

        // Xóa OrderDetails
        orderDetailsBO.deleteOrderDetails("I001");

        // Lấy danh sách OrderDetails
        ArrayList<OrderDetails> list = orderDetailsBO.getListOrderDetails();
        for (OrderDetails order : list) {
            System.out.println(order);
        }
    }
}
*/
/*
package model;

import model.bean.Warehouse;
import model.bo.WarehouseBO;

public class main {
    public static void main(String[] args) {
        WarehouseBO warehouseBO = new WarehouseBO();

        // Cập nhật thông tin Warehouse
        Warehouse warehouse = new Warehouse("W001", "Kho Cũ", "Hà Nội", "Hoàn Kiếm", "Cửa Đông", "12 Đường X");
        warehouse.setWareName("Kho Mới");  // Thay đổi tên kho
        warehouse.setCity("Hà Nội Mới");  // Thay đổi thành phố
        warehouse.setDistrict("Hoàn Kiếm Mới");
        warehouse.setWard("Cửa Đông Mới");
        warehouse.setAddress("123 Đường ABC");
        warehouseBO.updateWarehouse(warehouse);

        // Kiểm tra bảng WarehouseLog để xem thông tin thay đổi
        warehouseBO.checkWarehouseLogs();
    }
}
*/