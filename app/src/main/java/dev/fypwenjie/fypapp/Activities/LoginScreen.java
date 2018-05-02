package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Account;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;


public class LoginScreen extends AppCompatActivity {
    Button btn_get;
    ProgressDialog dialog;
    Account account;
    DatabaseHelper databaseHelper;
    String login_result, email, password, cust_info;
    String[] arr_custFields;
    Map<String, String> arr_custInfo = new HashMap<>();
    EditText et_email, et_password;
    TextView link_signup;
    String TAG = "Response";
    private static final String LOGIN_URL = "https://fyp-wenjie.000webhostapp.com/customer/cust_login";
    private static final String KEY_USERNAME = "login_username";
    private static final String KEY_LOGINPASS = "login_password";
    private static final String KEY_FOOD_SID = "food_store_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        account = new Account();
        databaseHelper = new DatabaseHelper(this);
        Resources res = getResources();
        arr_custFields = res.getStringArray(R.array.cust_info);
        link_signup = (TextView) findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fyp-wenjie.000webhostapp.com/customer/register"));
                startActivity(i);
            }
        });
        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);
        btn_get = (Button) findViewById(R.id.btn_login);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                new Login(email,password).execute();
            }
        });

    }

    public class Login extends AsyncTask<String, Void, String> {
        String acc_username;
        String acc_password;
        RequestHandler rh = new RequestHandler();

        public Login(String acc_username, String acc_password) {

            this.acc_username = acc_username;
            this.acc_password = acc_password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginScreen.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i("tag", "onPreExecute");
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                JSONArray jsonArray = new JSONArray(json);
                JSONObject studentsJsonArry =  jsonArray.getJSONObject(0);
                Log.i("Login Array", String.valueOf(studentsJsonArry));
                Log.i("Login ID",  String.valueOf( studentsJsonArry.get("id")));

                if (jsonArray != null) {

                        account.setAcc_id(String.valueOf( studentsJsonArry.get("id")));
                        account.setAcc_username(String.valueOf( studentsJsonArry.get("c_username")));
                        account.setAcc_name(String.valueOf(studentsJsonArry.get("c_display_name")));
                        account.setAcc_email(String.valueOf(studentsJsonArry.get("c_email")));
                        account.setAcc_contact(String.valueOf(studentsJsonArry.get("c_contact_no")));
                        account.setAcc_status( Integer.parseInt( String.valueOf(studentsJsonArry.get("c_status"))));

                        databaseHelper.Login(account);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.cancel();
            Intent i = new Intent(LoginScreen.this, MainActivity.class);
            LoginScreen.this.startActivity(i);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_USERNAME, acc_username);
            information.put(KEY_LOGINPASS, acc_password);
            return rh.sendPostRequest(LOGIN_URL, information);
        }
    }

    private AlertDialog showConfirmDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes) {
        final AlertDialog.Builder backDialog = new AlertDialog.Builder(act);
        backDialog.setTitle(title);
        backDialog.setMessage(message);
        backDialog.setCancelable(false);
        backDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return backDialog.show();
    }



}
