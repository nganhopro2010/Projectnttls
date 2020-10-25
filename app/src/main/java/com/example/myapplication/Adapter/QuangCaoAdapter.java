package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuangCaoAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrayListQuangCao;

    public QuangCaoAdapter(Context context, ArrayList<QuangCao> arrayListQuangCao) {
        this.context = context;
        this.arrayListQuangCao = arrayListQuangCao;
    }

    //vẽ bao nhiêu cái quảng cáo
    @Override
    public int getCount() {
        return arrayListQuangCao.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    //định hình, gán dữ liệu cho mỗi obj
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_quangcao, null);
        RoundedImageView imgbackground = view.findViewById(R.id.imageviewQuangcao);
        Picasso.with(context).load(arrayListQuangCao.get(position).getImage()).into(imgbackground);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
