package com.example.myproducts.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.myproducts.Model.User;

public class Common {

    public static User currentUser;

    public static final String DELETE="delete";
    public static final String USER_KEY="user";
    public static final String PASS_KEY="pass";

    public static String convertCodeToStatus(String status){
        if(status.equals("0")){
            return "placed";
        }
        else if(status.equals("1")){
            return "On My Way";
        }
        else {
            return "Shipped";
        }
    }


    public  static boolean isConnectedToInterner(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo[] infos=connectivityManager.getAllNetworkInfo();
            for(int i=0 ; i < infos.length ; i++){
                if(infos[i].getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }

        }
        return false;
    }


}
