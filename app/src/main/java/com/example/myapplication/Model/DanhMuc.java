package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DanhMuc implements Serializable {
private static String url_image = "https://nttlsales.000webhostapp.com/admin/uploads/";

@SerializedName("catId")
@Expose
private String catId;
@SerializedName("catName")
@Expose
private String catName;
@SerializedName("imageCat")
@Expose
private String imageCat;

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getCatName() {
return catName;
}

public void setCatName(String catName) {
this.catName = catName;
}

public String getImageCat() {
return url_image+imageCat;
}

public void setImageCat(String imageCat) {
this.imageCat = imageCat;
}

}