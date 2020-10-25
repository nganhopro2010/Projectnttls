package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Service.APIService;
import com.example.myapplication.Service.Dataservice;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamDaThichAdapter extends RecyclerView.Adapter<SanPhamDaThichAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public SanPhamDaThichAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_yeu_thich,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SanPham sanPham = sanPhamArrayList.get(i);
        Picasso.with(context).load(sanPham.getImage()).into(viewHolder.imgSanPham);
        viewHolder.tvTenSanPham.setText(sanPham.getProductName());
        viewHolder.tvCodeSale.setText(sanPham.getCodeSale());
        int value = Integer.valueOf(sanPham.getPrice());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        String format = String.valueOf(decimalFormat.format(value));
        viewHolder.tvGiaSanPham.setText(format+" đ");
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView imgSanPham;
        TextView tvTenSanPham;
        TextView tvGiaSanPham;
        TextView tvCodeSale;
        ImageView imgDeleteYeuThich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgDaYeu);
            tvTenSanPham = itemView.findViewById(R.id.tvTen);
            tvGiaSanPham = itemView.findViewById(R.id.tvGia);
            tvCodeSale = itemView.findViewById(R.id.tvGiamGia);
            imgDeleteYeuThich = itemView.findViewById(R.id.deleteYeuThich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("chitietspdm",sanPhamArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imgDeleteYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dataservice dataservice = APIService.getService();
                    Call<String> call = dataservice.XoaYeuThich(sanPhamArrayList.get(getPosition()).getProductId(), MainActivity.prefConfig.readCustomerId());

                    final SpotsDialog progressDoalog;
                    progressDoalog = new SpotsDialog(context,R.style.CustomPD);
                    // show it
                    progressDoalog.show();
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("YES")){
                                imgDeleteYeuThich.setImageResource(R.drawable.iconstartwhite);
                                imgDeleteYeuThich.setEnabled(false);
                                progressDoalog.dismiss();
                            }else{
                                Toast.makeText(context, "Bi Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDoalog.dismiss();
                        }
                    });
                }
            });
        }
    }
}
