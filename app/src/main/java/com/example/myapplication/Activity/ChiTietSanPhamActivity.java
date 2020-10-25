package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.Adapter.ChiTietSanPhamAdapter;
import com.example.myapplication.Adapter.MoTaSanPhamAdapter;
import com.example.myapplication.Adapter.SliderSanPhamAdapter;
import com.example.myapplication.Fragment.BottomThemGioHangFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.ChiTietSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.SliderSanPham;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSanPhamActivity extends AppCompatActivity implements BottomThemGioHangFragment.BottomSheetListener{
Toolbar toolbar;
public  static  SanPham sanPham;
public  static  GioHang gioHang;
ViewPager viewPagerSliderSanPham;
CircleIndicator circleIndicator;
ArrayList<SliderSanPham>sliderSanPhams;
SliderSanPhamAdapter sliderSanPhamAdapter;
ArrayList<ChiTietSanPham> chiTietSanPhams;
ChiTietSanPhamAdapter chiTietSanPhamAdapter;
MoTaSanPhamAdapter moTaSanPhamAdapter;
RecyclerView recyclerViewChiTiet,recyclerViewMoTaSanPham;
RelativeLayout relativeMuangay, relativeThemVaoGio, relativeYeuThich;
AppBarLayout appBarLayout;
CollapsingToolbarLayout collapsingtoolbar;
ImageView imgYeuThich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        DataIntent();
        if (sanPham != null && !sanPham.getProductId().equals("")){
            GetDataSliderSanPham(sanPham.getProductId());
            ThemLuotXem(MainActivity.prefConfig.readCustomerId(),sanPham.getProductId());
            ThemLuotXemProduct(sanPham.getProductId());
        }
        GetChiTietSanPham(sanPham.getProductId());
        GetMoTaSanPham(sanPham.getProductId());
        relativeYeuThich = findViewById(R.id.relativeNhanTin);
        relativeThemVaoGio = findViewById(R.id.relativeThemVaoGio);
        relativeThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomThemGioHangFragment bottomsheet = new BottomThemGioHangFragment();
                bottomsheet.show(getSupportFragmentManager(),"BottomShet");
                //PostVaoGio(sanPham.getProductId(), MainActivity.prefConfig.readCustomerId());
            }
        });
        relativeMuangay = findViewById(R.id.relativeMuangay);
        relativeMuangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, MuaNgayActivity.class);
                intent.putExtra("muaSanPham",sanPham);
                startActivity(intent);
                }
        });
        YeuThich();

    }

    private void anhxa() {
        viewPagerSliderSanPham = findViewById(R.id.viewpagerSliderSanPham);
        circleIndicator = findViewById(R.id.circleSlider);
        recyclerViewChiTiet = findViewById(R.id.recyclerviewChiTietSanPham);
        recyclerViewMoTaSanPham = findViewById(R.id.recyclerviewMota);
        collapsingtoolbar = findViewById(R.id.collapsingtoolbar);
        collapsingtoolbar.setCollapsedTitleTextAppearance(R.style.MyTitleTextApperance);
        toolbar = findViewById(R.id.toolbarChiTiet);
        imgYeuThich = findViewById(R.id.imgYeuThich);
        appBarLayout = findViewById(R.id.appbarlayout);
        collapsingtoolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + i == 0) {
                    collapsingtoolbar.setTitle("POLY MARKET");
                    isShow = true;
                } else if(isShow) {
                    collapsingtoolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
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

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("chitietspdm")){
                sanPham = (SanPham) intent.getSerializableExtra("chitietspdm");
            }
        }
    }
    private  void  GetDataSliderSanPham(String productId){
        Dataservice dataservice = APIService.getService();
        Call<List<SliderSanPham>> callback = dataservice.GetDataSliderSanPham(productId);
        callback.enqueue(new Callback<List<SliderSanPham>>() {
            @Override
            public void onResponse(Call<List<SliderSanPham>> call, Response<List<SliderSanPham>> response) {
                sliderSanPhams = (ArrayList<SliderSanPham>) response.body();
                sliderSanPhamAdapter = new SliderSanPhamAdapter(ChiTietSanPhamActivity.this,sliderSanPhams);
                viewPagerSliderSanPham.setAdapter(sliderSanPhamAdapter);
                circleIndicator.setViewPager(viewPagerSliderSanPham);
            }

            @Override
            public void onFailure(Call<List<SliderSanPham>> call, Throwable t) {

            }
        });

    }
    private void GetChiTietSanPham(String productId) {
        Dataservice dataservice = APIService.getService();
        Call<List<ChiTietSanPham>> callback = dataservice.GetChiTietSanPham(productId);
        callback.enqueue(new Callback<List<ChiTietSanPham>>() {
            @Override
            public void onResponse(Call<List<ChiTietSanPham>> call, Response<List<ChiTietSanPham>> response) {
                chiTietSanPhams = (ArrayList<ChiTietSanPham>) response.body();
                chiTietSanPhamAdapter = new ChiTietSanPhamAdapter(ChiTietSanPhamActivity.this,chiTietSanPhams);
                recyclerViewChiTiet.setLayoutManager(new LinearLayoutManager(ChiTietSanPhamActivity.this));
                recyclerViewChiTiet.setAdapter(chiTietSanPhamAdapter);
            }
            @Override
            public void onFailure(Call<List<ChiTietSanPham>> call, Throwable t) {

            }
        });

    }
    private void GetMoTaSanPham(String productId) {
        Dataservice dataservice = APIService.getService();
        Call<List<ChiTietSanPham>> callback = dataservice.GetChiTietSanPham(productId);
        callback.enqueue(new Callback<List<ChiTietSanPham>>() {
            @Override
            public void onResponse(Call<List<ChiTietSanPham>> call, Response<List<ChiTietSanPham>> response) {
                chiTietSanPhams = (ArrayList<ChiTietSanPham>) response.body();
                moTaSanPhamAdapter = new MoTaSanPhamAdapter(ChiTietSanPhamActivity.this,chiTietSanPhams);
                recyclerViewMoTaSanPham.setLayoutManager(new LinearLayoutManager(ChiTietSanPhamActivity.this));
                recyclerViewMoTaSanPham.setAdapter(moTaSanPhamAdapter);

            }

            @Override
            public void onFailure(Call<List<ChiTietSanPham>> call, Throwable t) {

            }
        });

    }
    private void ThemLuotXem(String customerId, String productId){
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> call = dataservice.ThemLuotXem(customerId,productId);
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });

    }
    private void ThemLuotXemProduct( String productId){
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> call = dataservice.ThemLuotXemProduct(productId);
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onOptionClick(String text) {

    }


    public void YeuThich(){
        relativeYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgYeuThich.setImageResource(R.drawable.iconstartwhite);
                Dataservice dataservice = APIService.getService();
                Call<List<SanPham>> call = dataservice.ThemYeuThich(MainActivity.prefConfig.readCustomerId(),sanPham.getProductId());
                call.enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }
        });
    }
}
