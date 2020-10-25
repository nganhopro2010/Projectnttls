package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDanhMuc {
private static String url_image = "https://nttlsales.000webhostapp.com/admin/uploads/";

@SerializedName("bannerCatId")
@Expose
private String bannerCatId;
@SerializedName("catId")
@Expose
private String catId;
@SerializedName("imageBannerCat")
@Expose
private String imageBannerCat;

public String getBannerCatId() {
return bannerCatId;
}

public void setBannerCatId(String bannerCatId) {
this.bannerCatId = bannerCatId;
}

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getImageBannerCat() {
return url_image+imageBannerCat;
}

public void setImageBannerCat(String imageBannerCat) {
this.imageBannerCat = imageBannerCat;
}

}