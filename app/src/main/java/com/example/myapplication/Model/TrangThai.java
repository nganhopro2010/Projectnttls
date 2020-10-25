package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class TrangThai {
    @SerializedName("response")
    private String Response;

    @SerializedName("tongTien")
    private String TongTien;

    public String getResponse() {
        return Response;
    }
    public String getTongTien() {
        return TongTien;
    }

}
