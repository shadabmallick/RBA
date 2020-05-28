package com.film.rba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
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

import static com.film.rba.networkService.AppConfig.register;

public class RegistrationActivity extends AppCompatActivity {
    String TAG="register";
    ImageView iv_eye;
    EditText edt_name,edtPassword,edt_email,edt_mobile;
    TextView txt_login;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog progressDialog;
    boolean password_visible = false;
    String device_id;
    RelativeLayout rl_login;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);
        preference.loadPrefrence();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        iv_eye=findViewById(R.id.iv_eye);
        rl_login=findViewById(R.id.rl_login);
        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile=findViewById(R.id.edt_mobile);
        edtPassword=findViewById(R.id.edt_pass);
        txt_login=findViewById(R.id.txt_login);
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
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


        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String mobile = edt_mobile.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {

                    if (!edt_name.getText().toString().isEmpty()) {
                        if (isValidEmail(edt_email.getText().toString())) {

                                if (!edtPassword.getText().toString().isEmpty()) {
                                    if (!edt_mobile.getText().toString().isEmpty()) {


                                            Register(name,email,mobile,password);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Mobile is Empty", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_LONG).show();
                                }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Insert Proper mail id", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });


    }
    private void Register(final String name, final String email,String mobile,String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+register, new Response.Listener<String>() {


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

                        globalClass.setLogin_status(false);


                        preference.savePrefrence();


                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);                            startActivity(intent);




                    }
                    else if(status==false){
                        edtPassword.setText("");
                        edt_email.setText("");
                        edt_mobile.setText("");
                        edt_name.setText("");
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


                params.put("login_by", "facebook");
                params.put("email", email);
                params.put("password",password);
                params.put("device_token",device_id);
                params.put("device_type","android");
                params.put("name",name);
                params.put("mobile",mobile);
                params.put("social_unique_id","required if the social media");
                params.put("gender","");


                Log.d(TAG, "login param: "+params);
                return params;
            }


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
