package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dev.fypwenjie.fypapp.Adapters.FoodAdapter;
import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.Domain.Store;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;


public class StoreScreen extends AppCompatActivity implements AbsListView.OnScrollListener {

    ArrayList<Food> foods = new ArrayList<Food>();
    ArrayList<Store> stores = new ArrayList<Store>();
    Food food = new Food();
    Store store = new Store();
    String categoriesTitle;
    ListView listView;
    String store_id;
    ProgressDialog dialog;
    String TAG = "Response";
    TextView txt_store_name;
    TextView txt_store_cat;

    private Toolbar mToolbar;
    View mContainerHeader;
    FloatingActionButton mFab;

    private static final String GET_FOOD_URL = "https://fyp-wenjie.000webhostapp.com/food/food_list";
    private static final String KEY_FOOD_SID = "food_store_id";
    ObjectAnimator fade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("store_name"));

        listView = (ListView)findViewById(R.id.listview);

        store_id = getIntent().getStringExtra("store_id");

        Log.d("store id", store_id.toString());
        // Inflate the header view and attach it to the ListView
        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.listview_header, listView, false);
        mContainerHeader = headerView.findViewById(R.id.container);
        listView.addHeaderView(headerView);

        txt_store_name = headerView.findViewById(R.id.txt_store_name);
        txt_store_name.setText(getIntent().getStringExtra("store_name"));

        txt_store_cat = headerView.findViewById(R.id.txt_store_cat);
        txt_store_cat.setText(getIntent().getStringExtra("store_cat"));

        // prepare the fade in/out animator
        fade =  ObjectAnimator.ofFloat(mContainerHeader, "alpha", 0f, 1f);
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(400);

        listView.setOnScrollListener(this);
        new Food_list(store_id).execute();
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
            if (view.getChildAt(0).getTop() < -dpToPx(100)) {
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

    public class Food_list extends AsyncTask<String, Void, String> {
        String food_sid;
        RequestHandler rh = new RequestHandler();

        public Food_list(String food_sid) {
            this.food_sid = food_sid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(StoreScreen.this);
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
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        food.setFood_id(String.valueOf(jsonobject.getString("id")));
                        food.setFood_name(jsonobject.getString("f_name"));
                        food.setFood_desc(jsonobject.getString("f_description"));
                        food.setFood_category(jsonobject.getString("f_category"));
                        food.setFood_price(jsonobject.getString("f_price"));
                        food.setFood_banner(jsonobject.getString("f_banner"));

                        foods.add(food.copy());
                    }

                    FoodAdapter foodAdapter = new FoodAdapter(foods, StoreScreen.this);


                    listView.setAdapter(foodAdapter);
                    dialog.cancel();
                    Log.i("tag", "onPostExecute");
                }else {
                    txt_store_name.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_FOOD_SID, food_sid);
            Log.d("store id 2",food_sid);
            return rh.sendPostRequest(GET_FOOD_URL, information);
        }
    }

}
