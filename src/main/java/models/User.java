package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private int phone;

    public User() {}

    public User(String nomUtlstr, String password, String lastName, String firstName, String address,
                int phone) {
        this.username = nomUtlstr;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.phone = phone;
    }

    public User(int userId, String nomUtlstr, String password, String lastName, String firstName, String address, int phone) {
        this.userId = userId;
        this.username = nomUtlstr;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  "idUtlstr=" + userId +
                ", nomUtlstr='" + username + '\'' +
                ", motDePasse='" + password + '\'' +
                ", nom='" + lastName + '\'' +
                ", prenom='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", numTel=" + phone;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void createUserInstance(ResultSet userInfoResult) throws SQLException {
        setUserId(userInfoResult.getInt(1));
        setUsername(userInfoResult.getString(2));
        setPassword(userInfoResult.getString(3));
        setLastName(userInfoResult.getString(4));
        setFirstName(userInfoResult.getString(5));
        setAddress(userInfoResult.getString(6));
        setPhone(userInfoResult.getInt(7));
    }


}
