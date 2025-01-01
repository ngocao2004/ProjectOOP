package bean;

public class Statusofproduct {
    private String orderID;         // OrderID (CHAR(5))
    private String currentWarehouseID; // CurrentWarehouseID (CHAR(5))
    private String orderStatus;     // OrderStatus (VARCHAR(30))

    // Constructor không tham số
    public Statusofproduct() {
        super();
    }

    // Constructor đầy đủ tham số
    public Statusofproduct(String orderID, String currentWarehouseID, String orderStatus) {
        this.orderID = orderID;
        this.currentWarehouseID = currentWarehouseID;
        this.orderStatus = orderStatus;
    }

    // Getter và Setter cho các thuộc tính
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCurrentWarehouseID() {
        return currentWarehouseID;
    }

    public void setCurrentWarehouseID(String currentWarehouseID) {
        this.currentWarehouseID = currentWarehouseID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Statusofproduct{" +
                "orderID='" + orderID + '\'' +
                ", currentWarehouseID='" + currentWarehouseID + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}