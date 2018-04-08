package dev.fypwenjie.fypapp.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.HashMap;

import dev.fypwenjie.fypapp.Domain.Account;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;
import dev.fypwenjie.fypapp.Util;

public class MainActivity extends AppCompatActivity {
    Button btn_test;
    private Account account;
    private static final String PUSH_URL = "https://fyp-wenjie.000webhostapp.com/login/push";
    final static String KEY_PARAM1 = "param1";
    final static String JSON_ARRAY = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "8f6e423b-3d12-424d-8719-95a979ff2ea0",
                Analytics.class, Crashes.class);

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            Util.longToast(this, "No Internet Access!");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_test = (Button) findViewById(R.id.btn_push);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Push("hi").execute();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Push extends AsyncTask<String, Void, String> {
        String param1;
        RequestHandler rh = new RequestHandler();

        public Push(String param1) {
            this.param1 = param1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
//
//            try {
//
//                JSONArray jsonArray = new JSONObject(json).getJSONArray(JSON_ARRAY);
//                JSONObject jsonData = jsonArray.getJSONObject(0);
//
//                accounts = new Accounts();
//
//
//                // These fields may not null
//                accounts.setAcc_id(jsonData.getInt(COLUMN_ACC_ID));
//                accounts.setCust_id(jsonData.getString(COLUMN_CUST_ID));
//                accounts.setUser_name(jsonData.getString(COLUMN_USER_NAME));
//                accounts.setAcc_password(jsonData.getString(COLUMN_ACC_PASSWORD));
//                accounts.setAcc_security_code(jsonData.getString(COLUMN_ACC_SECURITYCODE));
//                accounts.setProfile_image_path(jsonData.getString(COLUMN_PROFILE_IMAGE_PATH));
//                accounts.setAcc_balance(jsonData.getDouble(COLUMN_ACC_BALANCE));
//                accounts.setRegister_date(mySqlDateFormat.parse(jsonData.getString(COLUMN_REGISTER_DATE)));
//                accounts.setLogin_response((jsonData.getInt(KEY_RESPONSE)));
//                //Log.d("LoginActivity", "response : " + jsonObject.getInt("response")+"");
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            switch (response) {
//                case 0:
//                    longToast(LoginActivity.this, "No Connection! Please Try Again Later.");
//                    break;
//                case 1:
//                    shortToast(LoginActivity.this, "Login Successful");
//                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    break;
//                case 2:
//                    shortToast(LoginActivity.this, "Wrong Username or Password");
//                    editTextUsername.requestFocus();
//                    editTextPassword.setText("");
//                    break;
//            }
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_PARAM1, param1);
            return rh.sendPostRequest(PUSH_URL, information);
        }
    }
}
