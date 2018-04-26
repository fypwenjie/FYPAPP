package dev.fypwenjie.fypapp.Activities;

import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dev.fypwenjie.fypapp.Adapters.CategoryAdapter;
import dev.fypwenjie.fypapp.Adapters.OrderAdapter;
import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Cart;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;

public class OrderScreen extends AppCompatActivity {
    RecyclerView order_list;
    Button btn_checkout;

    ProgressDialog dialog;
    Cart cart = new Cart();
    ArrayList<Cart> carts = new ArrayList<Cart>();
    JSONObject jCheckout = new JSONObject();
    DatabaseHelper db = new DatabaseHelper(this);
    private static final String CHECKOUT_URL = "https://fyp-wenjie.000webhostapp.com/order/checkout";
    final static String KEY_PARAM1 = "arr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        order_list = (RecyclerView) findViewById(R.id.order_list);
        order_list.setLayoutManager(new GridLayoutManager(OrderScreen.this, 1));
        order_list.setNestedScrollingEnabled(false);

        btn_checkout = (Button)  findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jCheckout = createGroupInServer(cart,carts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        carts = db.getCarts("1");

        OrderAdapter orderAdapter = new OrderAdapter(OrderScreen.this, carts);

        order_list.setAdapter(orderAdapter);
    }

    public class Checkout extends AsyncTask<String, Void, String> {
       String param1;
        RequestHandler rh = new RequestHandler();

        public Checkout(String param_arr) {
            this.param1 = param_arr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(OrderScreen.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i("tag", "onPreExecute");
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);



        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_PARAM1, param1);
            return rh.sendPostRequest(CHECKOUT_URL, information);
        }
    }

    public JSONObject createGroupInServer(
            Cart cart,
            ArrayList<Cart> carts)
            throws JSONException {

        JSONObject jResult = new JSONObject();

        JSONArray jArray = new JSONArray();

        for (int i = 0; i < carts.size(); i++) {
            JSONObject jGroup = new JSONObject();
            jGroup.put("guid", carts.get(i).getCart_id());
            jGroup.put("name", carts.get(i).getFood_name());
            jGroup.put("isDeleted", carts.get(i).getStatus());
            // etcetera

            JSONObject jOuter = new JSONObject();
            jOuter.put("contact_group", jGroup);

            jArray.put(jOuter);
        }

        jResult.put("recordset", jArray);
        return jResult;
    }
}
