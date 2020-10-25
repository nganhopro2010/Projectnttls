package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Model.ChiTietSanPham;
import com.example.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoTaSanPhamAdapter extends RecyclerView.Adapter<MoTaSanPhamAdapter.ViewHolder> {
    Context context;
    ArrayList<ChiTietSanPham> mangChiTiestSanPham;

    public MoTaSanPhamAdapter(Context context, ArrayList<ChiTietSanPham> mangChiTiestSanPham) {
        this.context = context;
        this.mangChiTiestSanPham = mangChiTiestSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_mota_sanpham,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChiTietSanPham chiTietSanPham = mangChiTiestSanPham.get(i);
        viewHolder.tvMota.setText(chiTietSanPham.getProductDescDetail());
    }

    @Override
    public int getItemCount() {
        return mangChiTiestSanPham.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvMota;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMota = itemView.findViewById(R.id.tvMoTa);

        }
    }
}
