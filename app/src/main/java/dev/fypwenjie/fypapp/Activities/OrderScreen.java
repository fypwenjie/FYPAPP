package dev.fypwenjie.fypapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import dev.fypwenjie.fypapp.Adapters.OrderAdapter;
import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Cart;
import dev.fypwenjie.fypapp.R;
import dev.fypwenjie.fypapp.RequestHandler;

import static dev.fypwenjie.fypapp.R.id.total_price;

public class OrderScreen extends AppCompatActivity {
    final static String KEY_PARAM1 = "arr";
    final static String KEY_PARAM2 = "totalPrice";
    final static String KEY_PARAM3 = "cust_id";
    private static final String CHECKOUT_URL = "https://fyp-wenjie.000webhostapp.com/order/checkout";
    RecyclerView order_list;
    Button btn_checkout;
    Toolbar toolbar;
    LinearLayout total_layout;
    String totalPrice,cust_id;
    TextView txt_total,txt_no_record;
    ProgressDialog dialog;
    Cart cart = new Cart();
    ArrayList<Cart> carts = new ArrayList<Cart>();
    JSONObject jCheckout = new JSONObject();
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        order_list = (RecyclerView) findViewById(R.id.order_list);
        order_list.setLayoutManager(new GridLayoutManager(OrderScreen.this, 1));
        order_list.setNestedScrollingEnabled(false);

        total_layout = (LinearLayout) findViewById(R.id.total_layout);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);
        txt_total = (TextView) findViewById(total_price);
        txt_no_record = (TextView) findViewById(R.id.no_record);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String cartJson = gson.toJson(carts);
                Log.i("GSON",cartJson);
                new Checkout(cartJson, totalPrice, cust_id).execute();
            }
        });

        cust_id = "1";

        totalPrice =  String.format(Locale.US, "%.2f",  grandTotal (db.getCarts()));
        txt_total.setText("RM " + totalPrice );
        carts = db.getCarts();

        if(carts.size()> 0){
            txt_no_record.setVisibility(View.GONE);
            order_list.setVisibility(View.VISIBLE);
            btn_checkout.setVisibility(View.VISIBLE);
            total_layout.setVisibility(View.VISIBLE);
        }else {
            txt_no_record.setVisibility(View.VISIBLE);
            order_list.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.GONE);
            total_layout.setVisibility(View.GONE);
        }
        OrderAdapter orderAdapter = new OrderAdapter(OrderScreen.this, carts);

        order_list.setAdapter(orderAdapter);



    }

    private Double grandTotal(ArrayList<Cart> items){

        Double totalPrice = 0.00;
        for(int i = 0 ; i < items.size(); i++) {
            totalPrice += Double.parseDouble( items.get(i).getPrice());
        }

        return totalPrice;
    }



    public class Checkout extends AsyncTask<String, Void, String> {
        String cart, totalPrice, cust_id;
        RequestHandler rh = new RequestHandler();

        public Checkout(String param_arr, String totalPrice, String cust_id) {
            this.cart = param_arr;
            this.totalPrice = totalPrice;
            this.cust_id = cust_id;
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

            db.remove_cart();
            showConfirmDialog(OrderScreen.this,"Place order success","Queue number can be view in History page. ","Okay");
            dialog.cancel();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> information = new HashMap<>();
            information.put(KEY_PARAM1, cart);
            information.put(KEY_PARAM2, totalPrice);
            information.put(KEY_PARAM3, cust_id);
            return rh.sendPostRequest(CHECKOUT_URL, information);
        }
    }

    private AlertDialog showConfirmDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes) {
        final AlertDialog.Builder backDialog = new AlertDialog.Builder(act);
        backDialog.setTitle(title);
        backDialog.setMessage(message);
        backDialog.setCancelable(false);
        backDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        return backDialog.show();
    }
}
