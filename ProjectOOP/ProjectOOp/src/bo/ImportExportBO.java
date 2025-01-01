package bo;

import bean.ImportExport;

import dao.DBconnect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ImportExportDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ImportExportBO {
    private ImportExportDAO importExportDAO = new ImportExportDAO();

    // Lấy danh sách tất cả các bản ghi nhập/xuất kho
    public ArrayList<ImportExport> getAllImportExports() {
        try {
            return importExportDAO.getAllImportExports();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  
    public boolean addImport(ImportExport importExport) {
        try {
            importExportDAO.addImport(importExport);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin xuất kho
    public boolean updateExport(ImportExport importExport) {
        try {
            importExportDAO.updateExport(importExport);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bản ghi nhập xuất kho
    public boolean deleteImportExport(String orderID, String warehouseID) {
        try {
            importExportDAO.deleteImportExport(orderID, warehouseID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // Kiểm tra xem bản ghi đã tồn tại trong cơ sở dữ liệu chưa
    public boolean recordExists(String orderID, String warehouseID) {
        return importExportDAO.recordExists(orderID, warehouseID);
    }

    // Thêm bản ghi nhập kho
    public boolean addImportExport(String orderID, String warehouseID, Date inboundDate) {
        if (recordExists(orderID, warehouseID)) {
            System.out.println("Bản ghi đã tồn tại.");
            return false; // Nếu bản ghi đã tồn tại, không cho thêm
        }

        return importExportDAO.addImportExport(orderID, warehouseID, inboundDate);
    }
}