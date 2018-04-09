package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Adapters.FoodAdapter;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.Fragment.NavigationDrawerFragment;
import dev.fypwenjie.fypapp.R;

public class StoreScreen extends AppCompatActivity {
    //Testing
    private static final String SOAP_ACTION = "http://tempuri.org/IService1/GetICS_CategoryCoupons";
    private static final String OPERATION_NAME = "GetICS_CategoryCoupons";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://www.ideazes.com/IdeazesWCF/IdeazesServices.svc";
    ArrayList<Food> foods = new ArrayList<Food>();
    Food food = new Food();
    String categoriesTitle;
    String categoriesID;
    // GridView newCounpons_list;
    RecyclerView newCounpons_list;
    ProgressDialog dialog;
    String TAG = "Response";
    TextView txtTitle;
    private SliderLayout mDemoSlider;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Navigation drawer
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        categoriesTitle = getIntent().getStringExtra("categoryDesc");
        categoriesID = getIntent().getStringExtra("categoryID");

        txtTitle = (TextView) findViewById(R.id.txtCategoryTitle);

        txtTitle.setText(categoriesTitle.toUpperCase());

        //Coupons List
        newCounpons_list = (RecyclerView) findViewById(R.id.newCoupon_list);
        newCounpons_list.setLayoutManager(new GridLayoutManager(StoreScreen.this, 2));
        newCounpons_list.setNestedScrollingEnabled(false);

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

    }

    public void calculate() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME);

        request.addProperty("CategoryID", categoriesID);
        request.addProperty("StartRowIndex", "1");
        request.addProperty("MaxRows", "99");
        request.addProperty("OrderBy", "CouponID");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            Log.i("Rest", response.toString());

            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject pii = (SoapObject) response.getProperty(i);
                food.setCProductDesc(pii.getProperty("CProductDesc").toString());
                food.setCouponID(pii.getProperty("CouponID").toString());
                food.setDiscAmount(pii.getProperty("DiscAmount").toString());
                food.setUnitPrice(pii.getProperty("UnitPrice").toString());
                food.setUserID(pii.getProperty("UserID").toString());
                byte[] bloc = Base64.decode(pii.getProperty("CouponImage").toString(), Base64.DEFAULT);
                food.setProductimage(bloc);

                foods.add(food.copy());
            }

        } catch (Exception exception)

        {
            Log.i(TAG, exception.toString());
        }
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(StoreScreen.this);
            dialog.setCancelable(true);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            FoodAdapter couponAdapter = new FoodAdapter(StoreScreen.this, foods);
            Log.i("Category screen s", String.valueOf(foods.size()));

            newCounpons_list.setAdapter(couponAdapter);
            dialog.cancel();
            Log.i(TAG, "onPostExecute");
        }

    }
}
