package com.example.myapplication.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.TrangThai;
import com.example.myapplication.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetThongTinNguoiDung extends BottomSheetDialogFragment {
    EditText tvEmail,tvCusTel,tvCusPass,tvCusBirthday,tvCusAddress;
    LinearLayout linearlayoutCapNhat;
    Snackbar snackbar;
    OnLogoutListerner onLogoutListerner;
    public  interface  OnLogoutListerner{
        public  void logoutPerformed();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottom_sheet_thong_tin_nguoi_dung, container, false);

        tvEmail = v.findViewById(R.id.tvCusEmailBottom);
        tvCusTel = v.findViewById(R.id.tvCusTelBottom);
        tvCusAddress = v.findViewById(R.id.tvCusAddressBottom);
        tvCusBirthday = v.findViewById(R.id.tvCusBirthdayBottom);
        tvCusPass = v.findViewById(R.id.tvCusPassBottom);
        linearlayoutCapNhat = v.findViewById(R.id.linearlayoutCapNhat);

        tvEmail.setText(MainActivity.prefConfig.readCusEmail());
        tvCusPass.setText(MainActivity.prefConfig.readCusPass());
        tvCusBirthday.setText(MainActivity.prefConfig.readCusBirthday());
        tvCusTel.setText(MainActivity.prefConfig.readCusTel());
        tvCusAddress.setText(MainActivity.prefConfig.readCusAddress());

        linearlayoutCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhat();
            }
        });

        return v;

    }
    public void capNhat(){
        String strBirth = tvCusBirthday.getText().toString();
        String strTel = tvCusTel.getText().toString();
        String strPass = tvCusPass.getText().toString();
        String strAddress = tvCusAddress.getText().toString();

        Call<TrangThai> callback = MainActivity.dataservice.CapNhatThongTin(MainActivity.prefConfig.readCustomerId(),strTel,strPass,strAddress,strBirth);
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        progressDoalog.show();

        callback.enqueue(new Callback<TrangThai>() {
            @Override
            public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                    if (response.body().getResponse().equals("Oke")){
                        progressDoalog.dismiss();
                        FancyToast.makeText(getActivity(),"Cập nhật thành công. Đăng xuất sẽ lưu lại thông tin",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                    }
            }
            @Override
            public void onFailure(Call<TrangThai> call, Throwable t) {

            }
        });
    }


}
