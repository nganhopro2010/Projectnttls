package com.example.myapplication.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomThemGioHangFragment extends BottomSheetDialogFragment {
Button btnThem;
TextView tvValue,tvNamePd;
ImageView imgPd;
Button btnMore,btnLess;
int soLuongHT =1 ;
private BottomSheetListener bottomSheetListener;

    public BottomThemGioHangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        btnThem = view.findViewById(R.id.btnThem);
        btnMore = view.findViewById(R.id.btnMore);
        tvValue = view.findViewById(R.id.tvValue);
        btnLess = view.findViewById(R.id.btnLess);
        imgPd = view.findViewById(R.id.imgPd);
        tvNamePd = view.findViewById(R.id.tvNamePd);
        tvNamePd.setText(ChiTietSanPhamActivity.sanPham.getProductName());
        Picasso.with(getActivity()).load(ChiTietSanPhamActivity.sanPham.getImage()).into(imgPd);
        soLuongHT = Integer.parseInt(tvValue.getText().toString());

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuongHT = soLuongHT +1;
                tvValue.setText(String.valueOf(soLuongHT));
                if (soLuongHT>=10){
                    btnMore.setEnabled(false);
                }else if (soLuongHT < 10){
                    btnMore.setEnabled(true);
                    btnLess.setEnabled(true);
                }
            }
        });
        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuongHT = soLuongHT -1;
                tvValue.setText(String.valueOf(soLuongHT));
                if (soLuongHT<=1){
                    btnLess.setEnabled(false);
                }else if (soLuongHT >= 1){
                    btnLess.setEnabled(true);
                    btnMore.setEnabled(true);
                }
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostVaoGio(ChiTietSanPhamActivity.sanPham.getProductId(), MainActivity.prefConfig.readCustomerId(),soLuongHT);

            }
        });

        return view;
    }
    public  interface BottomSheetListener{
        void onOptionClick(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString());
        }
    }

    private void PostVaoGio(String productId, String customerId, int soLuong) {
        Dataservice dataservice = APIService.getService();
        Call<GioHang> call = dataservice.ThemVaoGio(productId,customerId,soLuong);
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPDDARK);
        progressDoalog.show();
        call.enqueue(new Callback<GioHang>() {
            @Override
            public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                if (response.body().getResponse().equals("Oke")){
                    progressDoalog.dismiss();
                    FancyToast.makeText(getActivity(),"Đã thêm",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                }else if(response.body().getResponse().equals("exist")){
                    progressDoalog.dismiss();
                    FancyToast.makeText(getActivity(),"Đã cập nhật số lượng",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }
            }
            @Override
            public void onFailure(Call<GioHang> call, Throwable t) {

            }
        });
    }
}
