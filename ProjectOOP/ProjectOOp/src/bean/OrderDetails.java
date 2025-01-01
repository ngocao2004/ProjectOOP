package bean;

public class OrderDetails {
    private String itemID;
    private String orderID;
    private String itemName;
    private String surchargeID;
    private double weight;
    private double itemPrice;

    // Constructor không tham số
    public OrderDetails() {}

    // Constructor đầy đủ tham số
    public OrderDetails(String itemID, String orderID, String itemName, String surchargeID, double weight, double itemPrice) {
        this.itemID = itemID;
        this.orderID = orderID;
        this.itemName = itemName;
        this.surchargeID = surchargeID;
        this.weight = weight;
        this.itemPrice = itemPrice;
    }

    // Getter và Setter
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSurchargeID() {
        return surchargeID;
    }

    public void setSurchargeID(String surchargeID) {
        this.surchargeID = surchargeID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemID='" + itemID + '\'' +
                ", orderID='" + orderID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", surchargeID='" + surchargeID + '\'' +
                ", weight=" + weight +
                ", itemPrice=" + itemPrice +
                '}';
    }
}