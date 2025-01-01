package bean;
import java.util.Date;

public class Shipper {
    private String employeeID;   // EmployeeID (CHAR(5))
    private String lastName;     // LastName (VARCHAR(10))
    private String middleName;   // MiddleName (VARCHAR(10))
    private String firstName;    // FirstName (VARCHAR(10), NOT NULL)
    private String gender;       // Gender (CHAR(6))
    private Date birthday;       // Birthday (DATE)
    private String phone;        // Phone (VARCHAR(10), UNIQUE, NOT NULL)
    private String homeTown;     // HomeTown (VARCHAR(45), NOT NULL)

    // Constructor không tham số
    public Shipper() {
        super();
    }

    // Constructor đầy đủ tham số
    public Shipper(String employeeID, String lastName, String middleName, String firstName,
                   String gender, Date birthday, String phone, String homeTown) {
        this.employeeID = employeeID;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.homeTown = homeTown;
    }

    // Getter và Setter
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    @Override
    public String toString() {
        return "Shipper{" +
                "employeeID='" + employeeID + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", homeTown='" + homeTown + '\'' +
                '}';
    }
}