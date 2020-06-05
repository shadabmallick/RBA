package com.film.rba.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.activities.ChangePassword;
import com.film.rba.activities.LoginActivity;
import com.film.rba.activities.SettingActvity;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingFragment extends Fragment  {

    private TextView txt_setting;
    private CardView card_change_pass, card_logout, card_privacy, card_terms,
            card_about_us, card_faq, card_contact_us;

    private GlobalClass globalClass;
    private Shared_Preference preference;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view){

        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        txt_setting=view.findViewById(R.id.txt_setting);
        card_logout=view.findViewById(R.id.card_logout);
        card_change_pass=view.findViewById(R.id.card_change_pass);

        card_privacy=view.findViewById(R.id.card_privacy);
        card_terms=view.findViewById(R.id.card_terms);
        card_about_us=view.findViewById(R.id.card_about_us);
        card_faq=view.findViewById(R.id.card_faq);
        card_contact_us=view.findViewById(R.id.card_contact_us);


        txt_setting.setVisibility(View.INVISIBLE);
        txt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show_more=new Intent(getActivity(), SettingActvity.class);
                startActivity(show_more);
            }
        });

        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        card_change_pass.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        card_privacy.setOnClickListener(v -> {

            String url = AppConfig.PRIVACY_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });

        card_terms.setOnClickListener(v -> {

            String url = AppConfig.TERMS_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });

        card_about_us.setOnClickListener(v -> {

            String url = AppConfig.ABOUT_US_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });

        card_faq.setOnClickListener(v -> {

            String url = AppConfig.FAQ_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });

        card_contact_us.setOnClickListener(v -> {

            String url = AppConfig.CONTACT_US_URL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });


    }



    @Override
    public void onResume() {
        super.onResume();

    }

    private void dialogLogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure you want to logout?");
        builder1.setCancelable(false);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        logout();
                    }
                });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    private void logout() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.logout, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "logout Response: " + response.toString());

                progressDialog.dismiss();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Boolean status = jobj.get("success").getAsBoolean();
                    String  message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "status: "+status);

                    if(status==true)  {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        preference.clearPrefrence();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);
                        getActivity().finish();

                    } else if(status==false){

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
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

}