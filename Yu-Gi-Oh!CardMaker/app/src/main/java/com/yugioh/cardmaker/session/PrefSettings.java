package com.yugioh.cardmaker.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yugioh.cardmaker.main.HomeActivity;

public class PrefSettings {
    public static String _id;
    public static String userName;
    public static String email;
    Activity activity;

    public PrefSettings(Activity activity){
        this.activity = activity;
    }

    public SharedPreferences getSharePreferances(){
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return preferences;
    }

    public void isLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        if(session.isLoggedIn()){
            pref = getSharePreferances();
            _id  = pref.getString("_id", "");
            userName = pref.getString("userName", "");
            email = pref.getString("email", "");
        }else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }

    public void checkLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        _id  = pref.getString("_id", "");
        userName = pref.getString("userName", "");
        email = pref.getString("email", "");
        if(session.isLoggedIn()){
            Intent i = new Intent(activity, HomeActivity.class);
            activity.startActivity(i);
            activity.finish();
        }
    }


    public void storeRegIdSharedPreferences(Context context, String _id, String userName, String email, SharedPreferences prefs){

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("_id", _id);
        editor.putString("userName", userName);
        editor.putString("email", email);
        editor.commit();
    }
}
