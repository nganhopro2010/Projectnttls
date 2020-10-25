package com.example.myapplication.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.Customer;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    TextView tvLogin;
    EditText edName,edNgaySinh,edEmail,edTel,edPass,edRepass,edAddress;
    Button btn_signup;

    final Calendar myCalendar = Calendar.getInstance();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        btn_signup = view.findViewById(R.id.btn_signup);
        tvLogin = view.findViewById(R.id.link_login);
        edName = view.findViewById(R.id.input_name);
        edNgaySinh = view.findViewById(R.id.birthday);
        edEmail = view.findViewById(R.id.input_email);
        edPass = view.findViewById(R.id.input_password);
        edRepass = view.findViewById(R.id.input_repassword);
        edTel = view.findViewById(R.id.tel);
        edAddress = view.findViewById(R.id.address);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPassWord = edPass.getText().toString();
                String strRePass = edRepass.getText().toString();
                if (ValidateEmail()<0){
                    Toast.makeText(getContext(),"Email Không Hợp lệ", Toast.LENGTH_SHORT).show();
                }else if (!strPassWord.equals(strRePass)){
                    Toast.makeText(getActivity(), "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }else {
                    performRegistration();
                }

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, date);
                updateLabel();
            }
        };
        edNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR)
                        , myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (edTel.getText().toString().length()<10){
                        Toast.makeText(getContext(),"Số điện thoại không đúng định dạng",Toast.LENGTH_SHORT).show();
                    }else {
                        edTel.setError(null);
                    }
                }else{
                    if (edTel.getText().toString().length() < 10){
                        Toast.makeText(getContext(),"Số điện thoại không đúng định dạng",Toast.LENGTH_SHORT).show();
                    }else {
                        edTel.setError(null);
                    }
                }

            }
        });


        return view;

    }
    public int ValidateEmail(){
        int check = 1;
        final String email = edEmail.getText().toString();
        final String emailParent = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailParent)){
            check =-1;
        }else{
            check=1;
        }
        return check;
    }
    public  void performRegistration(){
        String strEmail = edEmail.getText().toString();
        String strName = edName.getText().toString();
        String strNgaySinh = edNgaySinh.getText().toString();
        String strTel = edTel.getText().toString();
        String strPass = edPass.getText().toString();
        String strAddress = edAddress.getText().toString();
        Call<Customer> callback = MainActivity.dataservice.performRegistration
                (strName,strEmail,strTel,strPass,strNgaySinh,strAddress);
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        progressDoalog.show();
        callback.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.body().getResponse().equals("Oke")){
                    MainActivity.prefConfig.displayToast("Đăng ký thành công");
                    edEmail.setText("");
                    edName.setText("");
                    edNgaySinh.setText("");
                    edPass.setText("");
                    edRepass.setText("");
                    edTel.setText("");
                    edAddress.setText("");
                    progressDoalog.dismiss();
                }
                else if(response.body().getResponse().equals("exist")){
                    MainActivity.prefConfig.displayToast("Email đã tồn tại");

                }
                else if(response.body().getResponse().equals("error")){
                    MainActivity.prefConfig.displayToast("Có lỗi gì rồi...");
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edNgaySinh.setText(sdf.format(myCalendar.getTime()));
    }
}
