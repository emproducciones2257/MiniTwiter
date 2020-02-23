package com.emproducciones.minitwiter.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private SharedPreferencesManager(){}

    private static SharedPreferences getSharedPreferences(){
        return MyApp.getContext()
                .getSharedPreferences(Constantes.APP_DATA, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValues(String label, String value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(label,value);
        editor.commit();
    }

    public static void setSomeBooleanValues(String label, Boolean value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(label,value);
        editor.commit();
    }

    public static String getSomeStringValues(String label){
        return getSharedPreferences().getString(label,null);
    }
}
