package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.Model.ChiTietSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSanPhamAdapter extends RecyclerView.Adapter<SearchSanPhamAdapter.ViewHolder>{
   Context context;
   ArrayList<SanPham> sanPhamArrayList;

    public SearchSanPhamAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sanpham, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    SanPham sanPham = sanPhamArrayList.get(i);
    viewHolder.tvTen.setText(sanPham.getProductName());
    Picasso.with(context).load(sanPham.getImage()).into(viewHolder.imgSanPham);
        int value = Integer.valueOf(sanPham.getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        String format = String.valueOf(decimalFormat.format(value));
        viewHolder.tvGia.setText(format+" đ");

    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTen,tvGia;
        ImageView imgSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenSp);
            tvGia = itemView.findViewById(R.id.tvGiaSp);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("chitietspdm", sanPhamArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
