package com.example.mapdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Pref_Master {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private String str_user_id = "uid";
    private String user_id = "0";


    // 0 = English , 1 = Arabic , 3 = first
    Context context;

    public Pref_Master(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUID() {
        return pref.getString(str_user_id, user_id);
    }




    public void setUID(String name) {
        editor = pref.edit();
        editor.putString(str_user_id, name);
        editor.apply();
    }

    public void clear_pref() {
        pref.edit().clear().apply();
    }


}
