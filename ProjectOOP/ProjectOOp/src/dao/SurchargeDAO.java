package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bean.Surcharge;

public class SurchargeDAO extends DBconnect {
	
	
	public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Lấy danh sách tất cả các phụ phí
	public ArrayList<Surcharge> getAllSurcharges() throws SQLException {
        // Đảm bảo kết nối cơ sở dữ liệu
        if (connection == null || connection.isClosed()) {
            connect(); // Gọi phương thức connect() nếu chưa kết nối
        }

        String sql = "SELECT SurchargeID, SurchargeName, Price FROM Surcharge";
        ArrayList<Surcharge> surcharges = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Surcharge surcharge = new Surcharge();
                surcharge.setSurchargeID(rs.getString("SurchargeID"));
                surcharge.setSurchargeName(rs.getString("SurchargeName"));
                surcharge.setPrice(rs.getDouble("Price"));
                surcharges.add(surcharge);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách phụ phí: " + e.getMessage());
            throw e;
        }

        return surcharges;
    }

    // Kiểm tra phụ phí đã tồn tại
    public boolean exists(String surchargeID) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        String sql = "SELECT COUNT(*) FROM Surcharge WHERE SurchargeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surchargeID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của phụ phí: " + e.getMessage());
            throw e;
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }
    }

    // Lấy thông tin phụ phí theo ID
    public Surcharge getSurchargeByID(String surchargeID) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        String sql = "SELECT SurchargeID, SurchargeName, Price FROM Surcharge WHERE SurchargeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surchargeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Surcharge surcharge = new Surcharge();
                    surcharge.setSurchargeID(rs.getString("SurchargeID"));
                    surcharge.setSurchargeName(rs.getString("SurchargeName"));
                    surcharge.setPrice(rs.getDouble("Price"));
                    return surcharge;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin phụ phí: " + e.getMessage());
            throw e;
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }

        return null; // Trả về null nếu không tìm thấy
    }

    // Thêm phụ phí mới
    public void addSurcharge(Surcharge surcharge) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        String sql = "INSERT INTO Surcharge (SurchargeID, SurchargeName, Price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surcharge.getSurchargeID());
            ps.setString(2, surcharge.getSurchargeName());
            ps.setDouble(3, surcharge.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phụ phí: " + e.getMessage());
            throw e;
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }
    }

    // Cập nhật phụ phí
    public void updateSurcharge(Surcharge surcharge) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        String sql = "UPDATE Surcharge SET SurchargeName = ?, Price = ? WHERE SurchargeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surcharge.getSurchargeName());
            ps.setDouble(2, surcharge.getPrice());
            ps.setString(3, surcharge.getSurchargeID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật phụ phí: " + e.getMessage());
            throw e;
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }
    }

    // Xóa phụ phí
    public void deleteSurcharge(String surchargeID) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        String sql = "DELETE FROM Surcharge WHERE SurchargeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, surchargeID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa phụ phí: " + e.getMessage());
            throw e;
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }
    }

    // Tìm kiếm phụ phí theo ID hoặc tên
    public List<Surcharge> searchSurcharge(String searchQuery) throws SQLException {
        connect(); // Đảm bảo kết nối cơ sở dữ liệu

        List<Surcharge> surcharges = new ArrayList<>();
        String query = "";

        if (searchQuery.matches("\\d+")) {
            query = "SELECT * FROM Surcharge WHERE SurchargeID = ?";
        } else {
            query = "SELECT * FROM Surcharge WHERE SurchargeName LIKE ?";
            searchQuery = "%" + searchQuery + "%";  // Thêm wildcard
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, searchQuery);  // Gán giá trị tìm kiếm vào câu lệnh
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Surcharge surcharge = new Surcharge();
                surcharge.setSurchargeID(rs.getString("SurchargeID"));
                surcharge.setSurchargeName(rs.getString("SurchargeName"));
                surcharge.setPrice(rs.getDouble("Price"));
                surcharges.add(surcharge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném lại exception để xử lý ngoài
        } finally {
            disconnect(); // Đảm bảo kết nối được đóng
        }

        return surcharges;
    }
}
