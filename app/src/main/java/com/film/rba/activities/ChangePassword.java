package com.film.rba.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class ChangePassword extends AppCompatActivity {
    ImageView iv_eye1, iv_eye2;
    EditText edt_old_pass, edt_new_pass, edt_confirm_pass;
    RelativeLayout rl_submit;
    boolean password_visible1 = false;
    boolean password_visible2 = false;

    ProgressDialog progressDialog;
    GlobalClass globalClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initViews();

    }

    private void initViews(){

        globalClass = (GlobalClass) getApplicationContext();

        iv_eye1 = findViewById(R.id.iv_eye1);
        iv_eye2 = findViewById(R.id.iv_eye2);
        edt_old_pass = findViewById(R.id.edt_old_pass);
        edt_new_pass = findViewById(R.id.edt_new_pass);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        rl_submit = findViewById(R.id.rl_submit);


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));


        iv_eye1.setOnClickListener(v -> {

            if (password_visible1){
                edt_new_pass.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_eye1.setImageResource(R.mipmap.invisible);
                password_visible1 = false;
                edt_new_pass.setSelection(edt_new_pass.length());
            }else {
                edt_new_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_eye1.setImageResource(R.mipmap.invisible);
                password_visible1 = true;
                edt_new_pass.setSelection(edt_new_pass.length());
            }

        });

        iv_eye2.setOnClickListener(v -> {

            if (password_visible2){
                edt_confirm_pass.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_eye2.setImageResource(R.mipmap.invisible);
                password_visible2 = false;
                edt_confirm_pass.setSelection(edt_confirm_pass.length());
            }else {
                edt_confirm_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_eye2.setImageResource(R.mipmap.invisible);
                password_visible2 = true;
                edt_confirm_pass.setSelection(edt_confirm_pass.length());
            }
        });

        rl_submit.setOnClickListener(v -> {

            String new_pass = edt_new_pass.getText().toString();
            String new_con_pass = edt_confirm_pass.getText().toString();

            if (edt_old_pass.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your old password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (new_pass.trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your new password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!new_pass.equals(new_con_pass)){
                Toast.makeText(getApplicationContext(),
                        "Confirm password not match", Toast.LENGTH_SHORT).show();
                return;
            }



            changePassword();

        });

    }

    private void changePassword() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.change_password, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(AppConfig.TAG, "Response: " + response.toString());

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.optBoolean("success");

                    if(status)  {

                        String message = jsonObject.optString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        finish();
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

                params.put("id", globalClass.getId());
                params.put("old_password", edt_old_pass.getText().toString());
                params.put("password", edt_new_pass.getText().toString());
                params.put("confirm_password", edt_confirm_pass.getText().toString());
                params.put("token", globalClass.getToken());

                Log.d(AppConfig.TAG, "param: "+params);
                return params;
            }

        };

        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRY, backoffMultiplier));

    }


}
