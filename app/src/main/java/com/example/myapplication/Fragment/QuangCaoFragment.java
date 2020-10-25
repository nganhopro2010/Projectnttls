package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.QuangCaoAdapter;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuangCaoFragment extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    QuangCaoAdapter quangCaoAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quangcao,container,false);
        anhxa();
        getDataSlider();
        return view;

    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);

    }

    private void getDataSlider(){
        Dataservice dataservice = APIService.getService();
        Call<List<QuangCao>> callback = dataservice.GetDataQuangCao();
        final SpotsDialog progressDoalog;
        progressDoalog = new SpotsDialog(getActivity(),R.style.CustomPD);
        progressDoalog.show();
        callback.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                ArrayList<QuangCao> quangCaos = (ArrayList<QuangCao>) response.body();
                quangCaoAdapter = new QuangCaoAdapter(getActivity(),quangCaos);
                viewPager.setAdapter(quangCaoAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager.getAdapter().getCount()){
                            currentItem = 0;

                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable,3000);
                    }
                };
                handler.postDelayed(runnable, 3000);
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {

            }
        });
    }
}
