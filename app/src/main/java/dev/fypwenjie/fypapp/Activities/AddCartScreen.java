package dev.fypwenjie.fypapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import com.shawnlin.numberpicker.NumberPicker;

import dev.fypwenjie.fypapp.R;

public class AddCartScreen extends AppCompatActivity {
    TextView txt_food_title;
    TextView txt_food_desc;
    TextView txt_food_price;
    EditText edit_food_remark;
    NumberPicker np_food_quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cart_screen);

        txt_food_title = (TextView) findViewById(R.id.txt_food_title);
        txt_food_desc = (TextView) findViewById(R.id.txt_food_desc);
        txt_food_price = (TextView) findViewById(R.id.txt_food_price);
        edit_food_remark = (EditText) findViewById(R.id.edit_food_remark);
        np_food_quantity = (NumberPicker) findViewById(R.id.np_food_quantity);

        txt_food_title.setText(getIntent().getStringExtra("food_name"));
        txt_food_desc.setText(getIntent().getStringExtra("food_desc"));
        txt_food_price.setText(getIntent().getStringExtra("food_price"));
        //Set the minimum value of NumberPicker
        np_food_quantity.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np_food_quantity.setMaxValue(99);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np_food_quantity.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        /*np_food_quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tv.setText("Selected Number : " + newVal);
            }
        });*/
    }
}
