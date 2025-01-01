package bean;

import java.util.Date;

public class AccUser {
    private String userID; // UserID (CHAR(5))
    private String passwordd; //password add thêm
    private String lastName; // LastName (VARCHAR(10))
    private String middleName; // MiddleName (VARCHAR(10))
    private String firstName; // FirstName (VARCHAR(10) - NOT NULL)
    private Date birthday; // Birthday (DATE)
    private String gender; // Gender (CHAR(3))
    private String phone; // Phone (VARCHAR(10) - UNIQUE, NOT NULL)
    private String city; // City (VARCHAR(30))
    private String district; // District (VARCHAR(45))
    private String ware; // Ware (VARCHAR(45))
    private String address; // Address (VARCHAR(45))

    // Constructor không tham số
    public AccUser() {
        super();
    }

    // Constructor đầy đủ tham số
    public AccUser(String userID, String passwordd, String lastName, String middleName, String firstName,
                   Date birthday, String gender, String phone, String city,
                   String district, String ware, String address) {
        this.userID = userID;
        this.passwordd = passwordd;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ware = ware;
        this.address = address;
    }

    // Getter và Setter
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getPassword() {
        return passwordd;
    }
    public void setPassword(String password) {
        this.passwordd = password;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Phương thức toString để in thông tin đối tượng
    @Override
    public String toString() {
        return "AccUser{" +
                "userID:'" + userID + '\'' +
                ", password:'" + passwordd + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", middleName:'" + middleName + '\'' +
                ", firstName:'" + firstName + '\'' +
                ", birthday:" + birthday +
                ", gender:'" + gender + '\'' +
                ", phone:'" + phone + '\'' +
                ", city:'" + city + '\'' +
                ", district:'" + district + '\'' +
                ", ware:'" + ware + '\'' +
                ", address:'" + address + '\'' +
                '}';
    }
}