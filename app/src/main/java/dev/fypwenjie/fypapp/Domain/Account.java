package dev.fypwenjie.fypapp.Domain;


/**
 * Created by VINTEDGE on 7/4/2018.
 */

public class Account  {
    public static final String TABLE_NAME = "cust";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERID = "c_id";
    public static final String COLUMN_USERNAME = "c_username";
    public static final String COLUMN_DISPLAY_NAME = "c_name";
    public static final String COLUMN_EMAIL = "c_email";
    public static final String COLUMN_CONTACT = "c_contact_no";
    public static final String COLUMN_TOKEN = "c_contact_no";
    public static final String COLUMN_STATUS = "c_status";
    public static final String COLUMN_CREATETIME = "c_createtime";

    public String acc_id;
    public String acc_username;
    public String acc_name;
    public String acc_email;
    public String acc_contact;
    public String acc_token;
    public int acc_status;

    public Account(String acc_id, String acc_username, String acc_name, String acc_email, String acc_contact, String acc_token, int acc_status) {
        this.acc_id = acc_id;
        this.acc_username = acc_username;
        this.acc_name = acc_name;
        this.acc_email = acc_email;
        this.acc_contact = acc_contact;
        this.acc_token = acc_token;
        this.acc_status = acc_status;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERID + " INTEGER,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_DISPLAY_NAME + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_CONTACT + " TEXT,"
                    + COLUMN_TOKEN + " TEXT NOT NULL DEFAULT '1',"
                    + COLUMN_STATUS + " INTEGER NOT NULL DEFAULT 1,"
                    + COLUMN_CREATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public String getAcc_contact() {
        return acc_contact;
    }

    public void setAcc_contact(String acc_contact) {
        this.acc_contact = acc_contact;
    }

    public String getAcc_username() {
        return acc_username;
    }

    public void setAcc_username(String acc_username) {
        this.acc_username = acc_username;
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

    public int getAcc_status() {
        return acc_status;
    }

    public void setAcc_status(int login_response) {
        this.acc_status = login_response;
    }
}
