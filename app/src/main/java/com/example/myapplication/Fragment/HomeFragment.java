package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Activity.SearchActivity;
import com.example.myapplication.Adapter.SanPhamCurrentAdapter;
import com.example.myapplication.Adapter.SanPhamDaXemAdapter;
import com.example.myapplication.Adapter.SanPhamTop20Adapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private TextView tvSearch;
    Toolbar toolbar;
    ArrayList<SanPham>sanPhamArrayList;
    SanPhamTop20Adapter sanPhamTop20Adapter;
    SanPhamDaXemAdapter sanPhamDaXemAdapter;
    SanPhamCurrentAdapter sanPhamCurrentAdapter;
    RecyclerView recyclerView,recyclerViewDaXem,recyclerviewCurrentDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        recyclerView =view.findViewById(R.id.recyclerviewTop);
        recyclerViewDaXem = view.findViewById(R.id.recyclerviewDaxem);
        recyclerviewCurrentDay = view.findViewById(R.id.recyclerviewCurrentDay);

        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tvSearch = (TextView)view.findViewById(R.id.textviewSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);

            }
        });
        GetSanPhamTop20();
        GetSanPhamDaXem();
        GetDataCurrent();
        return view;
    }

    private void GetSanPhamTop20() {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSanPhamTop20();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhamArrayList = (ArrayList<SanPham>) response.body();
                sanPhamTop20Adapter = new SanPhamTop20Adapter(getActivity(),sanPhamArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(sanPhamTop20Adapter);
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
    private void GetSanPhamDaXem() {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSanPhamDaXem(MainActivity.prefConfig.readCustomerId());
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhamArrayList = (ArrayList<SanPham>) response.body();
                sanPhamDaXemAdapter = new SanPhamDaXemAdapter(getActivity(),sanPhamArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewDaXem.setLayoutManager(linearLayoutManager);
                recyclerViewDaXem.setAdapter(sanPhamDaXemAdapter);
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }

    private void GetDataCurrent() {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetSanPhamForCurrentDay();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhamArrayList = (ArrayList<SanPham>) response.body();
                sanPhamCurrentAdapter = new SanPhamCurrentAdapter(getActivity(),sanPhamArrayList);
                recyclerviewCurrentDay.setLayoutManager(new GridLayoutManager(getActivity(),2));
                recyclerviewCurrentDay.setAdapter(sanPhamCurrentAdapter);

            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }


}
