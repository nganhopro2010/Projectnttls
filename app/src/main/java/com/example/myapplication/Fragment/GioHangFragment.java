package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class GioHangFragment extends Fragment {
    RecyclerView lvGioHang;
    Snackbar snackbar;
    SanPhamGioHangAdapter sanPhamGioHangAdapter;
    ArrayList<SanPham>sanPhams;
    Button btnThanhToan;
    TextView tvTongTien;
    LinearLayout linearLayoutWarm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        lvGioHang = view.findViewById(R.id.recyclerviewGioHang);
        tvTongTien = view.findViewById(R.id.TongTien);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);
        linearLayoutWarm = view.findViewById(R.id.linearlayoutWarm);
        getTongTien();
        GetGioHang(MainActivity.prefConfig.readCustomerId());
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sanPhams.size()>0){
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThanhToanFragment()).commit();
                }else {
                    snackbar = Snackbar.make(getView(),"Bạn chưa có sản phẩm trong giỏ hàng",Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    snackbarView.setBackgroundColor(Color.parseColor("#B80C0C"));
                    snackbar.show();
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
        // show it
        progressDoalog.show();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
            sanPhams = (ArrayList<SanPham>) response.body();
            sanPhamGioHangAdapter = new SanPhamGioHangAdapter(getActivity(),sanPhams);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                lvGioHang.setLayoutManager(linearLayoutManager);
                lvGioHang.setAdapter(sanPhamGioHangAdapter);
                if (sanPhams.size() !=0){
                    linearLayoutWarm.setVisibility(View.INVISIBLE);

                }else {
                    linearLayoutWarm.setVisibility(View.VISIBLE);
                }
                progressDoalog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

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
                        int value = Integer.valueOf(response.body().getTongTien());
                        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                        String format = String.valueOf(decimalFormat.format(value));
                        tvTongTien.setText(format+" đ");
                    }
                }catch (Exception ex){
                    tvTongTien.setText("0 đ");
                }
            }

            @Override
            public void onFailure(Call<TrangThai> call, Throwable t) {

            }
        });
    }

//=================================================================================================================
    public class SanPhamGioHangAdapter extends RecyclerView.Adapter<SanPhamGioHangAdapter.ViewHolder>  {
        Context context;
        ArrayList<SanPham> sanPhamArrayList;
        public SanPhamGioHangAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
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
                                Call<String> call = dataservice.XoaGioHang(sanPhamArrayList.get(getPosition()).getProductId(), MainActivity.prefConfig.readCustomerId());

                                final SpotsDialog progressDoalog;
                                progressDoalog = new SpotsDialog(context,R.style.CustomPD);
                                // show it
                                progressDoalog.show();
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String ketqua = response.body();
                                        if (ketqua.equals("YES")){
                                            FancyToast.makeText(context,"Đã xóa khỏi giỏ hàng",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new GioHangFragment()).commit();
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


