package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.Adapter.SanPhamDaThichAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeuThichFragment extends Fragment {
    RecyclerView recyclerViewYeuThich;
    SanPhamDaThichAdapter sanPhamDaThichAdapter;
    ArrayList<SanPham>sanPhamArrayList;
    LinearLayout linearLayoutWarm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tinnhan, container, false);
        recyclerViewYeuThich = view.findViewById(R.id.recyclerviewYeuThich);
        GetYeuThich();
        return view;
    }

    private void GetYeuThich() {
        Dataservice dataservice = APIService.getService();
        Call<List<SanPham>> callback = dataservice.GetYeuThich(MainActivity.prefConfig.readCustomerId());
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        // show it
        progressDoalog.show();
        callback.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                sanPhamArrayList = (ArrayList<SanPham>) response.body();
                sanPhamDaThichAdapter = new SanPhamDaThichAdapter(getActivity(),sanPhamArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewYeuThich.setLayoutManager(linearLayoutManager);
                recyclerViewYeuThich.setAdapter(sanPhamDaThichAdapter);
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
}
