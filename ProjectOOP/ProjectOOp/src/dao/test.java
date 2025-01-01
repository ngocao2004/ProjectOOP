package dao;
import java.util.List;

import bo.ShipperBO;

public class test {

	    public static void main(String[] args) {
	        ShipperBO shipperBO = new ShipperBO();
	        
	        // Lấy tất cả các EmployeeID từ cơ sở dữ liệu
	        List<String> employeeIDs = shipperBO.getAllEmployeeIDs();
	        
	        // Hiển thị tất cả các EmployeeID
	        if (employeeIDs.isEmpty()) {
	            System.out.println("Không có employeeID nào trong cơ sở dữ liệu.");
	        } else {
	            for (String id : employeeIDs) {
	                System.out.println("EmployeeID: " + id);
	            }
	        }
	    }
	


}
