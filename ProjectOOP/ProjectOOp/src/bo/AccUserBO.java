package bo;

import java.sql.SQLException;
import bean.AccUser;
import dao.AccUserDAO;

public class AccUserBO {

    AccUserDAO AccuserDAO = new AccUserDAO();

    //kiem tra dang nhap
    public boolean CheckLogin(String userID, String passwordd) throws SQLException {
        return AccuserDAO.CheckLogin(userID, passwordd);
    }
    //them user
    public void addAccUser(String userID, String passwordd, String lastName, String middleName, String firstName,
                           java.util.Date birthday, String gender, String phone, String city, String district, String ware, String address) {
        AccuserDAO.addAccUser(userID, passwordd, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);
    }
    //sua thong tin user
    public void editProfile(String userID,String lastName, String middleName, String firstName,
                            java.util.Date birthday, String gender, String phone, String city, String district, String ware, String address) {
        AccuserDAO.editProfile(userID, lastName, middleName, firstName, birthday, gender, phone, city, district, ware, address);
    }
    //doi mat khau
    public void changePassword(String userID, String newpassword) {
        AccuserDAO.changePassword(userID, newpassword);
    }
    //xoa user
    public void deleteAccUser(String userID, String passwordd)throws SQLException {AccuserDAO.deleteAccUser(userID, passwordd);}
    //hien thi ds nguoi dung
    public void showAllUsers() throws SQLException {
            AccUserDAO accUserDAO = new AccUserDAO();
            accUserDAO.showAllUsers();
    }
    public void showUserByID(String userID) throws SQLException {
        AccUserDAO accUserDAO = new AccUserDAO();
        accUserDAO.showUserByID(userID);
    }
    
    public AccUser getUserByID(String userID) throws SQLException {
        return AccUserDAO.getUserByID(userID); // Gọi DAO để truy vấn
    }
    
    
}