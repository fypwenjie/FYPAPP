package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

import dev.fypwenjie.fypapp.R;



public class LoginScreen extends AppCompatActivity {
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://www.ideazes.com/IdeazesWCF/IdeazesServices.svc";
    private static final String SOAP_ACTION_VALID = "http://tempuri.org/IService1/GetValidateICSUser";
    private static final String OPERATION_NAME_VALID = "GetValidateICSUser";
    private static final String SOAP_ACTION_CUSTINFO = "http://tempuri.org/IService1/GetICSUserDatabyEmail";
    private static final String OPERATION_NAME_CUSTINFO = "GetICSUserDatabyEmail";
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String DECRYPT_KEY = "IDEAZESICS88";
    Button btn_get;
    ProgressDialog dialog;
    String login_result, email, password, cust_info;
    String[] arr_custFields;
    Map<String, String> arr_custInfo = new HashMap<>();
    EditText et_email, et_password;
    String TAG = "Response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Resources res = getResources();
        arr_custFields = res.getStringArray(R.array.cust_info);

        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);
        btn_get = (Button) findViewById(R.id.btn_login);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                AsyncCallValidate taskValid = new AsyncCallValidate();
                taskValid.execute();
            }
        });
    }

    public void get_response() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME_VALID);
        request.addProperty("Email", email);
        request.addProperty("Password", password);
        request.addProperty("Decryptkey", DECRYPT_KEY);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        try {
            httpTransport.call(SOAP_ACTION_VALID, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            login_result = response.toString();
            Log.i("Rest", response.toString());

        } catch (Exception exception)

        {
            Log.i(TAG, exception.toString());
        }
    }

    public void get_cust_info() {
        if (arr_custFields != null && arr_custFields.length > 0) {
            for (String c : arr_custFields) {

                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                        OPERATION_NAME_CUSTINFO);
                request.addProperty("Email", email);
                request.addProperty("Field", c);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

                try {
                    httpTransport.call(SOAP_ACTION_CUSTINFO, envelope);
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    cust_info = response.toString();
                    arr_custInfo.put(c, cust_info);
                    Log.i("Rest", response.toString());

                } catch (Exception exception) {
                    Log.i(TAG, exception.toString());
                }
            }
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

    private class AsyncCallValidate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginScreen.this);
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
            if (login_result.equals("false")) {
                showConfirmDialog(LoginScreen.this, "Error", "Login Failed !", "Close").show();
                dialog.cancel();
            } else {
                dialog.cancel();
                AsyncCallCustInfo taskCust = new AsyncCallCustInfo();
                taskCust.execute();

                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private class AsyncCallCustInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginScreen.this);
            dialog.setCancelable(true);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            get_cust_info();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            for (Map.Entry entry : arr_custInfo.entrySet()) {
                editor.putString(entry.getKey().toString(), entry.getValue().toString());
            }
            editor.commit();

            dialog.cancel();
        }
    }

}
