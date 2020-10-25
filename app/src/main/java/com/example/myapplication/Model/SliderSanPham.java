package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderSanPham {
    private static String url_image = "https://nttlsales.000webhostapp.com/admin/uploads/";

@SerializedName("idGallery")
@Expose
private String idGallery;
@SerializedName("productId")
@Expose
private String productId;
@SerializedName("hinhAnh")
@Expose
private String hinhAnh;

public String getIdGallery() {
return idGallery;
}

public void setIdGallery(String idGallery) {
this.idGallery = idGallery;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

public String getHinhAnh() {
return url_image+hinhAnh;
}

public void setHinhAnh(String hinhAnh) {
this.hinhAnh = hinhAnh;
}

}