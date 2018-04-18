package dev.fypwenjie.fypapp.Domain;



/**
 * Created by VINTEDGE on 7/4/2018.
 */

public class Account  {

    public String acc_id;
    public String acc_name;
    public String acc_email;
    public String acc_token;
    public int login_response;

    public Account(String acc_id, String acc_name, String acc_email, String acc_token, int login_response) {
        this.acc_id = acc_id;
        this.acc_name = acc_name;
        this.acc_email = acc_email;
        this.acc_token = acc_token;
        this.login_response = login_response;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_email() {
        return acc_email;
    }

    public void setAcc_email(String acc_email) {
        this.acc_email = acc_email;
    }

    public String getAcc_token() {
        return acc_token;
    }

    public void setAcc_token(String acc_token) {
        this.acc_token = acc_token;
    }

    public int getLogin_response() {
        return login_response;
    }

    public void setLogin_response(int login_response) {
        this.login_response = login_response;
    }
}
