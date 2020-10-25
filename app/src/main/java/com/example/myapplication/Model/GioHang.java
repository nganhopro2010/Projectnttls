package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("response")
    private String Response;
    @SerializedName("productId")
    private String productId;

    @SerializedName("customerId")
    private String customerId;
    public String getProductId() {
        return productId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getResponse() {
        return Response;
    }

}
