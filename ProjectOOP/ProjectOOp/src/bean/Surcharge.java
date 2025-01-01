package bean;

public class Surcharge {
    private String surchargeID;   // SurchargeID (CHAR(5))
    private String surchargeName; // SurchargeName (VARCHAR(20))
    private double price;         // Price (DECIMAL(5,2))

    // Constructor không tham số
    public Surcharge() {
        super();
    }

    // Constructor đầy đủ tham số
    public Surcharge(String surchargeID, String surchargeName, double price) {
        this.surchargeID = surchargeID;
        this.surchargeName = surchargeName;
        this.price = price;
    }

    // Getter và Setter
    public String getSurchargeID() {
        return surchargeID;
    }

    public void setSurchargeID(String surchargeID) {
        this.surchargeID = surchargeID;
    }
    
  

    public String getSurchargeName() {
        return surchargeName;
    }

    public void setSurchargeName(String surchargeName) {
        this.surchargeName = surchargeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Surcharge{" +
                "surchargeID='" + surchargeID + '\'' +
                ", surchargeName='" + surchargeName + '\'' +
                ", price=" + price +
                '}';
    }
}