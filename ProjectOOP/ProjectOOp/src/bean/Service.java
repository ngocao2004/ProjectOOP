package bean;

public class Service {
    private String serviceID;       // ServiceID (CHAR(5))
    private String serviceName;     // ServiceName (VARCHAR(20))
    private double price;           // Price (DECIMAL(12,3))
    private String maxDistance;     // MaxDistance (CHAR(6))

    // Constructor không tham số
    public Service() {
        super();
    }

    // Constructor đầy đủ tham số
    public Service(String serviceID, String serviceName, double price, String maxDistance) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.maxDistance = maxDistance;
    }

    // Getter và Setter
    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", price=" + price +
                ", maxDistance='" + maxDistance + '\'' +
                '}';
    }
}