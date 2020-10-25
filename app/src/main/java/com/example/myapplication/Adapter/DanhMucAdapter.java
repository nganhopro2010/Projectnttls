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

import com.example.myapplication.Activity.DanhMucActivity;
import com.example.myapplication.Model.DanhMuc;
import com.example.myapplication.R;
import com.github.siyamed.shapeimageview.OctogonImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>  {
    Context context;
    ArrayList<DanhMuc> mangdanhMuc;

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> mangdanhMuc) {
        this.context = context;
        this.mangdanhMuc = mangdanhMuc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_danh_muc,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DanhMuc danhMuc = mangdanhMuc.get(i);
        viewHolder.tvdanhMuc.setText(danhMuc.getCatName());
        Picasso.with(context).load(danhMuc.getImageCat()).into(viewHolder.imgdanhMuc);


    }

    @Override
    public int getItemCount() {
        return mangdanhMuc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgdanhMuc;
        TextView tvdanhMuc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgdanhMuc = itemView.findViewById(R.id.imgDanhmuc);
            tvdanhMuc = itemView.findViewById(R.id.tvdanhMuc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhMucActivity.class);
                    intent.putExtra("danhmuc",mangdanhMuc.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

}
