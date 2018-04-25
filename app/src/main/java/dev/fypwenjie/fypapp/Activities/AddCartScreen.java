package dev.fypwenjie.fypapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;

import dev.fypwenjie.fypapp.R;

public class AddCartScreen extends AppCompatActivity {
    TextView txt_food_title;
    TextView txt_food_desc;
    TextView txt_food_price;
    EditText edit_food_remark;
    NumberPicker np_food_quantity;
    private Toolbar toolbar;

    String display_food_price;
    Float food_price;
    String food_desc;
    String food_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        food_desc = getIntent().getStringExtra("food_desc");
        food_name = getIntent().getStringExtra("food_name");
        food_price = Float.parseFloat( getIntent().getStringExtra("food_price"));
        display_food_price = "RM " + getIntent().getStringExtra("food_price");

        txt_food_title = (TextView) findViewById(R.id.txt_food_title);
        txt_food_desc = (TextView) findViewById(R.id.txt_food_desc);
        txt_food_price = (TextView) findViewById(R.id.txt_food_price);
        edit_food_remark = (EditText) findViewById(R.id.edit_food_remark);
        np_food_quantity = (NumberPicker) findViewById(R.id.np_food_quantity);

        txt_food_title.setText(food_name);
        txt_food_desc.setText(food_desc);
        txt_food_price.setText(display_food_price);
        //Set the minimum value of NumberPicker
        np_food_quantity.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np_food_quantity.setMaxValue(99);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np_food_quantity.setWrapSelectorWheel(true);


        np_food_quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                display_food_price = "RM " + String.format(Locale.US, "%.2f", food_price * newVal);
                txt_food_price.setText(display_food_price);
            }
        });
        //Set a value change listener for NumberPicker
        /*np_food_quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tv.setText("Selected Number : " + newVal);
            }
        });*/
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
