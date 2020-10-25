package com.example.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Model.SliderSanPham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderSanPhamAdapter extends PagerAdapter {
    Context context;
    ArrayList<SliderSanPham> arrayListSliderSanPham;

    public SliderSanPhamAdapter(Context context, ArrayList<SliderSanPham> arrayListSliderSanPham) {
        this.context = context;
        this.arrayListSliderSanPham = arrayListSliderSanPham;
    }

    @Override
    public int getCount() {
        return arrayListSliderSanPham.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_slider_sanpham, null);
        ImageView imgSliderSanPham = view.findViewById(R.id.imgSliderSanPham);
        Picasso.with(context).load(arrayListSliderSanPham.get(position).getHinhAnh()).into(imgSliderSanPham);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
