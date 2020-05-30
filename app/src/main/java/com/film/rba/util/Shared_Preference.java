package com.film.rba.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Shadab Mallick on 24/1/20.
 */

public class Shared_Preference {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private GlobalClass globalclass;

    private boolean pref_logInStatus;
    private String pref_name;
    private String pref_id;
    private String pref_email;
    private String pref_phone_number;
    private String pref_profile_img;
    private String islogin;
    private String enquiry_base_id;


    private String remote_user_id;

    private String fcm;
    private String login_from;


    private static final String PREFS_NAME = "preferences";
    private static final String PREFS_NAME2 = "preferences2";

    private static final String PREF_logInStatus = "logInStatus";
    private static final String PREF_name = "name";
    private static final String PREF_email = "user_email";
    private static final String PREF_phone_number = "phone_number";
    private static final String PREF_id = "id";
    private static final String PREF_profile_img = "profile_img";
    private static final String PREF_login_from = "login_from";
    private static final String remote = "remote_id";
    private static final String Islogiin = "is_login";
    private static final String token = "token";
    private static final String Prefenquiry_base_id = "enquiry_base_id";


    public Shared_Preference(Context context) {
        this.context = context;

        this.globalclass = (GlobalClass) context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

    }

    public void savePrefrence() {

        if (globalclass.getLogin_status()) {

            editor.putString(remote,remote_user_id);
            pref_logInStatus = globalclass.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);

            pref_name = globalclass.getName();
            editor.putString(PREF_name, pref_name);

            pref_id= globalclass.getId();
            editor.putString(PREF_id,pref_id);

            pref_email= globalclass.getEmail();
            editor.putString(PREF_email,pref_email);

            editor.putString(PREF_profile_img, globalclass.getProfil_pic());

            enquiry_base_id = globalclass.getEnquiry_base_id();
            editor.putString(Prefenquiry_base_id,enquiry_base_id);

            pref_phone_number= globalclass.getPhone_number();
            editor.putString(PREF_phone_number,pref_phone_number);

            editor.putString(token, globalclass.getToken());

             islogin=globalclass.getIslogin();
             editor.putString(Islogiin,islogin);

            editor.commit();

        }else{
            // dont save anything, if user is logged out
            pref_logInStatus = globalclass.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);
            editor.commit();
        }

    }

    public void loadPrefrence() {

        pref_logInStatus = sharedPreferences.getBoolean(PREF_logInStatus, false);
        globalclass.setLogin_status(pref_logInStatus);

        Log.d("TV", globalclass.getLogin_status() + "");
        if (globalclass.getLogin_status()) {

            remote_user_id=sharedPreferences.getString(remote,"");

            pref_name = sharedPreferences.getString(PREF_name, "");
            globalclass.setName(pref_name);

            islogin=sharedPreferences.getString(Islogiin,"");
            globalclass.setIslogin(islogin);

            pref_id= sharedPreferences.getString(PREF_id,"");
            globalclass.setId(pref_id);

            pref_phone_number=sharedPreferences.getString(PREF_phone_number,"");
            globalclass.setPhone_number(pref_phone_number);

            pref_email=sharedPreferences.getString(PREF_email,"");
            globalclass.setEmail(pref_email);


            enquiry_base_id=sharedPreferences.getString(Prefenquiry_base_id,"");
            globalclass.setEnquiry_base_id(enquiry_base_id);

            pref_profile_img=sharedPreferences.getString(PREF_profile_img,"");
            globalclass.setProfil_pic(pref_profile_img);


            login_from=sharedPreferences.getString(PREF_login_from,"");
            globalclass.setLogin_from(login_from);

            globalclass.setToken(sharedPreferences.getString(token,""));

        }
    }

    public void clearPrefrence(){
        editor.clear();
        editor.commit();
    }




}
