package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.BannerDanhMucAdapter;
import com.example.myapplication.Adapter.SanPhamAdapter;
import com.example.myapplication.Adapter.SanPhamMoiDanhMucAdapter;
import com.example.myapplication.Model.BannerDanhMuc;
import com.example.myapplication.Model.DanhMuc;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhMucActivity extends AppCompatActivity {
DanhMuc danhMuc;
SanPhamAdapter sanPhamAdapter;
SanPhamMoiDanhMucAdapter sanPhamMoiDanhMucAdapter;
RecyclerView recyclerViewSp,recyclerViewSpMoi;
TextView tvDanhMucChiTiet;
ViewPager viewPagerBannerDanhMuc;
CircleIndicator circleIndicator;
ArrayList<BannerDanhMuc> danhMucs;
ArrayList<SanPham>sanPhams;
Toolbar toolbar;
BannerDanhMucAdapter bannerDanhMucAdapter;
Runnable runnable;
Handler handler;
int currentItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        tvDanhMucChiTiet = findViewById(R.id.tvDanhMucChiTiet);
        anhxa();
        DataIntent();
        if (danhMuc !=null && !danhMuc.getCatId().equals("")){
            GetDataBannerDanhMuc(danhMuc.getCatId());
            GetDataSanPhamDanhMuc(danhMuc.getCatId());
            GetDataSanPhamMoi(danhMuc.getCatId());
        }
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("danhmuc")){
                danhMuc = (DanhMuc) intent.getSerializableExtra("danhmuc");
                tvDanhMucChiTiet.setText(danhMuc.getCatName());
            }
        }
    }
    private void GetDataSanPhamDanhMuc(String catId) {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSanPhamTheoDanhMuc(catId);
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhams = (ArrayList<SanPham>) response.body();
                sanPhamAdapter = new SanPhamAdapter(DanhMucActivity.this,sanPhams);
                recyclerViewSp.setLayoutManager(new GridLayoutManager(DanhMucActivity.this,2));
                recyclerViewSp.setAdapter(sanPhamAdapter);

            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
    private void GetDataSanPhamMoi(String catIdSPMoi) {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSanPhamMoiDanhMuc(catIdSPMoi);
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhams = (ArrayList<SanPham>) response.body();
                sanPhamMoiDanhMucAdapter = new SanPhamMoiDanhMucAdapter(DanhMucActivity.this,sanPhams);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhMucActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewSpMoi.setLayoutManager(linearLayoutManager);
                recyclerViewSpMoi.setAdapter(sanPhamMoiDanhMucAdapter);

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }

    private void GetDataBannerDanhMuc(String catId) {
        Dataservice dataservice = APIService.getService();
        Call<List<BannerDanhMuc>> callback = dataservice.GetDataBannerTheoDanhMuc(catId);
        callback.enqueue(new Callback<List<BannerDanhMuc>>() {
            @Override
            public void onResponse(Call<List<BannerDanhMuc>> call, Response<List<BannerDanhMuc>> response) {
                danhMucs = (ArrayList<BannerDanhMuc>) response.body();
                bannerDanhMucAdapter = new BannerDanhMucAdapter(DanhMucActivity.this,danhMucs);
                viewPagerBannerDanhMuc.setAdapter(bannerDanhMucAdapter);
                circleIndicator.setViewPager(viewPagerBannerDanhMuc);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPagerBannerDanhMuc.getCurrentItem();
                        currentItem++;
                        if (currentItem>= viewPagerBannerDanhMuc.getAdapter().getCount()){
                            currentItem = 0;

                        }
                        viewPagerBannerDanhMuc.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 3000);
                    }
                };
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onFailure(Call<List<BannerDanhMuc>> call, Throwable t) {

            }
        });
    }
    private void anhxa() {
        viewPagerBannerDanhMuc = findViewById(R.id.viewpagerBannerDanhmuc);
        circleIndicator = findViewById(R.id.circleBannerdanhmuc);
        recyclerViewSp = findViewById(R.id.recyclerviewSanPhamDanhMuc);
        recyclerViewSpMoi = findViewById(R.id.recycleviewSanPhamMoi);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                return;
            }
        });
    }
}
