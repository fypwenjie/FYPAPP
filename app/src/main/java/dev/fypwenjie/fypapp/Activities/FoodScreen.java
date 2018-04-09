package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
        final AsyncCallWS task = new AsyncCallWS();
        task.execute();

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lineSep = System.getProperty("line.separator");
                String yourString = food.getCouponDesc();

                yourString = yourString.replaceAll("<br/>", lineSep);
                showConfirmDialog(FoodScreen.this, "More Info", yourString, "Close").show();
            }
        });
    }

    public void get_data() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME);
        request.addProperty("CouponID", coupon_id);
        request.addProperty("UserID", user_id);
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
                byte[] bloc = Base64.decode(pii.getProperty("CouponImage").toString(), Base64.DEFAULT);
                food.setProductimage(bloc);
                food.setTotalCoupon(pii.getProperty("TotalCoupon").toString());
                food.setAvailableCoupon(pii.getProperty("AvailableCoupon").toString());
                food.setValidFrom(pii.getProperty("ValidFrom").toString());
                food.setValidTo(pii.getProperty("ValidTo").toString());
                food.setCouponDesc(pii.getProperty("CouponDesc").toString());

                foods.add(food.copy());
            }
        } catch (Exception exception)

        {
            Log.i(TAG, exception.toString());
        }
    }

    public void get_response() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME_VALID);
        request.addProperty("Email", "kiensengc@gmail.com");
        request.addProperty("Password", "abcd1234");
        request.addProperty("Decryptkey", decryp.getText().toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Log.i("Rest", decryp.getText().toString());
        try {
            httpTransport.call(SOAP_ACTION_VALID, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.i("Rest", response.toString());
/*
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject pii = (SoapObject) response.getProperty(i);
                coupon.setCProductDesc(pii.getProperty("CProductDesc").toString());
                coupon.setCouponID(pii.getProperty("CouponID").toString());
                coupon.setDiscAmount(pii.getProperty("DiscAmount").toString());
                coupon.setUnitPrice(pii.getProperty("UnitPrice").toString());
                byte[] bloc = Base64.decode(pii.getProperty("CouponImage").toString(), Base64.DEFAULT);
                coupon.setProductimage(bloc);
                coupon.setTotalCoupon(pii.getProperty("TotalCoupon").toString());
                coupon.setAvailableCoupon(pii.getProperty("AvailableCoupon").toString());
                coupon.setValidFrom(pii.getProperty("ValidFrom").toString());
                coupon.setValidTo(pii.getProperty("ValidTo").toString());
                coupon.setCouponDesc(pii.getProperty("CouponDesc").toString());

                coupons.add(coupon.copy());
            }*/

        } catch (Exception exception)

        {
            Log.i(TAG, exception.toString());
        }
    }

    private AlertDialog showConfirmDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes) {
        final AlertDialog.Builder backDialog = new AlertDialog.Builder(act);
        backDialog.setTitle(title);
        backDialog.setMessage(message);
        backDialog.setCancelable(false);
        backDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return backDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FoodScreen.this);
            dialog.setCancelable(true);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            get_data();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            txtProductName.setText(food.getCProductDesc());
            txtUnitPrice.setText(currencyCode + " " + food.getUnitPrice());
            txtDisc.setText(currencyCode + " " + food.getDiscAmount());
            txtSaving.setText(String.format("%.0f", ((Double.parseDouble(food.getDiscAmount()) / Double.parseDouble(food.getUnitPrice())) * 100)) + "%");
            txtPrice.setText("Now " + currencyCode + String.format("%.2f", Double.parseDouble(food.getUnitPrice()) - Double.parseDouble(food.getDiscAmount())) + " only");
            txtAvailability.setText(food.getAvailableCoupon() + " Coupons Available");
            txtCouponsQTY.setText((Integer.parseInt(food.getTotalCoupon()) - Integer.parseInt(food.getAvailableCoupon())) + " Downloaded");


            txtValidFrom.setText(food.getValidFrom().substring(0, 10));
            txtValidUntil.setText(food.getValidTo().substring(0, 10));
            Bitmap couponBmp = BitmapFactory.decodeByteArray(food.getProductimage(), 0, food.getProductimage().length);
            productImage.setImageBitmap(couponBmp);

            //  newCounpons_list.setAdapter(couponAdapter);
            dialog.cancel();

        }
    }

    private class AsyncCallValidate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FoodScreen.this);
            dialog.setCancelable(true);
            dialog.setMessage("Validating...");
            dialog.show();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            get_response();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            dialog.cancel();
        }
    }
}