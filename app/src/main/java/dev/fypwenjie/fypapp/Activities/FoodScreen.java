package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Domain.Food;
import dev.fypwenjie.fypapp.R;

public class FoodScreen extends AppCompatActivity {
    private static final String SOAP_ACTION = "http://tempuri.org/IService1/GetICS_CouponDetails";
    private static final String OPERATION_NAME = "GetICS_CouponDetails";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://www.ideazes.com/IdeazesWCF/IdeazesServices.svc";
    private static final String SOAP_ACTION_VALID = "http://tempuri.org/IService1/GetValidateICSUser";
    private static final String OPERATION_NAME_VALID = "GetValidateICSUser";
    String coupon_id;
    String user_id;
    String currencyCode;
    String TAG = "Response";
    TextView txtProductName, txtUnitPrice, txtDisc, txtSaving, txtPrice, txtValidFrom, txtValidUntil, txtAvailability, txtCouponsQTY;
    ImageView productImage;
    ProgressDialog dialog;
    Button btn_get, btn_more;
    EditText decryp;

    ArrayList<Food> foods = new ArrayList<Food>();
    Food food = new Food();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //String From Value/String
        currencyCode = this.getString(R.string.currency);
        //Get String from intent
        user_id = getIntent().getStringExtra("user_id");
        coupon_id = getIntent().getStringExtra("coupon_id");

        //Define Views
        productImage = (ImageView) findViewById(R.id.productImg);
        txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtUnitPrice = (TextView) findViewById(R.id.txtUnitPrice);
        txtDisc = (TextView) findViewById(R.id.txtDisc);
        txtSaving = (TextView) findViewById(R.id.txtSaving);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtValidFrom = (TextView) findViewById(R.id.txtValidFrom);
        txtValidUntil = (TextView) findViewById(R.id.txtValidUntil);
        txtAvailability = (TextView) findViewById(R.id.txtAvailability);
        txtCouponsQTY = (TextView) findViewById(R.id.txtCouponsQTY);
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_more = (Button) findViewById(R.id.btn_more_info);
        decryp = (EditText) findViewById(R.id.decryp);

        //Call Async , GetData from webservice


        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}