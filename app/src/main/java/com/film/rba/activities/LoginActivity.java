package com.film.rba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.film.rba.util.Shared_Preference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String TAG="login";
    ImageView iv_eye;
    EditText edtUsername, edtPassword;
    TextView txt_register, password;
    boolean password_visible = false;
    RelativeLayout rl_login;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    String device_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        iv_eye=findViewById(R.id.iv_eye);
        edtUsername=findViewById(R.id.edt_fullname);
        edtPassword=findViewById(R.id.edt_pass);
        txt_register=findViewById(R.id.txt_register);
        rl_login=findViewById(R.id.rl_login);
        password=findViewById(R.id.password);

        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        txt_register.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
            startActivity(intent);
        });

        password.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),ForgetPassword.class);
            startActivity(intent);
        });

        rl_login.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            if(validateLogin(username, password)){
                login(username, password);
            }
        });

        iv_eye.setOnClickListener(v -> {

            if (!password_visible) {

                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_eye.setImageResource(R.mipmap.invisible);

                password_visible = true;

            } else {

                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_eye.setImageResource(R.mipmap.invisible);

                password_visible = false;

            }

            edtPassword.setSelection(edtPassword.length());

        });


    }


    private void login(final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                progressDialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    boolean status = jsonObject.optBoolean("success");

                    Log.d(TAG, "status: "+status);

                    if(status)  {

                        String email = jsonObject.optString("email");
                        String user_id = jsonObject.optString("id");
                        String mobile = jsonObject.optString("mobile");

                        String name = jsonObject.optString("name");
                        String gender = jsonObject.optString("gender");
                        String picture = AppConfig.image_url+jsonObject.optString("picture");
                        String token = jsonObject.optString("token");
                        String token_expiry = jsonObject.optString("token_expiry");
                        String login_by = jsonObject.optString("login_by");
                        String user_type = jsonObject.optString("user_type");
                        String social_unique_id = jsonObject.optString("social_unique_id");
                        String push_status = jsonObject.optString("push_status");

                        globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setName(name);
                        globalClass.setGender(gender);
                        globalClass.setToken_expiry(token_expiry);
                        globalClass.setPhone_number(mobile);
                        globalClass.setLogin_by(login_by);
                        globalClass.setToken(token);
                        globalClass.setProfil_pic(picture);
                        globalClass.setUser_type(user_type);
                        globalClass.setSocial_unique(social_unique_id);
                        globalClass.setPush_status(push_status);

                        globalClass.setLogin_status(true);
                        preference.savePrefrence();

                        Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        edtPassword.setText("");
                        edtUsername.setText("");
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
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("password", password);
                params.put("email", user_email);
                params.put("device_type", "android");
                params.put("device_token", device_id);
                params.put("login_by", "manual");

                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }
    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(getApplicationContext(),
                    "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(getApplicationContext(),
                    "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
