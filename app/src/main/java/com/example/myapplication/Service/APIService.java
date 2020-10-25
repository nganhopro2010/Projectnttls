package com.example.myapplication.Service;

public class APIService {
    private static String base_url ="https://nttlsales.000webhostapp.com/Server/";
    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
