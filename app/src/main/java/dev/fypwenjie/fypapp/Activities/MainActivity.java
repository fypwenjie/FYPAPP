package dev.fypwenjie.fypapp.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.HashMap;

import dev.fypwenjie.fypapp.Domain.Account;
import dev.fypwenjie.fypapp.Fragment.NavigationDrawerFragment;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;
import dev.fypwenjie.fypapp.Util.Util;

import static dev.fypwenjie.fypapp.R.id.slider;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener  {
    Button btn_test;
    private Toolbar toolbar;

    private Account account;
    private static final String PUSH_URL = "https://fyp-wenjie.000webhostapp.com/login/push";
    final static String KEY_PARAM1 = "param1";
    final static String JSON_ARRAY = "result";
    private SliderLayout mDemoSlider;

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

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mDemoSlider = (SliderLayout) findViewById(slider);
      /*  HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("img1", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("img2", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("img3", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("img4", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");*/

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.vocal);
        file_maps.put("Big Bang Theory", R.drawable.aquarium);
        file_maps.put("House of Cards", R.drawable.watermelon);

        for (String name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            defaultSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(defaultSliderView);
        }
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        mDemoSlider.setDuration(4000);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
