package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ArrowKeyMovementMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Adapter.SearchSanPhamAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
Toolbar toolbar;
private EditText edTuKhoa;
private TextView tvTimkiem, tvNoData;
private ImageView imgDeleteSearch,imgback;
SearchSanPhamAdapter searchSanPhamAdapter;
RecyclerView recyclerviewSearch;
LinearLayout linearLayoutNodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        edTuKhoa = (EditText)findViewById(R.id.edTuKhoa) ;
        imgback = (ImageView)findViewById(R.id.imgback);
        imgDeleteSearch = (ImageView) findViewById(R.id.imgDeleteSearch) ;
        tvTimkiem = (TextView)findViewById(R.id.tvTimkiem);
        tvNoData = findViewById(R.id.tvNoData);
        recyclerviewSearch = findViewById(R.id.recyclerviewSearch);
        linearLayoutNodata = findViewById(R.id.linearlayoutNodata);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        edTuKhoa.requestFocus();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                return;
            }
        });

        imgDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTuKhoa.setText("");
            }
        });
        tvTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchTuKhoaSanPham();
            }
        });

    }

    private void SearchTuKhoaSanPham(){
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSearchSanPham(edTuKhoa.getText().toString());
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(SearchActivity.this,R.style.CustomPD);
        // show it
        progressDoalog.show();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                ArrayList<SanPham> sanPhamArrayList = (ArrayList<SanPham>) response.body();
                if (sanPhamArrayList.size()>0){
                    searchSanPhamAdapter = new SearchSanPhamAdapter(SearchActivity.this, sanPhamArrayList);
                    recyclerviewSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
                    recyclerviewSearch.setAdapter(searchSanPhamAdapter);
                    linearLayoutNodata.setVisibility(View.GONE);
                    recyclerviewSearch.setVisibility(View.VISIBLE);

                }else {
                    recyclerviewSearch.setVisibility(View.INVISIBLE);
                    linearLayoutNodata.setVisibility(View.VISIBLE);
                }
                progressDoalog.dismiss();

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }


}
