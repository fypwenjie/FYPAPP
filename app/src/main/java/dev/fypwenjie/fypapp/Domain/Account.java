package dev.fypwenjie.fypapp.Domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by VINTEDGE on 7/4/2018.
 */

public class Account implements Serializable {

    public int acc_id;
    public String cust_id;
    public String user_name;
    public String acc_password;
    public String acc_security_code;
    public String profile_image_path;
    public double acc_balance;
    public Date register_date;
    public int login_response;

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAcc_password() {
        return acc_password;
    }

    public void setAcc_password(String acc_password) {
        this.acc_password = acc_password;
    }

    public String getAcc_security_code() {
        return acc_security_code;
    }

    public void setAcc_security_code(String acc_security_code) {
        this.acc_security_code = acc_security_code;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public double getAcc_balance() {
        return acc_balance;
    }

    public void setAcc_balance(double acc_balance) {
        this.acc_balance = acc_balance;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public int getLogin_response() {
        return login_response;
    }

    public void setLogin_response(int login_response) {
        this.login_response = login_response;
    }

}
