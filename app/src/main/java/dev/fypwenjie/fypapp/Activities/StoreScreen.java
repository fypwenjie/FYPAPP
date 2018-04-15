package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Adapters.FoodAdapter;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.R;

public class StoreScreen extends AppCompatActivity implements AbsListView.OnScrollListener {
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

    final static String[] DUMMY_DATA = {
            "France",
            "Sweden",
            "Germany",
            "USA",
            "Portugal",
            "The Netherlands",
            "Belgium",
            "Spain",
            "United Kingdom",
            "Mexico",
            "Finland",
            "Norway",
            "Italy",
            "Ireland",
            "Brazil",
            "Japan"
    };

    Toolbar mToolbar;
    View mContainerHeader;
    FloatingActionButton mFab;

    ObjectAnimator fade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("categoryDesc"));

        ListView listView = (ListView)findViewById(R.id.listview);


        // Inflate the header view and attach it to the ListView
        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header, listView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        listView.addHeaderView(headerView);

        // prepare the fade in/out animator
        fade =  ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        listView.setOnScrollListener(this);
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                DUMMY_DATA));
        //Navigation drawer

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    /**
     * Listen to the scroll events of the listView
     * @param view the listView
     * @param firstVisibleItem the first visible item
     * @param visibleItemCount the number of visible items
     * @param totalItemCount the amount of items
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onScroll(AbsListView view,
                         int firstVisibleItem,
                         int visibleItemCount,
                         int totalItemCount) {
        // we make sure the list is not null and empty, and the header is visible
        if (view != null && view.getChildCount() > 0 && firstVisibleItem == 0) {

            // we calculate the FAB's Y position
            int translation = view.getChildAt(0).getHeight() + view.getChildAt(0).getTop();

            // if we scrolled more than 16dps, we hide the content and display the title
            if (view.getChildAt(0).getTop() < -dpToPx(25)) {
                toggleHeader(false, false);
                mToolbar.setBackgroundColor(getResources().getColor(android.R.color.white));
            } else {
                toggleHeader(true, true);
                mToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        } else {
            toggleHeader(false, false);
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.white));
        }

        // if the device uses Lollipop or above, we update the ToolBar's elevation
        // according to the scroll position.
        if (isLollipop()) {
            if (firstVisibleItem == 0) {
                mToolbar.setElevation(0);
            } else {
                mToolbar.setElevation(dpToPx(4));
            }
        }
    }

    /**
     * Start the animation to fade in or out the header's content
     * @param visible true if the header's content should appear
     * @param force true if we don't wait for the animation to be completed
     *              but force the change.
     */
    private void toggleHeader(boolean visible, boolean force) {
        if ((force && visible) || (visible && mContainerHeader.getAlpha() == 0f)) {
            fade.setFloatValues(mContainerHeader.getAlpha(), 1f);
            fade.start();
        } else if (force || (!visible && mContainerHeader.getAlpha() == 1f)){
            fade.setFloatValues(mContainerHeader.getAlpha(), 0f);
            fade.start();
        }
        // Toggle the visibility of the title.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(!visible);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, m);
        return super.onCreateOptionsMenu(m);
    }

    /**
     * Convert Dps into Pxs
     * @param dp a number of dp to convert
     * @return the value in pixels
     */
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    /**
     * Check if the device rocks, and runs Lollipop
     * @return true if Lollipop or above
     */
    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
