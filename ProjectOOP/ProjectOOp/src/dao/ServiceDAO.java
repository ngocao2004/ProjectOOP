package dao;

import java.sql.*;
import java.util.ArrayList;

import bean.Service;

public class ServiceDAO extends DBconnect {

    // Lấy danh sách tất cả các dịch vụ
    public ArrayList<Service> getAllServices() throws SQLException {
        connect();  // Kết nối cơ sở dữ liệu

        String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service service = new Service();
            service.setServiceID(rs.getString("ServiceID"));
            service.setServiceName(rs.getString("ServiceName"));
            service.setPrice(rs.getDouble("Price"));
            service.setMaxDistance(rs.getString("MaxDistance"));
            services.add(service);
        }
        return services;  // Trả về danh sách dịch vụ
    }

    // Thêm dịch vụ mới
    public void addService(Service service) throws SQLException {
        connect();

        String sql = "INSERT INTO Service (ServiceID, ServiceName, Price, MaxDistance) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, service.getServiceID());
        stmt.setString(2, service.getServiceName());
        stmt.setDouble(3, service.getPrice());
        stmt.setString(4, service.getMaxDistance());

        stmt.executeUpdate();  // Thực thi câu lệnh thêm dịch vụ
    }

    // Cập nhật dịch vụ
    public void updateService(Service service) throws SQLException {
        connect();

        String sql = "UPDATE Service SET ServiceName = ?, Price = ?, MaxDistance = ? WHERE ServiceID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, service.getServiceName());
        stmt.setDouble(2, service.getPrice());
        stmt.setString(3, service.getMaxDistance());
        stmt.setString(4, service.getServiceID());

        stmt.executeUpdate();  // Thực thi câu lệnh cập nhật dịch vụ
    }

    // Xóa dịch vụ
    public void deleteService(String serviceID) throws SQLException {
        connect();

        String sql = "DELETE FROM Service WHERE ServiceID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, serviceID);

        stmt.executeUpdate();  // Thực thi câu lệnh xóa dịch vụ
    }
    
    
 
 // Sắp xếp dịch vụ theo giá (tăng dần hoặc giảm dần)
 public ArrayList<Service> getServicesSortedByPrice(boolean ascending) throws SQLException {
     connect();

     // Tùy thuộc vào tham số ascending để chọn thứ tự sắp xếp
     String order = ascending ? "ASC" : "DESC";
     String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service ORDER BY Price " + order;

     Statement stmt = connection.createStatement();
     ResultSet rs = stmt.executeQuery(sql);

     ArrayList<Service> services = new ArrayList<>();
     while (rs.next()) {
         Service service = new Service();
         service.setServiceID(rs.getString("ServiceID"));
         service.setServiceName(rs.getString("ServiceName"));
         service.setPrice(rs.getDouble("Price"));
         service.setMaxDistance(rs.getString("MaxDistance"));
         services.add(service);
     }
     return services;  // Trả về danh sách dịch vụ đã sắp xếp
 }

 // Sắp xếp dịch vụ theo ID (tăng dần hoặc giảm dần)
 public ArrayList<Service> getServicesSortedByID(boolean ascending) throws SQLException {
     connect();

     // Tùy thuộc vào tham số ascending để chọn thứ tự sắp xếp
     String order = ascending ? "ASC" : "DESC";
     String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service ORDER BY ServiceID " + order;

     Statement stmt = connection.createStatement();
     ResultSet rs = stmt.executeQuery(sql);

     ArrayList<Service> services = new ArrayList<>();
     while (rs.next()) {
         Service service = new Service();
         service.setServiceID(rs.getString("ServiceID"));
         service.setServiceName(rs.getString("ServiceName"));
         service.setPrice(rs.getDouble("Price"));
         service.setMaxDistance(rs.getString("MaxDistance"));
         services.add(service);
     }
     return services;  // Trả về danh sách dịch vụ đã sắp xếp
 }
 
 
 
//Tìm kiếm dịch vụ theo ID
public ArrayList<Service> searchServiceByID(String serviceID) throws SQLException {
  connect();

  String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service WHERE ServiceID LIKE ?";
  PreparedStatement stmt = connection.prepareStatement(sql);
  stmt.setString(1, "%" + serviceID + "%");
  ResultSet rs = stmt.executeQuery();

  ArrayList<Service> services = new ArrayList<>();
  while (rs.next()) {
      Service service = new Service();
      service.setServiceID(rs.getString("ServiceID"));
      service.setServiceName(rs.getString("ServiceName"));
      service.setPrice(rs.getDouble("Price"));
      service.setMaxDistance(rs.getString("MaxDistance"));
      services.add(service);
  }
  return services;
}

//Tìm kiếm dịch vụ theo tên
public ArrayList<Service> searchServiceByName(String serviceName) throws SQLException {
  connect();

  String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service WHERE ServiceName LIKE ?";
  PreparedStatement stmt = connection.prepareStatement(sql);
  stmt.setString(1, "%" + serviceName + "%");
  ResultSet rs = stmt.executeQuery();

  ArrayList<Service> services = new ArrayList<>();
  while (rs.next()) {
      Service service = new Service();
      service.setServiceID(rs.getString("ServiceID"));
      service.setServiceName(rs.getString("ServiceName"));
      service.setPrice(rs.getDouble("Price"));
      service.setMaxDistance(rs.getString("MaxDistance"));
      services.add(service);
  }
  return services;
}

public Service getServiceByID(String serviceID) throws SQLException {
    connect();  // Ensure the database connection is established
    
    String sql = "SELECT ServiceID, ServiceName, Price, MaxDistance FROM Service WHERE ServiceID = ?";
    PreparedStatement stmt = connection.prepareStatement(sql);
    stmt.setString(1, serviceID);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        Service service = new Service();
        service.setServiceID(rs.getString("ServiceID"));
        service.setServiceName(rs.getString("ServiceName"));
        service.setPrice(rs.getDouble("Price"));
        service.setMaxDistance(rs.getString("MaxDistance"));
        return service;  // Return the service object if found
    }
    return null;  // Return null if no service is found with that ID
}






}

