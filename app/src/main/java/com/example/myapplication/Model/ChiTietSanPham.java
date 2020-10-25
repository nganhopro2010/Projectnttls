package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChiTietSanPham implements Serializable {
    private static String url_image = "https://nttlsales.000webhostapp.com/admin/uploads/";

@SerializedName("productId")
@Expose
private String productId;
@SerializedName("productName")
@Expose
private String productName;
@SerializedName("catId")
@Expose
private String catId;
@SerializedName("brandId")
@Expose
private String brandId;
@SerializedName("productDesc")
@Expose
private String productDesc;
@SerializedName("price")
@Expose
private String price;
@SerializedName("type")
@Expose
private String type;
@SerializedName("image")
@Expose
private String image;
@SerializedName("codeSale")
@Expose
private String codeSale;
@SerializedName("adminId")
@Expose
private String adminId;
@SerializedName("comId")
@Expose
private String comId;
@SerializedName("productDescDetail")
@Expose
private String productDescDetail;
@SerializedName("quantity")
@Expose
private String quantity;
@SerializedName("catName")
@Expose
private String catName;
@SerializedName("brandName")
@Expose
private String brandName;
@SerializedName("adminName")
@Expose
private String adminName;
@SerializedName("adminEmail")
@Expose
private String adminEmail;
@SerializedName("adminTel")
@Expose
private String adminTel;

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getBrandId() {
return brandId;
}

public void setBrandId(String brandId) {
this.brandId = brandId;
}

public String getProductDesc() {
return productDesc;
}

public void setProductDesc(String productDesc) {
this.productDesc = productDesc;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getImage() {
return url_image+image;
}

public void setImage(String image) {
this.image = image;
}

public String getCodeSale() {
return codeSale;
}

public void setCodeSale(String codeSale) {
this.codeSale = codeSale;
}

public String getAdminId() {
return adminId;
}

public void setAdminId(String adminId) {
this.adminId = adminId;
}

public String getComId() {
return comId;
}

public void setComId(String comId) {
this.comId = comId;
}

public String getProductDescDetail() {
return productDescDetail;
}

public void setProductDescDetail(String productDescDetail) {
this.productDescDetail = productDescDetail;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

public String getCatName() {
return catName;
}

public void setCatName(String catName) {
this.catName = catName;
}

public String getBrandName() {
return brandName;
}

public void setBrandName(String brandName) {
this.brandName = brandName;
}

public String getAdminName() {
return adminName;
}

public void setAdminName(String adminName) {
this.adminName = adminName;
}

public String getAdminEmail() {
return adminEmail;
}

public void setAdminEmail(String adminEmail) {
this.adminEmail = adminEmail;
}

public String getAdminTel() {
return adminTel;
}

public void setAdminTel(String adminTel) {
this.adminTel = adminTel;
}

}