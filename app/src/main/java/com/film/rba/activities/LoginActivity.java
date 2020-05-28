package com.film.rba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static com.film.rba.networkService.AppConfig.login;

public class LoginActivity extends AppCompatActivity {
    String TAG="login";
    ImageView iv_eye;
    EditText edtUsername,edtPassword;
    TextView txt_register,password;
    boolean password_visible = false;
    RelativeLayout rl_login;
    ProgressDialog progressDialog;
    GlobalClass globalClass;
    Shared_Preference preference;
    String device_id;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
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

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(intent);
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(validateLogin(username, password)){
                    login(username, password);
                }
            }
        });

        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });

    }


    private void login(final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+login, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                progressDialog.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Boolean status = jobj.get("success").getAsBoolean();
                  //  String  user_image_url = jobj.get("user_image_url").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "status: "+status);

                    if(status==true)  {

                        String email = jobj.get("email").getAsString().replaceAll("\"", "");
                        String user_id = jobj.get("id").getAsString().replaceAll("\"", "");
                        String mobile = jobj.get("mobile").getAsString().replaceAll("\"", "");

                        String name = jobj.get("name").getAsString().replaceAll("\"", "");
                        String gender = jobj.get("gender").getAsString().replaceAll("\"", "");
                        String picture = jobj.get("picture").getAsString().replaceAll("\"", "");
                        String token = jobj.get("token").getAsString().replaceAll("\"", "");
                        String token_expiry = jobj.get("token_expiry").getAsString().replaceAll("\"", "");
                        String login_by = jobj.get("login_by").getAsString().replaceAll("\"", "");
                        String user_type = jobj.get("user_type").getAsString().replaceAll("\"", "");
                        String social_unique_id = jobj.get("social_unique_id").getAsString().replaceAll("\"", "");
                        String push_status = jobj.get("push_status").getAsString().replaceAll("\"", "");

                        globalClass.setEmail(email);
                        globalClass.setId(user_id);
                        globalClass.setName(name);
                        globalClass.setGender(gender);
                        globalClass.setToken_expiry(token_expiry);
                        globalClass.setPhone_number(mobile);
                        globalClass.setLogin_by(login_by);
                        globalClass.setFcm_token(token);
                        globalClass.setProfil_pic(picture);
                        globalClass.setUser_type(user_type);
                        globalClass.setSocial_unique(social_unique_id);
                        globalClass.setPush_status(push_status);

                        globalClass.setLogin_status(true);


                        preference.savePrefrence();

                        Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);




                    }
                    else if(status==false){
                        edtPassword.setText("");
                        edtUsername.setText("");
                        String error = jobj.get("error").getAsString().replaceAll("\"", "");

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
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
