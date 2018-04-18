package dev.fypwenjie.fypapp.Activities;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import dev.fypwenjie.fypapp.R;



public class LoginScreen2 extends AppCompatActivity {
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
        setContentView(R.layout.activity_login_screen2);

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

            }
        });
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



}
