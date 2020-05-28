package com.film.rba.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.HomeScreen;
import com.film.rba.activities.JustArrivedActvity;
import com.film.rba.activities.LoginActivity;
import com.film.rba.activities.SettingActvity;
import com.film.rba.adapter.MostViewed;
import com.film.rba.adapter.SearchAdapter;
import com.film.rba.model.Event;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.film.rba.networkService.AppConfig.login;
import static com.film.rba.networkService.AppConfig.logouta;

public class SettingFragment extends Fragment  {
    View view;
    TextView txt_setting;
    ImageView img_profile,back;
    LinearLayout ll_profile,ll_setting;
    TextView logout;

   // private List<notice> groceryList = new ArrayList<>();
    private List<Event> groceryList1 = new ArrayList<>();
    private RecyclerView groceryRecyclerView,recyclerView,recyclerView2;
    private MostViewed groceryAdapter;
    private SearchAdapter eventAdapter;
    Toolbar toolbar;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;
    GridView androidGridView;
    TabLayout tabs;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        txt_setting=view.findViewById(R.id.txt_setting);
        logout=view.findViewById(R.id.logout);
        txt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show_more=new Intent(getActivity(), SettingActvity.class);
                startActivity(show_more);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });




        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }
    private void logout() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+logouta, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                progressDialog.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Boolean status = jobj.get("success").getAsBoolean();
                      String  message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "status: "+status);

                    if(status==true)  {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        preference.clearPrefrence();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);




                    }
                    else if(status==false){

                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }






                }catch (Exception e) {

                    Toast.makeText(getActivity(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {



            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection Error", Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("email",globalClass.getEmail());

                Log.d(TAG, "login param: " + params);
                return params;

            }


        };
        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}