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

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamCurrentAdapter extends RecyclerView.Adapter<SanPhamCurrentAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public SanPhamCurrentAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sanpham,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SanPham sanPham = sanPhamArrayList.get(i);
        Picasso.with(context).load(sanPham.getImage()).into(viewHolder.imgSanPham);
        viewHolder.tvTenSanPham.setText(sanPham.getProductName());
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
        ImageView imgSanPham;
        TextView tvTenSanPham;
        TextView tvGiaSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSp);
            tvGiaSanPham = itemView.findViewById(R.id.tvGiaSp);
            itemView.setOnClickListener(new View.OnClickListener() {
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
