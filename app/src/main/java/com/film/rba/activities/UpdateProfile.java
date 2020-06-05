package com.film.rba.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.film.rba.R;
import com.film.rba.networkService.AppConfig;
import com.film.rba.util.Commons;
import com.film.rba.util.GlobalClass;
import com.film.rba.util.Shared_Preference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.film.rba.networkService.AppConfig.RETRY;
import static com.film.rba.networkService.AppConfig.TIMEOUT;
import static com.film.rba.networkService.AppConfig.backoffMultiplier;

public class UpdateProfile extends AppCompatActivity {

    CircularImageView img_profile;
    ImageView iv_edit_name, iv_edit_phone;
    EditText edt_name, edt_email, edt_mobile;
    RelativeLayout rel_camera, rl_update;

    ProgressDialog progressDialog;
    GlobalClass globalClass;

    private final int PICK_IMAGE_CAMERA = 11, PICK_IMAGE_GALLERY = 22;
    private File p_image;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile=findViewById(R.id.edt_mobile);
        rl_update=findViewById(R.id.rl_update);
        rel_camera=findViewById(R.id.rel_camera);
        img_profile=findViewById(R.id.img_profile);
        iv_edit_name=findViewById(R.id.iv_edit_name);
        iv_edit_phone=findViewById(R.id.iv_edit_phone);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        globalClass = (GlobalClass) getApplicationContext();

        edt_name.setEnabled(false);
        edt_email.setEnabled(false);
        edt_mobile.setEnabled(false);

        edt_name.setText(globalClass.getName());
        edt_email.setText(globalClass.getEmail());
        edt_mobile.setText(globalClass.getPhone_number());
        Picasso.get().load(globalClass.getProfil_pic()).into(img_profile);

        iv_edit_name.setOnClickListener(v -> {
            edt_name.setEnabled(true);
            edt_name.setSelection(edt_name.length());
        });

        iv_edit_phone.setOnClickListener(v -> {
            edt_mobile.setEnabled(true);
            edt_mobile.setSelection(edt_name.length());
        });

        rel_camera.setOnClickListener(v -> {
            if (checkPermission()){
                dialogSelect();
            }
        });

        rl_update.setOnClickListener(v -> {

            if (edt_name.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your name", Toast.LENGTH_LONG).show();
                return;
            }

            if (edt_email.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your email id", Toast.LENGTH_LONG).show();
                return;
            }

            if (edt_mobile.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),
                        "Enter your mobile", Toast.LENGTH_LONG).show();
                return;
            }

            updateProfile();

        });


    }


    private boolean checkPermission() {

        List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(UpdateProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(UpdateProfile.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(UpdateProfile.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) UpdateProfile.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    121);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 121:
                if (permissions.length == 1 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED
                        ||
                        (permissions.length == 2 && grantResults[0]
                                == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)){

                }

        }
    }


    private void dialogSelect() {

        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setCancelable(false);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (which == 1) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (which == 2) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        p_image = null;
        /// profile ...
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                writeBitmap(bitmap, img_profile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {

            try {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                writeBitmap(bitmap, img_profile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void writeBitmap(Bitmap bitmap, CircularImageView imageView){

        bitmap = Commons.getResizedBitmap(bitmap, 480, 520);

        final String dir = Commons.getFolderDirectoryImage();

        File file = new File(dir);
        if (!file.exists())
            file.mkdir();


        String files = dir + "/P_"+System.currentTimeMillis() +".jpg";
        File newfile = new File(files);

        try {

            imageView.setImageBitmap(bitmap);

            newfile.delete();
            OutputStream outFile = null;
            try {

                p_image = newfile;

                Log.d(Commons.TAG, "file = "+p_image);

                outFile = new FileOutputStream(newfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
                outFile.flush();
                outFile.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void updateProfile(){

        progressDialog.show();

        String url = AppConfig.update_profile;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id", globalClass.getId());
        params.put("name", edt_name.getText().toString());
        params.put("mobile", edt_mobile.getText().toString());
        params.put("token", globalClass.getToken());

        try{
            params.put("picture", p_image);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        Log.d(Commons.TAG , "URL = "+url);
        Log.d(Commons.TAG , "PARAM = "+params.toString());


        client.setSSLSocketFactory(
                new SSLSocketFactory(Commons.getSslContext(),
                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

        client.setMaxRetriesAndTimeout(Commons.MAX_RETRIES , Commons.TIMEOUT);
        client.post(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(Commons.TAG, "profile_update- " + response.toString());
                    try {

                        progressDialog.dismiss();
                        boolean status = response.optBoolean("success");

                        if (status) {

                            String name = response.optString("name");
                            String mobile = response.optString("mobile");
                            String gender = response.optString("gender");
                            String email = response.optString("email");
                            String picture = response.optString("picture");
                            String token = response.optString("token");

                            globalClass.setName(name);
                            globalClass.setPhone_number(mobile);

                            if (!picture.isEmpty()){
                                globalClass.setProfil_pic(AppConfig.image_url+picture);
                            }

                            Shared_Preference shared_preference =
                                    new Shared_Preference(UpdateProfile.this);
                            shared_preference.savePrefrence();

                            Toast.makeText(getApplicationContext(),
                                    "Update successfully", Toast.LENGTH_LONG).show();

                        } else{
                            Toast.makeText(getApplicationContext(),
                                    "Something wrong", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
                progressDialog.dismiss();

            }
        });


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
