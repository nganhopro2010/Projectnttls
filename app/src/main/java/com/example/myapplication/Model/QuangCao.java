package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuangCao implements Serializable {
private static String url_image = "https://nttlsales.000webhostapp.com/admin/uploads/";
    @SerializedName("comId")
    @Expose
    private String comId;
    @SerializedName("catId")
    @Expose
    private String catId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("comDesc")
    @Expose
    private String comDesc;

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getImage() {
        return url_image+image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComDesc() {
        return comDesc;
    }

    public void setComDesc(String comDesc) {
        this.comDesc = comDesc;
    }
}