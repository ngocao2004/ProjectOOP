package bean;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Warehouse {
    private String WarehouseID;  // WarehouseID (CHAR(4))
    private String WareName;     // WareName (VARCHAR(30))
    private String City;         // City (VARCHAR(40))
    private String District;     // District (VARCHAR(30))
    private String Ward;         // Ward (VARCHAR(30))
    private String Address;      // Address (VARCHAR(30))

    // Constructor không tham số
    public Warehouse() {
        super();
    }

    // Constructor đầy đủ tham số
    public Warehouse(String warehouseID, String wareName, String city, String district, String ward, String address) {
        this.WarehouseID = warehouseID;
        this.WareName = wareName;
        this.City = city;
        this.District = district;
        this.Ward = ward;
        this.Address = address;
    }

    // Getter và Setter cho các thuộc tính
    public String getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        WarehouseID = warehouseID;
    }

    public String getWareName() {
        return WareName;
    }

    public void setWareName(String wareName) {
        WareName = wareName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    
 // Các phương thức trả về StringProperty cho JavaFX TableView
    public StringProperty warehouseIDProperty() {
        return new SimpleStringProperty(WarehouseID);
    }

    public StringProperty wareNameProperty() {
        return new SimpleStringProperty(WareName);
    }

    public StringProperty cityProperty() {
        return new SimpleStringProperty(City);
    }

    public StringProperty districtProperty() {
        return new SimpleStringProperty(District);
    }

    public StringProperty wardProperty() {
        return new SimpleStringProperty(Ward);
    }

    public StringProperty addressProperty() {
        return new SimpleStringProperty(Address);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "WarehouseID='" + WarehouseID + '\'' +
                ", WareName='" + WareName + '\'' +
                ", City='" + City + '\'' +
                ", District='" + District + '\'' +
                ", Ward='" + Ward + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}