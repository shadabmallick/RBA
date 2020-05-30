package com.film.rba.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.GlobalClass;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.film.rba.networkService.AppConfig.RETRY;
import static com.film.rba.networkService.AppConfig.TIMEOUT;
import static com.film.rba.networkService.AppConfig.backoffMultiplier;

public class ForgetPassword extends AppCompatActivity {

    EditText edt_email;
    TextView txt_login;
    RelativeLayout rl_submit;

    ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        edt_email=findViewById(R.id.edt_email);
        rl_submit=findViewById(R.id.rl_submit);
        txt_login=findViewById(R.id.txt_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        txt_login.setOnClickListener(v -> {
            finish();
        });

        rl_submit.setOnClickListener(v -> {

            if (edt_email.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your registered email id", Toast.LENGTH_LONG).show();
                return;
            }

            forgotPassword();
        });


    }


    private void forgotPassword() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.forgot_password, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(AppConfig.TAG, "forgot_password Response: " + response.toString());

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.optBoolean("success");

                    if(status)  {

                        String message = jsonObject.optString("message");
                        dialog(message);

                    } else {
                        String error = jsonObject.optString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppConfig.TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", edt_email.getText().toString());

                Log.d(AppConfig.TAG, "param: "+params);
                return params;
            }


        };

        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY, backoffMultiplier));

    }


    private void dialog(String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
