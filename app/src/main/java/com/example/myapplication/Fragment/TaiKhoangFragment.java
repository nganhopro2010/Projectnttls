package com.example.myapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class TaiKhoangFragment extends Fragment {
    Button btnEvent;
    TextView tvCusName,tv_nhandonhang_click, tv_vanchuyen_click,tv_huydonhang_click,tv_thanhcong_click;
    ImageView img_show_info_click;
    OnLogoutListerner logoutListerner;
    public  interface  OnLogoutListerner{
        public  void logoutPerformed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);
        btnEvent = view.findViewById(R.id.btnEvent);
        tvCusName = view.findViewById(R.id.tvCusName);

        tv_nhandonhang_click = view.findViewById(R.id.tv_nhandonhang_click);
        tv_nhandonhang_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetNhanDonHang bottomDialog = new BottomSheetNhanDonHang();
                bottomDialog.show(getFragmentManager(), "BottomSheetNhanDonHang");
            }
        });

        tv_vanchuyen_click = view.findViewById(R.id.tv_vanchuyen_click);
        tv_vanchuyen_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetHangVanChuyen bottomVanChuyen = new BottomSheetHangVanChuyen();
                bottomVanChuyen.show(getFragmentManager(), "BottomSheetHangVanChuyen");
            }
        });

        tv_huydonhang_click = view.findViewById(R.id.tv_huydonhang_click);
        tv_huydonhang_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetHangDaHuy bottomHangDaHuy = new BottomSheetHangDaHuy();
                bottomHangDaHuy.show(getFragmentManager(), "BottomSheetHangDaHuy");
            }
        });

        tv_thanhcong_click = view.findViewById(R.id.tv_thanhcong_click);
        tv_thanhcong_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDonHangThanhCong bottomHangThanhCong = new BottomSheetDonHangThanhCong();
                bottomHangThanhCong.show(getFragmentManager(), "BottomSheetHangThanhCong");
            }
        });

        img_show_info_click = view.findViewById(R.id.img_show_info_click);
        img_show_info_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetThongTinNguoiDung bottomThongTinNguoiDung = new BottomSheetThongTinNguoiDung();
                bottomThongTinNguoiDung.show(getFragmentManager(), "BottomSheetThongTinNguoiDung");
            }
        });

        tvCusName.setText(MainActivity.prefConfig.readCusName());

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutListerner.logoutPerformed();
            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        logoutListerner = (OnLogoutListerner) activity;
    }
}
