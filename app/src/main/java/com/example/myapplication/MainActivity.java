package com.example.myapplication;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.Adapter.PrefConfig;
import com.example.myapplication.Fragment.DanhMucFragment;
import com.example.myapplication.Fragment.GioHangFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.RegisterFragment;
import com.example.myapplication.Fragment.TaiKhoangFragment;
import com.example.myapplication.Fragment.YeuThichFragment;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, TaiKhoangFragment.OnLogoutListerner {
    public static PrefConfig prefConfig;
    public static Dataservice dataservice;
    Toolbar toolbar;
    Fragment selectedFragment = null;
    BottomNavigationView bottomView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomView = findViewById(R.id.bottom_nav);
        bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomView.setOnNavigationItemSelectedListener(navListener);
        prefConfig = new PrefConfig(this);
        dataservice = APIService.getService();

        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null){
                return;
            }
            if (prefConfig.readLoginStatus()){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                bottomView.setVisibility(View.VISIBLE);
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                bottomView.setVisibility(View.INVISIBLE);

            }
        }
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaiKhoangFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_dao:
                    selectedFragment = new DanhMucFragment();
                    break;
                case R.id.nav_tinnhan:
                    selectedFragment = new YeuThichFragment();
                    break;
                case R.id.nav_giohang:
                    selectedFragment = new GioHangFragment();
                    break;
                case R.id.nav_taikhoan:
                    selectedFragment = new TaiKhoangFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;

        }
    };
    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RegisterFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performLogin(String CustomerId, String CusName, String CusEmail, String CusTel, String CusPass, String CusBirthday, String CusAddress) {
        prefConfig.writeCustomerId(CustomerId, CusName,CusEmail,CusTel,CusPass,CusBirthday,CusAddress);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        bottomView.setVisibility(View.VISIBLE);
    }


    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeCustomerId("CustomerId","CusName","CusEmail","CusTel","CusPass","CusBirthday","CusAddress");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();
        bottomView.setVisibility(View.INVISIBLE);

    }


}
