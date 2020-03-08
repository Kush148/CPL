package com.example.cpl;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private String id = "id";
    private String name = "name";
    private String managerType = "manager";;

    public SharedPref(Context context) {
        pref = context.getSharedPreferences("CPL", 0);
        editor = pref.edit();
        editor.apply();
    }

    public String getId() {
        return pref.getString(id, "");
    }

    public void setId(String id) {
        editor.putString(this.id, id);
        editor.commit();
    }

    public String getName() {
        return pref.getString(name, "");
    }

    public void setName(String name) {
        editor.putString(this.name, name);
        editor.commit();
    }

    public String getManagerType() {
        return pref.getString(managerType, "");
    }

    public void setManagerType(String manager) {
        editor.putString(this.managerType, manager);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}