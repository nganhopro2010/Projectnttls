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

public class ChiTietSanPhamAdapter extends RecyclerView.Adapter<ChiTietSanPhamAdapter.ViewHolder> {
    Context context;
    ArrayList<ChiTietSanPham> mangChiTiestSanPham;

    public ChiTietSanPhamAdapter(Context context, ArrayList<ChiTietSanPham> mangChiTiestSanPham) {
        this.context = context;
        this.mangChiTiestSanPham = mangChiTiestSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_chitiet_sanpham,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChiTietSanPham chiTietSanPham = mangChiTiestSanPham.get(i);
        viewHolder.tvTieuDe.setText(chiTietSanPham.getProductDesc());
        viewHolder.tvNhaCungCap.setText(chiTietSanPham.getAdminName());
        viewHolder.tvThuongHieu.setText(chiTietSanPham.getBrandName());
        if (viewHolder.tvCodeSale.equals("")){
            viewHolder.tvCodeSale.setText("Không áp dụng");
        }else {
            viewHolder.tvCodeSale.setText(chiTietSanPham.getCodeSale());

        }
        int value = Integer.valueOf(chiTietSanPham.getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        String format = String.valueOf(decimalFormat.format(value));
        viewHolder.tvGia.setText(format+" đ");
    }

    @Override
    public int getItemCount() {
        return mangChiTiestSanPham.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvGia, tvTieuDe, tvThuongHieu, tvNhaCungCap,tvCodeSale;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvGia = itemView.findViewById(R.id.tvGiaSanPham);
            tvThuongHieu = itemView.findViewById(R.id.tvThuongHieu);
            tvNhaCungCap = itemView.findViewById(R.id.tvNhaCungCap);
            tvCodeSale = itemView.findViewById(R.id.tvMaGiamGia);


        }
    }
}
