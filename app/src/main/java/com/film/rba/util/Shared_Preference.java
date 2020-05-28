package com.film.rba.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Shadab Mallick on 24/1/20.
 */

public class Shared_Preference {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;



    GlobalClass globalclass;
    private boolean pref_logInStatus;
    private String pref_name;
    private String pref_fname;
    private String pref_lname;
    private String pref_id;
    private String list_id;
    private String pref_email;
    private String pref_phone_number;
    private String pref_order_id;
    private String pref_order_type;
    private String pref_business;
    private String pref_user_type;
    private String pref_profile_img;
    private String pref_shipping_address;
    private String pref_cart_no;
    private String pref_ship_address_id;
    private String pref_ship_full_address;
    private String pref_organisation;
    private String islogin;
    private String enquiry_base_id;
    String department_ids;
    String group_ids;
    String role_ids;




    private String remote_user_id;

    private String fcm;
    private String login_from;


    private static final String PREFS_NAME = "preferences";
    private static final String PREFS_NAME2 = "preferences2";

    private static final String PREF_logInStatus = "logInStatus";
    private static final String PREF_name = "name";
    private static final String PREF_fname = "fname";
    private static final String PREF_lname = "lname";
    private static final String PREF_email = "user_email";
    private static final String PREF_phone_number = "phone_number";
    private static final String PREF_user_type = "user_type";
    private static final String PREF_business = "business";
    private static final String PREF_id = "id";
    private static final String PREF_profile_img = "profile_img";
    private static final String PREF_cart_no = "cart_no";
    private static final String PREF_ship_address_id = "ship_address_id";
    private static final String PREF_ship_full_address = "ship_full_address";
    private static final String PREF_login_from = "login_from";
    private static final String PREF_organisation = "organisation";
    private static final String PREF_order_id = "order_id";
    private static final String PREF_order_type = "ordertype";
    private static final String remote = "remote_id";
    private static final String Islogiin = "is_login";
    private static final String Prefenquiry_base_id = "enquiry_base_id";
    private static final String PREF_department_ids = "department_ids";
    private static final String Pref_group_ids = "group_ids";
    private static final String Pref_role_ids = "role_ids";




    public Shared_Preference(Context context) {
        this.context = context;

        this.globalclass = (GlobalClass) context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

        this.sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        this.editor2 = sharedPreferences2.edit();



    }

    public void savePrefrence() {


        if (globalclass.getLogin_status()) {

            editor.putString(remote,remote_user_id);
            pref_logInStatus = globalclass.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);

            pref_name = globalclass.getName();
            editor.putString(PREF_name, pref_name);

            pref_fname = globalclass.getFname();
            editor.putString(PREF_fname, pref_fname);

            pref_lname = globalclass.getLname();
            editor.putString(PREF_lname, pref_lname);

            pref_id= globalclass.getId();
            editor.putString(PREF_id,pref_id);

           // list_id= globalclass.getList_Id();
            editor.putString(PREF_id,pref_id);

            pref_email= globalclass.getEmail();
            editor.putString(PREF_email,pref_email);


            enquiry_base_id = globalclass.getEnquiry_base_id();
            editor.putString(Prefenquiry_base_id,enquiry_base_id);

            pref_phone_number= globalclass.getPhone_number();
            editor.putString(PREF_phone_number,pref_phone_number);

            role_ids= globalclass.getRole_ids();
            editor.putString(Pref_role_ids,role_ids);

            group_ids= globalclass.getGroup_ids();
            editor.putString(Pref_group_ids,group_ids);

            department_ids= globalclass.getDepartment_ids();
            editor.putString(PREF_department_ids,department_ids);

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
          //  globalclass.setRemote_user_id(remote_user_id);
            pref_name = sharedPreferences.getString(PREF_name, "");
            globalclass.setName(pref_name);
            islogin=sharedPreferences.getString(Islogiin,"");
            globalclass.setIslogin(islogin);
            pref_fname = sharedPreferences.getString(PREF_fname, "");
            globalclass.setFname(pref_fname);

            pref_lname = sharedPreferences.getString(PREF_lname, "");
            globalclass.setLname(pref_lname);

            pref_id= sharedPreferences.getString(PREF_id,"");
            globalclass.setId(pref_id);

            pref_phone_number=sharedPreferences.getString(PREF_phone_number,"");
            globalclass.setPhone_number(pref_phone_number);

            pref_email=sharedPreferences.getString(PREF_email,"");
            globalclass.setEmail(pref_email);


            enquiry_base_id=sharedPreferences.getString(Prefenquiry_base_id,"");
            globalclass.setEnquiry_base_id(enquiry_base_id);

            pref_organisation=sharedPreferences.getString(PREF_organisation,"");
          //  globalclass.setOrganization(pref_organisation);

            pref_profile_img=sharedPreferences.getString(PREF_profile_img,"");
            globalclass.setProfil_pic(pref_profile_img);


            role_ids=sharedPreferences.getString(Pref_role_ids,"");
            globalclass.setRole_ids(role_ids);


            group_ids=sharedPreferences.getString(Pref_group_ids,"");
            globalclass.setGroup_ids(group_ids);

            department_ids=sharedPreferences.getString(PREF_department_ids,"");
            globalclass.setDepartment_ids(department_ids);


            login_from=sharedPreferences.getString(PREF_login_from,"");
            globalclass.setLogin_from(login_from);


        }
    }

    public void clearPrefrence(){

        editor.clear();
        editor.commit();


    }













}
