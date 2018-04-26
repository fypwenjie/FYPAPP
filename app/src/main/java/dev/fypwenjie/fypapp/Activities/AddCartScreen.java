package dev.fypwenjie.fypapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;

import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Cart;
import dev.fypwenjie.fypapp.R;

public class AddCartScreen extends AppCompatActivity {
    TextView txt_food_title;
    TextView txt_food_desc;
    TextView txt_food_price;
    EditText edit_food_remark;
    NumberPicker np_food_quantity;
    Button btn_add_cart;
    private Toolbar toolbar;

    Cart cart = new Cart();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    String display_food_price;
    Float food_price;
    String food_desc;
    String food_id;
    String food_quantity;
    String food_name;
    String user_id = "1";
    String token = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        food_desc = getIntent().getStringExtra("food_desc");
        food_name = getIntent().getStringExtra("food_name");
        food_id = getIntent().getStringExtra("food_id");
        food_quantity = getIntent().getStringExtra("food_quantity");
        food_price = Float.parseFloat( getIntent().getStringExtra("food_price"));
        display_food_price = getIntent().getStringExtra("food_price");

        txt_food_title = (TextView) findViewById(R.id.txt_food_title);
        txt_food_desc = (TextView) findViewById(R.id.txt_food_desc);
        txt_food_price = (TextView) findViewById(R.id.txt_food_price);
        edit_food_remark = (EditText) findViewById(R.id.edit_food_remark);
        np_food_quantity = (NumberPicker) findViewById(R.id.np_food_quantity);
        btn_add_cart = (Button) findViewById(R.id.btn_add_cart);


        txt_food_title.setText(food_name);
        txt_food_desc.setText(food_desc);
        txt_food_price.setText("RM " +  display_food_price);

        //Set the minimum value of NumberPicker
        np_food_quantity.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np_food_quantity.setMaxValue(Integer.parseInt(food_quantity));

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np_food_quantity.setWrapSelectorWheel(true);
        np_food_quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                display_food_price = String.format(Locale.US, "%.2f", food_price * newVal);
                txt_food_price.setText("RM " + display_food_price);
            }
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cart.setUser_id(user_id);
                cart.setFood_id(food_id);
                cart.setFood_name(food_name);
                cart.setQuantity (String.valueOf(np_food_quantity.getValue()));
                cart.setPrice (display_food_price);
                cart.setRemark (String.valueOf(edit_food_remark.getText()));
                cart.setToken (token);

                databaseHelper.addCart(cart);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
}
