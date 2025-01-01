package bean;

import java.util.Date;

public class OrderCreate {
    private String orderID;        // OrderID (CHAR(5))
    private String giverID;        // GiverID (CHAR(5))
    private String receiverID;     // ReciverID (CHAR(5))
    private Date orderDate;        // OrderDate (DATE)

    // Constructor không tham số
    public OrderCreate() {
        super();
    }

    // Constructor đầy đủ tham số
    public OrderCreate(String orderID, String giverID, String receiverID, Date orderDate) {
        this.orderID = orderID;
        this.giverID = giverID;
        this.receiverID = receiverID;
        this.orderDate = orderDate;
    }

    // Getter và Setter
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getGiverID() {
        return giverID;
    }

    public void setGiverID(String giverID) {
        this.giverID = giverID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderCreate{" +
                "orderID='" + orderID + '\'' +
                ", giverID='" + giverID + '\'' +
                ", receiverID='" + receiverID + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}