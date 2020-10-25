package com.example.myapplication.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.TrangThai;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThanhToanFragment extends Fragment {
    EditText edCodeSale,tvCusTel,tvCusEmail,tvCusAddress;
    TextView tvCusName,tvTongTienMua;
    Button btnKiemTra, btnThanhToan;
    RecyclerView recyclerViewCheck;
    LinearLayout linearLayoutGiamGia;
    CheckSanPhamAdapter checkSanPhamAdapter;
    ArrayList<SanPham> sanPhams;
    Snackbar snackbar;
    int tongTien;
    int value;
    String khuyenMai = "Không giảm giá" ;


    public ThanhToanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_thanh_toan, container, false);
        tvCusName = view.findViewById(R.id.tvCusName);
        tvCusAddress = view.findViewById(R.id.tvCusAddress);
        tvCusEmail = view.findViewById(R.id.tvCusEmail);
        tvCusTel = view.findViewById(R.id.tvCusTel);
        edCodeSale = view.findViewById(R.id.edCodeSale);
        btnKiemTra = view.findViewById(R.id.btnKiemTra);
        tvTongTienMua = view.findViewById(R.id.TongTienMua);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);
        linearLayoutGiamGia = view.findViewById(R.id.linearlayoutGiamGia);
        recyclerViewCheck = view.findViewById(R.id.recyclerviewCheck);
        GetGioHang(MainActivity.prefConfig.readCustomerId());
        tvCusName.setText(MainActivity.prefConfig.readCusName());
        tvCusTel.setText(MainActivity.prefConfig.readCusTel());
        tvCusEmail.setText(MainActivity.prefConfig.readCusEmail());
        tvCusAddress.setText(MainActivity.prefConfig.readCusAddress());
        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMaGiamGia();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tongTien==0){
                    Toast.makeText(getActivity(), "Bạn phải kiểm tra trước",
                            Toast.LENGTH_SHORT).show();
                }else {

                    String strEmail = tvCusEmail.getText().toString();
                    String strTel = tvCusTel.getText().toString();
                    String strAddress = tvCusAddress.getText().toString();
                    //Toast.makeText(getActivity(), "Số tiền là"+tongTien, Toast.LENGTH_SHORT).show();
                    Call<TrangThai> callback = MainActivity.dataservice.GetShopping(
                            MainActivity.prefConfig.readCustomerId(),strEmail,strTel,strAddress, String.valueOf(tongTien),khuyenMai);
                    final SpotsDialog progressDoalog;
                    progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
                    progressDoalog.show();
                    callback.enqueue(new Callback<TrangThai>() {
                        @Override
                        public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                            if (response.body().getResponse().equals("Oke")){
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new GioHangFragment()).commit();
                                progressDoalog.dismiss();
                                FancyToast.makeText(getActivity(),"Đặt hàng thành công",
                                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            }else if (response.body().getResponse().equals("failed")){
                                Toast.makeText(getActivity(), "Có lỗi nào đó", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<TrangThai> call, Throwable t) {

                        }
                    });

                }
            }
        });
        return view;
    }

    public void GetGioHang(String customerId){
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetGioHang(customerId);
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        progressDoalog.show();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhams = (ArrayList<SanPham>) response.body();
                checkSanPhamAdapter = new CheckSanPhamAdapter(getActivity(),sanPhams);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewCheck.setLayoutManager(linearLayoutManager);
                recyclerViewCheck.setAdapter(checkSanPhamAdapter);
                progressDoalog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
    public void getMaGiamGia(){
        Call<TrangThai> callback = MainActivity.dataservice.GetMaGiamGia(
                MainActivity.prefConfig.readCustomerId(),edCodeSale.getText().toString());
        callback.enqueue(new Callback<TrangThai>() {
            @Override
            public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                try {
                    if (response.body().getResponse().equals("failed")){
                        linearLayoutGiamGia.setVisibility(View.INVISIBLE);
                        snackbar = Snackbar.make(getView(),"Mã không hợp lệ",Snackbar.LENGTH_SHORT);
                        getTongTien();
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.WHITE);
                        snackbarView.setBackgroundColor(Color.parseColor("#B80C0C"));
                        snackbar.show();
                    }else if (response.body().getResponse().equals("Oke")){
                        linearLayoutGiamGia.setVisibility(View.VISIBLE);
                        Call<TrangThai> callback = MainActivity.dataservice.GetTongTien(MainActivity.prefConfig.readCustomerId());
                        callback.enqueue(new Callback<TrangThai>() {
                            @Override
                            public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                                try {
                                    if (response.body().getResponse().equals("Oke")){
                                        value = Integer.valueOf(response.body().getTongTien());
                                        tongTien = value-(value/10);
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                                        String format = String.valueOf(decimalFormat.format(tongTien));
                                        tvTongTienMua.setText(format+" đ");
                                        khuyenMai = "Giảm 10%";
                                    }
                                }catch (Exception ex){
                                    tvTongTienMua.setText("0 đ");
                                }
                            }

                            @Override
                            public void onFailure(Call<TrangThai> call, Throwable t) {

                            }
                        });
                    }
                }catch (Exception ex){

                }
            }

            @Override
            public void onFailure(Call<TrangThai> call, Throwable t) {

            }
        });
    }

    public void getTongTien(){
        Call<TrangThai> callback = MainActivity.dataservice.GetTongTien(MainActivity.prefConfig.readCustomerId());
        callback.enqueue(new Callback<TrangThai>() {
            @Override
            public void onResponse(Call<TrangThai> call, Response<TrangThai> response) {
                try {
                    if (response.body().getResponse().equals("Oke")){
                        value = Integer.valueOf(response.body().getTongTien());
                        tongTien = value;
                        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                        String format = String.valueOf(decimalFormat.format(tongTien));
                        tvTongTienMua.setText(format+" đ");
                    }
                }catch (Exception ex){
                    tvTongTienMua.setText("0 đ");
                }
            }

            @Override
            public void onFailure(Call<TrangThai> call, Throwable t) {

            }
        });
    }

    public class CheckSanPhamAdapter extends RecyclerView.Adapter<CheckSanPhamAdapter.ViewHolder>  {
        Context context;
        ArrayList<SanPham> sanPhamArrayList;
        public CheckSanPhamAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
            this.context = context;
            this.sanPhamArrayList = sanPhamArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_gio_hang,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            SanPham sanPham = sanPhamArrayList.get(i);
            Picasso.with(context).load(sanPham.getImage()).into(viewHolder.imgSanPham);
            viewHolder.tvTenSanPham.setText(sanPham.getProductName());
            viewHolder.tvCodeSale.setText(sanPham.getCodeSale());
            viewHolder.tvSoLuong.setText(sanPham.getSoLuong());
            int value = Integer.valueOf(sanPham.getPrice());
            DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
            String format = String.valueOf(decimalFormat.format(value));
            viewHolder.tvGiaSanPham.setText(format+" đ");
        }
        @Override
        public int getItemCount() {
            return sanPhamArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imgSanPham,imgXoa;
            TextView tvTenSanPham;
            TextView tvGiaSanPham, tvCodeSale,tvSoLuong;
            public ViewHolder(@NonNull final View itemView) {
                super(itemView);
                imgSanPham = itemView.findViewById(R.id.imgHinh);
                tvTenSanPham = itemView.findViewById(R.id.tvTen);
                tvGiaSanPham = itemView.findViewById(R.id.tvGia);
                imgXoa = itemView.findViewById(R.id.deleteCart);
                tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
                tvCodeSale = itemView.findViewById(R.id.tvGiamGia);
                imgXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Xóa sản phẩm khỏi giỏ hàng");
                        builder.setMessage("Bạn có chắc không?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                Dataservice dataservice = APIService.getService();
                                Call<String> call = dataservice.XoaGioHang(
                                        sanPhamArrayList.get(getPosition()).getProductId(),
                                        MainActivity.prefConfig.readCustomerId());
                                final SpotsDialog progressDoalog;
                                progressDoalog = new SpotsDialog(context,R.style.CustomPD);
                                // show it
                                progressDoalog.show();
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua = response.body();
                                        if (ketqua.equals("YES")){
                                            FancyToast.makeText(context,"Đã xóa khỏi giỏ hàng",
                                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                            getFragmentManager().beginTransaction()
                                                    .replace(R.id.fragment_container, new ThanhToanFragment()).commit();
                                            progressDoalog.dismiss();
                                        }else{
                                            Toast.makeText(context, "Bi Lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        progressDoalog.dismiss();
                                    }
                                });
                            }

                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                imgSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                        intent.putExtra("chitietspdm",sanPhamArrayList.get(getPosition()));
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

}
