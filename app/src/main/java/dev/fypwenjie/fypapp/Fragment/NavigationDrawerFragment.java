package dev.fypwenjie.fypapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.fypwenjie.fypapp.Activities.LoginScreen;
import dev.fypwenjie.fypapp.Activities.MainActivity;
import dev.fypwenjie.fypapp.Activities.OrderScreen;
import dev.fypwenjie.fypapp.Activities.QrScanner;
import dev.fypwenjie.fypapp.DatabaseHelper;
import dev.fypwenjie.fypapp.Domain.Account;
import dev.fypwenjie.fypapp.Domain.Cart;
import dev.fypwenjie.fypapp.Domain.Store;
import dev.fypwenjie.fypapp.R;

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
    ArrayList<Store> categories = new ArrayList<Store>();
    Store store = new Store();
    ProgressDialog dialog;
    String TAG = "Response";
    DatabaseHelper databaseHelper;
    RecyclerView newCategory_list;
    TextView qr_scanner, login,logout, txt_cart_qty,txtImgName, txtCustName, txtCustEmail, Cart;
    ArrayList<Account> account = new ArrayList<Account>();
    ArrayList<Cart> cart = new ArrayList<Cart>();
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout drawer_user_profile;
    private LinearLayout logout_layout;
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
        logout = (TextView) view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.logout();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        Cart = (TextView) view.findViewById(R.id.Cart);
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderScreen.class);
                startActivity(intent);
            }
        });

        drawer_user_profile = (RelativeLayout) view.findViewById(R.id.drawer_user_profile);
        logout_layout = (LinearLayout) view.findViewById(R.id.logout_layout);
        txtCustEmail = (TextView) view.findViewById(R.id.txtCustEmail);
        txtCustName = (TextView) view.findViewById(R.id.txtCustName);
        databaseHelper = new DatabaseHelper(getActivity());
        txt_cart_qty = (TextView) view.findViewById(R.id.cart_qty);

        cart = databaseHelper.getCarts();

        if(cart.size() > 0){
            txt_cart_qty.setText(String.valueOf( cart.size()));
        }else {
            txt_cart_qty.setText("");
        }

        account = databaseHelper.getAccount();

        if(account.size()> 0){
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            drawer_user_profile.setVisibility(View.VISIBLE);
            String name = databaseHelper.getUsername();
            String email = databaseHelper.getCustEmail();

            txtImgName.setText((name != null && !name.isEmpty()) ? String.valueOf(name.charAt(0)) : "");
            txtCustEmail.setText((email != null && !email.isEmpty()) ? String.valueOf(email) : "");
            txtCustName.setText((name != null) ? String.valueOf(name) : "");
        }else {
            drawer_user_profile.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }





        /*    login.setVisibility(View.GONE);
            logout_layout.setVisibility(View.VISIBLE);
            drawer_user_profile.setVisibility(View.VISIBLE);
            txtCustEmail.setText("testing@gmail.com");
            txtCustName.setText("Testing boy");
*/

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


}
