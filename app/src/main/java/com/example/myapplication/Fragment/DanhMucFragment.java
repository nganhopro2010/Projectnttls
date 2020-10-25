package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.Adapter.DanhMucAdapter;
import com.example.myapplication.Model.DanhMuc;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhMucFragment extends Fragment {
    RecyclerView recyclerViewDanhMuc;
    DanhMucAdapter danhMucAdapter;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dash, container, false);
        recyclerViewDanhMuc = view.findViewById(R.id.recyclerviewDanhMuc);
        GetDataDanhMuc();
        return view;
    }

    private void GetDataDanhMuc() {
        Dataservice dataservice = APIService.getService();
        Call<List<DanhMuc>> callback = dataservice.GetDataDanhMuc();
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        progressDoalog.show();
        callback.enqueue(new Callback<List<DanhMuc>>() {
            @Override
            public void onResponse(Call<List<DanhMuc>> call, Response<List<DanhMuc>> response) {
                ArrayList<DanhMuc> danhMucArrayList = (ArrayList<DanhMuc>) response.body();
                danhMucAdapter = new DanhMucAdapter(getActivity(),danhMucArrayList);
                recyclerViewDanhMuc.setLayoutManager(new GridLayoutManager(getContext(),3));
                recyclerViewDanhMuc.setAdapter(danhMucAdapter);
                progressDoalog.dismiss();
            }
            @Override
            public void onFailure(Call<List<DanhMuc>> call, Throwable t) {

            }
        });
    }

}
