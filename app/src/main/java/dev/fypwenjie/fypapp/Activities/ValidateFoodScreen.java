package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import dev.fypwenjie.fypapp.R;

public class ValidateFoodScreen extends AppCompatActivity {

    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://www.ideazes.com/IdeazesWCF/IdeazesServices.svc";
    private static final String SOAP_ACTION_VALID = "http://tempuri.org/IService1/GetValidateCoupon";
    private static final String OPERATION_NAME_VALID = "GetValidateCoupon";

    ProgressDialog dialog;
    Toolbar toolbar;
    ImageView imgQR;
    TextView txtCouponCode, txtVerify;
    Button btn_close;
    String TAG = "Response";
    String user_id, coupon_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_coupon_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        user_id = getIntent().getStringExtra("UserID");
        coupon_code = getIntent().getStringExtra("CouponCode");
        imgQR = (ImageView) findViewById(R.id.productImg);

        String input = coupon_code.toString();
        if (!input.equals("")) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(input, BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                if (!bitmap.equals("")) {
                    imgQR.setImageBitmap(bitmap);
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        txtCouponCode = (TextView) findViewById(R.id.txtCouponCode);
        txtCouponCode.setText(coupon_code);
        txtVerify = (TextView) findViewById(R.id.txtVerify);
        txtVerify.setText("Verify Failed, Please Try Again");
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ValidateFoodScreen.this, MainActivity.class);
                startActivity(i);
            }
        });
        Log.i("coupon_code", coupon_code);
        Log.i("user_id", user_id);

        final AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }


    public void get_response() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME_VALID);
        request.addProperty("CouponCode", coupon_code);
        request.addProperty("UserID ", user_id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        try {
            httpTransport.call(SOAP_ACTION_VALID, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            Log.i("Rest", response.toString());

        } catch (Exception exception) {
            Log.i(TAG, exception.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ValidateFoodScreen.this, MainActivity.class);
        startActivity(i);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ValidateFoodScreen.this);
            dialog.setCancelable(true);
            dialog.setMessage("Loading...");
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
