package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.TrangThai;
import com.example.myapplication.R;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuaNgayActivity extends AppCompatActivity {
EditText tvCusTel,tvCusEmail,tvCusAddress,edGiamGia;
TextView tvCusName,tvTenSp,tvGia,tvValue,tvMaGiamGia, tvTongTienMua;
SanPham sanPham;
ImageView imgHinh;
Button btnLess, btnMore, btnKiemTra,btnThanhToan;
int soLuongHT =1 ;
String strGiamGia;
int value, tongTien;
String khuyenMai = "Không giảm giá" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_shopping);
        anhXa();
        DataIntent();

        tvCusName.setText(MainActivity.prefConfig.readCusName());
        tvCusTel.setText(MainActivity.prefConfig.readCusTel());
        tvCusEmail.setText(MainActivity.prefConfig.readCusEmail());
        tvCusAddress.setText(MainActivity.prefConfig.readCusAddress());
        strGiamGia = edGiamGia.getText().toString();

        value = Integer.valueOf(sanPham.getPrice());
        tongTien = value;
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        String format = String.valueOf(decimalFormat.format(tongTien));
        tvTongTienMua.setText(format+" đ");

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
                value = Integer.valueOf(sanPham.getPrice());
                tongTien = value*soLuongHT;
                DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                String format = String.valueOf(decimalFormat.format(tongTien));
                tvTongTienMua.setText(format+" đ");
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
                value = Integer.valueOf(sanPham.getPrice());
                tongTien = value*soLuongHT;
                DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                String format = String.valueOf(decimalFormat.format(tongTien));
                tvTongTienMua.setText(format+" đ");
            }
        });

        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCodeSale();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tongTien==0){
                    Toast.makeText(MuaNgayActivity.this, "Bạn phải kiểm tra trước", Toast.LENGTH_SHORT).show();
                }else {
                    String strEmail = tvCusEmail.getText().toString();
                    String strTel = tvCusTel.getText().toString();
                    String strAddress = tvCusAddress.getText().toString();
                    //Toast.makeText(getActivity(), "Số tiền là"+tongTien, Toast.LENGTH_SHORT).show();
                    Call<TrangThai> callback = MainActivity.dataservice.GetShoppingNow(
                            MainActivity.prefConfig.readCustomerId(),strEmail,strTel,strAddress, String.valueOf(tongTien),khuyenMai,sanPham.getProductId(),soLuongHT);
                    final SpotsDialog progressDoalog;
                    progressDoalog = new SpotsDialog(MuaNgayActivity.this,R.style.CustomPD);
                    progressDoalog.show();
                    callback.enqueue(new Callback<TrangThai>() {
                        @Override
                        public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                            if (response.body().getResponse().equals("Oke")){
                                progressDoalog.dismiss();
                                FancyToast.makeText(MuaNgayActivity.this,"Đặt hàng thành công",
                                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                Intent intent = new Intent(MuaNgayActivity.this,ChiTietSanPhamActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }else if (response.body().getResponse().equals("failed")){
                                Toast.makeText(MuaNgayActivity.this, "Có lỗi nào đó", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<TrangThai> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    public  void DataIntent(){
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("muaSanPham")){
                sanPham = (SanPham) intent.getSerializableExtra("muaSanPham");
                tvTenSp.setText(sanPham.getProductName());
                tvMaGiamGia.setText(sanPham.getCodeSale());
                Picasso.with(this).load(sanPham.getImage()).into(imgHinh);
                int value = Integer.valueOf(sanPham.getPrice());
                DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                String format = String.valueOf(decimalFormat.format(value));
                tvGia.setText(format+" đ");
            }
        }

    }
    public  void anhXa(){
        tvCusName = findViewById(R.id.tvCusName);
        tvCusAddress = findViewById(R.id.tvCusAddress);
        tvCusEmail = findViewById(R.id.tvCusEmail);
        tvCusTel = findViewById(R.id.tvCusTel);
        tvTenSp = findViewById(R.id.tvTen);
        imgHinh = findViewById(R.id.imgHinh);
        tvGia = findViewById(R.id.tvGia);
        tvValue = findViewById(R.id.tvValue);
        tvMaGiamGia = findViewById(R.id.tvGiamGia);
        btnLess = findViewById(R.id.btnLess);
        btnMore = findViewById(R.id.btnMore);
        btnKiemTra = findViewById(R.id.btnKiemTraMua);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        edGiamGia = findViewById(R.id.edCheckGiamGia);
        tvTongTienMua = findViewById(R.id.TongTienMua);
    }
    public void checkCodeSale(){
        Call<TrangThai> callback = MainActivity.dataservice.CheckCodeSale(edGiamGia.getText().toString(),sanPham.getProductId());
        callback.enqueue(new Callback<TrangThai>() {
            @Override
            public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                if (response.body().getResponse().equals("failed")){
                    Toast.makeText(MuaNgayActivity.this, "Không tồn tại", Toast.LENGTH_SHORT).show();
                    value = Integer.valueOf(sanPham.getPrice());
                    tongTien = value*soLuongHT;
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                    String format = String.valueOf(decimalFormat.format(tongTien));
                    tvTongTienMua.setText(format+" đ");

                }else if (response.body().getResponse().equals("Oke")){
                    Toast.makeText(MuaNgayActivity.this, "Mã hợp lệ", Toast.LENGTH_SHORT).show();
                    value = Integer.valueOf(sanPham.getPrice());
                    tongTien = (value-(value/10))*soLuongHT;
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                    String format = String.valueOf(decimalFormat.format(tongTien));
                    tvTongTienMua.setText(format+" đ");
                    khuyenMai = "Giảm 10%";

                }
            }

            @Override
            public void onFailure(Call<TrangThai> call, Throwable t) {

            }
        });
    }

}
