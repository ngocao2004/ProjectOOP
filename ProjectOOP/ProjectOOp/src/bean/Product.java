package bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private StringProperty orderID;
    private StringProperty payer;
    private StringProperty pickupCity;
    private StringProperty pickupDistrict;
    private StringProperty pickupWard;
    private StringProperty pickupAddress;
    private StringProperty recipientName;
    private StringProperty phoneRecipient;
    private StringProperty deliveryCity;
    private StringProperty deliveryDistrict;
    private StringProperty deliveryWard;
    private StringProperty deliveryAddress;
    private StringProperty serviceID;

    // Constructor không tham số
    public Product() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "");
    }

    // Constructor đầy đủ tham số
    public Product(String orderID, String payer, String pickupCity, String pickupDistrict, String pickupWard,
                   String pickupAddress, String recipientName, String phoneRecipient, String deliveryCity,
                   String deliveryDistrict, String deliveryWard, String deliveryAddress, String serviceID) {
        this.orderID = new SimpleStringProperty(orderID);
        this.payer = new SimpleStringProperty(payer);
        this.pickupCity = new SimpleStringProperty(pickupCity);
        this.pickupDistrict = new SimpleStringProperty(pickupDistrict);
        this.pickupWard = new SimpleStringProperty(pickupWard);
        this.pickupAddress = new SimpleStringProperty(pickupAddress);
        this.recipientName = new SimpleStringProperty(recipientName);
        this.phoneRecipient = new SimpleStringProperty(phoneRecipient);
        this.deliveryCity = new SimpleStringProperty(deliveryCity);
        this.deliveryDistrict = new SimpleStringProperty(deliveryDistrict);
        this.deliveryWard = new SimpleStringProperty(deliveryWard);
        this.deliveryAddress = new SimpleStringProperty(deliveryAddress);
        this.serviceID = new SimpleStringProperty(serviceID);
    }

    // Getter và Setter với StringProperty
    public String getOrderID() {
        return orderID.get();
    }

    public void setOrderID(String orderID) {
        this.orderID.set(orderID);
    }

    public StringProperty orderIDProperty() {
        return orderID;
    }

    public String getPayer() {
        return payer.get();
    }

    public void setPayer(String payer) {
        this.payer.set(payer);
    }

    public StringProperty payerProperty() {
        return payer;
    }

    public String getPickupCity() {
        return pickupCity.get();
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity.set(pickupCity);
    }

    public StringProperty pickupCityProperty() {
        return pickupCity;
    }

    public String getPickupDistrict() {
        return pickupDistrict.get();
    }

    public void setPickupDistrict(String pickupDistrict) {
        this.pickupDistrict.set(pickupDistrict);
    }

    public StringProperty pickupDistrictProperty() {
        return pickupDistrict;
    }

    public String getPickupWard() {
        return pickupWard.get();
    }

    public void setPickupWard(String pickupWard) {
        this.pickupWard.set(pickupWard);
    }

    public StringProperty pickupWardProperty() {
        return pickupWard;
    }

    public String getPickupAddress() {
        return pickupAddress.get();
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress.set(pickupAddress);
    }

    public StringProperty pickupAddressProperty() {
        return pickupAddress;
    }

    public String getRecipientName() {
        return recipientName.get();
    }

    public void setRecipientName(String recipientName) {
        this.recipientName.set(recipientName);
    }

    public StringProperty recipientNameProperty() {
        return recipientName;
    }

    public String getPhoneRecipient() {
        return phoneRecipient.get();
    }

    public void setPhoneRecipient(String phoneRecipient) {
        this.phoneRecipient.set(phoneRecipient);
    }

    public StringProperty phoneRecipientProperty() {
        return phoneRecipient;
    }

    public String getDeliveryCity() {
        return deliveryCity.get();
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity.set(deliveryCity);
    }

    public StringProperty deliveryCityProperty() {
        return deliveryCity;
    }

    public String getDeliveryDistrict() {
        return deliveryDistrict.get();
    }

    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict.set(deliveryDistrict);
    }

    public StringProperty deliveryDistrictProperty() {
        return deliveryDistrict;
    }

    public String getDeliveryWard() {
        return deliveryWard.get();
    }

    public void setDeliveryWard(String deliveryWard) {
        this.deliveryWard.set(deliveryWard);
    }

    public StringProperty deliveryWardProperty() {
        return deliveryWard;
    }

    public String getDeliveryAddress() {
        return deliveryAddress.get();
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress.set(deliveryAddress);
    }

    public StringProperty deliveryAddressProperty() {
        return deliveryAddress;
    }

    public String getServiceID() {
        return serviceID.get();
    }

    public void setServiceID(String serviceID) {
        this.serviceID.set(serviceID);
    }

    public StringProperty serviceIDProperty() {
        return serviceID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "orderID=" + getOrderID() +
                ", payer=" + getPayer() +
                ", pickupCity=" + getPickupCity() +
                ", pickupDistrict=" + getPickupDistrict() +
                ", pickupWard=" + getPickupWard() +
                ", pickupAddress=" + getPickupAddress() +
                ", recipientName=" + getRecipientName() +
                ", phoneRecipient=" + getPhoneRecipient() +
                ", deliveryCity=" + getDeliveryCity() +
                ", deliveryDistrict=" + getDeliveryDistrict() +
                ", deliveryWard=" + getDeliveryWard() +
                ", deliveryAddress=" + getDeliveryAddress() +
                ", serviceID=" + getServiceID() +
                '}';
    }
}

