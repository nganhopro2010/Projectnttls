package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Model.BannerDanhMuc;
import com.example.myapplication.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerDanhMucAdapter extends PagerAdapter {
    Context context;
    ArrayList<BannerDanhMuc> arrayListBannerDanhMuc;

    public BannerDanhMucAdapter(Context context, ArrayList<BannerDanhMuc> arrayListBannerDanhMuc) {
        this.context = context;
        this.arrayListBannerDanhMuc = arrayListBannerDanhMuc;
    }

    @Override
    public int getCount() {
        return arrayListBannerDanhMuc.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_banner_danhmuc, null);
        RoundedImageView imgBannerDanhMuc = view.findViewById(R.id.imgBannerDanhMuc);
        Picasso.with(context).load(arrayListBannerDanhMuc.get(position).getImageBannerCat()).into(imgBannerDanhMuc);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
