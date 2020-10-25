package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("response")
    private String Response;

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("cusName")
    private String cusName;

    @SerializedName("cusEmail")
    private String cusEmail;

    @SerializedName("cusTel")
    private String cusTel;

    @SerializedName("cusPass")
    private String cusPass;

    @SerializedName("cusBirthday")
    private String cusBirthday;

    @SerializedName("cusAddress")
    private String cusAddress;

    public String getResponse() {
        return Response;
    }

    public String getCustomerId() {
        return customerId;
    }
    public String getCusName() {
        return cusName;
    }
    public String getCusEmail() {
        return cusEmail;
    }
    public String getCusTel() {
        return cusTel;
    }
    public String getCusPass() {
        return cusPass;
    }
    public String getCusBirthday() {
        return cusBirthday;
    }
    public String getCusAddress() {
        return cusAddress;
    }

}
