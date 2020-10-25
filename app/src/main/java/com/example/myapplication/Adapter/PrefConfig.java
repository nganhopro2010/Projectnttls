package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myapplication.R;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public  PrefConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),
                Context.MODE_PRIVATE);
    }
    public  void  writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }
    public  boolean readLoginStatus(){
        return  sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),false);

    }
    public  void  writeCustomerId(String CustomerId, String CusName,String CusEmail, String CusTel, String CusPass, String CusBirthday, String CusAddress){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_customerId),CustomerId);
        editor.putString(context.getString(R.string.pref_cusName),CusName);
        editor.putString(context.getString(R.string.pref_cusEmail),CusEmail);
        editor.putString(context.getString(R.string.pref_cusTel),CusTel);
        editor.putString(context.getString(R.string.pref_cusPass),CusPass);
        editor.putString(context.getString(R.string.pref_cusBirthday),CusBirthday);
        editor.putString(context.getString(R.string.pref_cusAddress),CusAddress);
        editor.commit();
    }
    public  String readCustomerId(){
        return sharedPreferences.getString(context.getString(R.string.pref_customerId),"CustomerId");
    }
    public  String readCusName(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusName),"CusName");
    }

    public  String readCusEmail(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusEmail),"CusEmail");
    }
    public  String readCusTel(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusTel),"CusTel");
    }
    public  String readCusPass(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusPass),"CusPass");
    }
    public  String readCusBirthday(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusBirthday),"CusBirthday");
    }
    public  String readCusAddress(){
        return sharedPreferences.getString(context.getString(R.string.pref_cusAddress),"CusAddress");
    }


    public  void displayToast (String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
