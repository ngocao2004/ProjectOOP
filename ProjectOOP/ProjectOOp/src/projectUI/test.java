package projectUI;
import java.util.ArrayList;
import bo.WarehouseBO;

import bo.ProductBO;

public class test {
	public static void main(String[] args) {
	    WarehouseBO warehouseBO = new WarehouseBO();
	    ArrayList<String> warehouseIDs = warehouseBO.getAllWarehouseIDs();

	    if (warehouseIDs.isEmpty()) {
	        System.out.println("Không có WarehouseID nào trong bảng Warehouse.");
	    } else {
	        System.out.println("Danh sách WarehouseID:");
	        for (String id : warehouseIDs) {
	            System.out.println(id);
	        }
	    }
	}



}
