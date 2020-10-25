package com.example.myapplication.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.Customer;
import com.example.myapplication.R;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Toolbar toolbar;
    TextView tvSignUp;
    EditText edEmail,edPass;
    String Email,Pass;
    Button btnlogin;
    LinearLayout linearLayoutLogin;
    ImageView imgLogo;
    Animation animation;
    TextInputLayout tvIpPass;
    TextInputLayout tvIpEmail;
    OnLoginFormActivityListener loginFormActivityListener;

    public LoginFragment() {
        // Required empty public constructor
    }
    public interface OnLoginFormActivityListener{
        public  void performRegister();
        public  void performLogin(String CustomerId,String CusName,String CusEmail,String CusTel,String CusPass,String CusBirthday,String CusAddress);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        tvSignUp = view.findViewById(R.id.link_signup);
        edEmail = view.findViewById(R.id.input_email);
        edPass = view.findViewById(R.id.input_password);
        btnlogin = view.findViewById(R.id.btnlogin);
        tvIpPass = view.findViewById(R.id.tvIpPass);


        tvIpEmail = view.findViewById(R.id.tvIpEmail);
        imgLogo = view.findViewById(R.id.imgLogo);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginFormActivityListener.performRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

private  void performLogin(){
    Email  = edEmail.getText().toString();
    Pass = edPass.getText().toString();
    Call<Customer> callback = MainActivity.dataservice.performUserLogin(Email,Pass);
    final SpotsDialog progressDoalog;
    progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
    progressDoalog.show();
    callback.enqueue(new Callback<Customer>() {
        @Override
        public void onResponse(Call<Customer> call, Response<Customer> response) {
            if (Email.isEmpty()){
                tvIpEmail.setErrorEnabled(false);
                tvIpEmail.setError("Chưa Nhập Email");
                progressDoalog.dismiss();
            }
            else if (Pass.isEmpty()){
                tvIpPass.setErrorEnabled(false);
                tvIpPass.setError("Chưa Nhập Pass");
                progressDoalog.dismiss();
            }else {
            if (response.body().getResponse().equals("Oke")){
                MainActivity.prefConfig.writeLoginStatus(true);
                loginFormActivityListener.performLogin(response.body().getCustomerId(),
                        response.body().getCusName(),
                        response.body().getCusEmail(),
                        response.body().getCusTel(),
                        response.body().getCusPass(),
                        response.body().getCusBirthday(),
                        response.body().getCusAddress()
                        );
                progressDoalog.dismiss();

            }else if (response.body().getResponse().equals("failed")){
                MainActivity.prefConfig.displayToast("Tên đăng nhập hoặc mật khẩu sai");
                edEmail.setText("");
                edPass.setText("");
                progressDoalog.dismiss();
            }
            }
        }

        @Override
        public void onFailure(Call<Customer> call, Throwable t) {

        }
    });
}

}
