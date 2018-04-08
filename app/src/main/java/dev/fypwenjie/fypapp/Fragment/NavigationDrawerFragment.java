package dev.fypwenjie.fypapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beta.ideazes.tcfoo.ideazes.Activity.LoginScreen;
import com.beta.ideazes.tcfoo.ideazes.Activity.QrScanner;
import com.beta.ideazes.tcfoo.ideazes.Adapter.CategoryAdapter;
import com.beta.ideazes.tcfoo.ideazes.Data.Category;
import dev.fypwenjie.fypapp.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String SOAP_ACTION = "http://tempuri.org/IService1/GetICS_Categories";
    private static final String OPERATION_NAME = "GetICS_Categories";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ADDRESS = "http://www.ideazes.com/IdeazesWCF/IdeazesServices.svc";
    ArrayList<Category> categories = new ArrayList<Category>();
    Category category = new Category();
    ProgressDialog dialog;
    String TAG = "Response";
    RecyclerView newCategory_list;
    TextView qr_scanner, login, txtImgName, txtCustName, txtCustEmail;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout drawer_user_profile;
    private boolean mUserLearnedDrawer;
    private View containerView;
    private boolean mFromSavedInsatanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static void saveToPreferemces(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.commit();
    }

    public static String readFromPreferemces(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferemces(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInsatanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        qr_scanner = (TextView) view.findViewById(R.id.QR_SCANNER);
        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QrScanner.class);
                startActivity(intent);
            }
        });
        login = (TextView) view.findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
            }
        });

        drawer_user_profile = (RelativeLayout) view.findViewById(R.id.drawer_user_profile);
        txtImgName = (TextView) view.findViewById(R.id.txtImgName);
        txtCustEmail = (TextView) view.findViewById(R.id.txtCustEmail);
        txtCustName = (TextView) view.findViewById(R.id.txtCustName);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String cust_user_id = prefs.getString("UserID", "");
        String cust_last_name = prefs.getString("UserLastName", "");
        String cust_first_name = prefs.getString("UserFirstName", "");
        String cust_email = prefs.getString("Email", "");
        Log.i("restoredText UserID", cust_user_id);

        if (cust_user_id.equals("")) {
            drawer_user_profile.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.GONE);
            drawer_user_profile.setVisibility(View.VISIBLE);
            txtImgName.setText((cust_first_name != null && !cust_first_name.isEmpty()) ? String.valueOf(cust_first_name.charAt(0)) : "");
            txtCustEmail.setText((cust_email != null && !cust_email.isEmpty()) ? String.valueOf(cust_email) : "");
            txtCustName.setText((cust_first_name != null && cust_last_name != null) ? String.valueOf(cust_first_name + " " + cust_last_name) : "");
        }

        newCategory_list = (RecyclerView) view.findViewById(R.id.newCategory_list);
        newCategory_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        newCategory_list.setNestedScrollingEnabled(false);

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

        return view;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferemces(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInsatanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void calculate() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME);

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
                category.setCategory(pii.getProperty("CategoryDesc").toString());
                category.setCategoryID(pii.getProperty("CategoryID").toString());

                categories.add(category.copy());
            }

        } catch (Exception exception) {
            Log.i(TAG, exception.toString());
        }
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(true);
            dialog.setMessage("Loading...");
            dialog.show();
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categories);
            Log.i("categories screen s", String.valueOf(categories.size()));

            newCategory_list.setAdapter(categoryAdapter);
            dialog.cancel();
            Log.i(TAG, "onPostExecute");
        }

    }


}
