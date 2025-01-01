package bean;

import java.util.Date;

public class ImportExport {
    private String orderID;         // OrderID (CHAR(5))
    private String warehouseID;     // WarehouseID (CHAR(5))
    private Date inboundDate;       // InboundDate (DATE)
    private Date outboundDate;      // OutboundDate (DATE)

    // Constructor không tham số
    public ImportExport() {
        super();
    }
    
    

    // Constructor đầy đủ tham số
    public ImportExport(String orderID, String warehouseID, Date inboundDate, Date outboundDate) {
        this.orderID = orderID;
        this.warehouseID = warehouseID;
        this.inboundDate = inboundDate;
        this.outboundDate = outboundDate;
    }

    // Getter và Setter cho các thuộc tính
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    @Override
    public String toString() {
        return "ImportExport{" +
                "orderID='" + orderID + '\'' +
                ", warehouseID='" + warehouseID + '\'' +
                ", inboundDate=" + inboundDate +
                ", outboundDate=" + outboundDate +
                '}';
    }
}