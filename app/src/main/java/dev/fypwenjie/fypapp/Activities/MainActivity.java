package dev.fypwenjie.fypapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import dev.fypwenjie.fypapp.Adapters.CategoryAdapter;
import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Account;
import dev.fypwenjie.fypapp.Domain.Category;
import dev.fypwenjie.fypapp.Fragment.NavigationDrawerFragment;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;
import dev.fypwenjie.fypapp.Util.GlobalValue;
import dev.fypwenjie.fypapp.Util.Util;

import static dev.fypwenjie.fypapp.R.id.slider;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener  {
    Button btn_test;
    private Toolbar toolbar;
    ProgressDialog dialog;
    private static final String GET_CATEGORY_URL = "https://fyp-wenjie.000webhostapp.com/category/category_list";
    RecyclerView newStore_list;

    ArrayList<Category> categories = new ArrayList<Category>();
    Category category = new Category();

    private Account account;
    private static final String GET_PROMOTION_URL = "https://fyp-wenjie.000webhostapp.com/promotion/promotion_list";
    ProgressDialog dialog2;
    final static String KEY_PARAM1 = "param1";
    DatabaseHelper db = new DatabaseHelper(this);

    ArrayList<Account> accounts_db = new ArrayList<Account>();

    final static String ACCOUNT_TABLE = "account";
    final static String COLUMN_ACC_ID = "acc_id";
    final static String COLUMN_CUST_ID = "cust_id";
    final static String COLUMN_USER_NAME = "user_name";
    final static String COLUMN_ACC_PASSWORD = "acc_password";
    final static String COLUMN_ACC_SECURITYCODE = "acc_security_code";
    final static String COLUMN_PROFILE_IMAGE_PATH = "profile_image_path";
    final static String COLUMN_ACC_BALANCE = "acc_balance";
    final static String COLUMN_REGISTER_DATE = "register_date";
    final static String KEY_RESPONSE = "response";

    final static String JSON_ARRAY = "result";
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //accounts_db = db.getAccount();

        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "8f6e423b-3d12-424d-8719-95a979ff2ea0",
                Analytics.class, Crashes.class);

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            Util.longToast(this, "No Internet Access!");
        }
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "cache_folder");
        ImageLoader imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

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
        file_maps.put("Banner 1", R.drawable.default_banner_1);
        file_maps.put("Banner 2", R.drawable.default_banner_2);

        for (String name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout

            defaultSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle()
                    .putString("extra", "0");
            mDemoSlider.addSlider(defaultSliderView);
        }
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        mDemoSlider.setDuration(4000);

        newStore_list = (RecyclerView) findViewById(R.id.newCoupon_list);
        newStore_list.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        newStore_list.setNestedScrollingEnabled(false);

        new Store_list("0").execute();
        new Promotion_list("0").execute();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String p_id = String.valueOf(slider.getBundle().get("extra"));
        Log.i("P_ID",p_id);
        if (p_id != "0") {
            Intent i = new Intent(this, PromotionScreen.class);
            i.putExtra("promotion_id", String.valueOf(slider.getBundle().get("extra")));
            this.startActivity(i);
        }
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

    public class Store_list extends AsyncTask<String, Void, String> {
        String param1;
        RequestHandler rh = new RequestHandler();

        public Store_list(String param1) {
            this.param1 = param1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
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

                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    category.setCategory_name(jsonobject.getString("fc_name"));
                    category.setCategory_id(valueOf(jsonobject.getInt("id")));
                    category.setCategory_banner(jsonobject.getString("fc_image"));

                    categories.add(category.copy());
                }

                CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, categories);
                Log.i("home screen s", valueOf(categories.size()));


                newStore_list.setAdapter(categoryAdapter);
                dialog.cancel();
                Log.i("tag", "onPostExecute");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_PARAM1, param1);
            return rh.sendPostRequest(GET_CATEGORY_URL, information);
        }
    }

    public class Promotion_list extends AsyncTask<String, Void, String> {
        String param1;
        RequestHandler rh = new RequestHandler();

        public Promotion_list(String param1) {
            this.param1 = param1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(MainActivity.this);
            dialog2.setCancelable(false);
            dialog2.setMessage("Loading...");
            //  dialog.show();
            Log.i("tag", "onPreExecute");
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {

                JSONArray jsonArray = new JSONArray(json);
                Log.d("promotion list", valueOf(jsonArray));
                mDemoSlider = (SliderLayout) findViewById(slider);
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);

                    url_maps.put(valueOf(jsonobject.getInt("id")) + ";" + jsonobject.getString("p_name"), GlobalValue.Domain_name + jsonobject.getString("p_banner"));
                }

                for (String name : url_maps.keySet()) {
                    DefaultSliderView defaultSliderView = new DefaultSliderView(MainActivity.this);
                    // initialize a SliderLayout

                    String[] parts = name.split(";");
                    String id = parts[0];
                    String title = parts[1];

                    Log.i("Name", name);
                    Log.i("ID", id);
                    Log.i("Title", title);
                    defaultSliderView
                            .description(title)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(MainActivity.this);
                    //add your extra information
                    defaultSliderView.bundle(new Bundle());
                    defaultSliderView.getBundle()
                            .putString("extra", id);

                    mDemoSlider.addSlider(defaultSliderView);
                }
                mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
                mDemoSlider.setDuration(4000);

                dialog2.cancel();
                Log.i("PromotionList", "onPromotionPostExecute");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_PARAM1, param1);
            Log.d("URL ", GET_PROMOTION_URL);
            Log.d("param1 ", param1);
            return rh.sendPostRequest(GET_PROMOTION_URL, information);
        }
    }
}
