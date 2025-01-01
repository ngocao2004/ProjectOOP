package bean;

import java.util.Date;

public class Send {
    private String employeeID;     // EmployeeID (CHAR(5))
    private String orderID;        // OrderID (CHAR(5))
    private Date receiptDate;      // ReceiptDate (DATE)
    private Date estimatedDate;    // EstimatedDate (DATE)
    private Date actualDate;       // ActualDate (DATE)
    private String sendStatus;     // SendStatus (VARCHAR(30))

    // Constructor không tham số
    public Send() {
        super();
    }

    // Constructor đầy đủ tham số
    public Send(String employeeID, String orderID, Date receiptDate, Date estimatedDate, Date actualDate, String sendStatus) {
        this.employeeID = employeeID;
        this.orderID = orderID;
        this.receiptDate = receiptDate;
        this.estimatedDate = estimatedDate;
        this.actualDate = actualDate;
        this.sendStatus = sendStatus;
    }

    // Getter và Setter cho các thuộc tính
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Date getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(Date estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Override
    public String toString() {
        return "Send{" +
                "employeeID='" + employeeID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", receiptDate=" + receiptDate +
                ", estimatedDate=" + estimatedDate +
                ", actualDate=" + actualDate +
                ", sendStatus='" + sendStatus + '\'' +
                '}';
    }
}