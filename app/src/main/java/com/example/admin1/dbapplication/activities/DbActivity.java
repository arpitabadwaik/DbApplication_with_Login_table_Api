package com.example.admin1.dbapplication.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin1.dbapplication.R;
import com.example.admin1.dbapplication.db.DatabaseManager;
import com.example.admin1.dbapplication.db.DatabaseProvider;
import com.example.admin1.dbapplication.interfaces.ApiServiceCaller;
import com.example.admin1.dbapplication.models.LoginData;
import com.example.admin1.dbapplication.utilities.App;
import com.example.admin1.dbapplication.webservices.ApiConstants;
import com.example.admin1.dbapplication.webservices.JsonResponse;
import com.example.admin1.dbapplication.webservices.WebRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class DbActivity extends AppCompatActivity implements ApiServiceCaller, View.OnClickListener{

    Context context;

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private Button btnUpdate;
    private Button btnDelete;

    String userId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        context = this;

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        btnLogin.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    private void getLoginData(){
        try {
        /*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String imeiNumber = telephonyManager.getDeviceId(); */
        String imeiNumber="911555000227422";

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", userId);
                jsonObject.put("password", password);
                jsonObject.put("imei_no", imeiNumber);
                JsonObjectRequest request = WebRequest.callPostMethod(context, "", jsonObject, Request.Method.POST, ApiConstants.LOGIN_URL,
                        ApiConstants.LOGIN, this, "");
                App.getInstance().addToRequestQueue(request, ApiConstants.LOGIN);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        if (label == ApiConstants.LOGIN){
            if (jsonResponse != null) {
                if (jsonResponse.SUCCESS != null && jsonResponse.result.equals(jsonResponse.SUCCESS)) {
                    if (jsonResponse.responsedata != null) {
                        LoginData loginData = new LoginData();
                        loginData.user_id = jsonResponse.responsedata.getUser_id();
                        loginData.city = jsonResponse.responsedata.getCity();
                        loginData.contact_no = jsonResponse.responsedata.getContact_no();
                        loginData.user_name = jsonResponse.responsedata.getUser_name();
                        loginData.email_id = jsonResponse.responsedata.getEmail_id();
                        loginData.address = jsonResponse.responsedata.getAddress();
                        loginData.emp_type = jsonResponse.responsedata.getEmp_type();
                        loginData.address = jsonResponse.responsedata.getAddress();
                        loginData.profile_image = jsonResponse.responsedata.getProfile_image();
                        loginData.token = jsonResponse.responsedata.getToken();
                        loginData.emp_id = jsonResponse.responsedata.getEmp_id();

                        // Insert data in to DB
                        DatabaseManager.saveLoginDetails(context, loginData);

                        //Select data from DB
                        LoginData log = new LoginData();
                        log = DatabaseManager.getData(userId);

                        // Print selected data through model object
                        Log.i("ccccccccc",""+log.getUser_id());
                        Log.i("id",""+log.getEmp_id());
                        Log.i("type",""+log.getEmp_type());

                        /*Update data of DB
                        DatabaseManager.updateData(userId, "Accepted");*/


                        Toast.makeText(context, "You have Login Successfully",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAsyncCompletelyFail(String message, String label) {
        Toast.makeText(context, "Completely Fail", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            userId = edtUsername.getText().toString().trim();
            password = edtPassword.getText().toString().trim();
            getLoginData();

        }
        else if (v == btnUpdate) {
            userId = edtUsername.getText().toString().trim();
            DatabaseManager.updateData(userId, "Accepted");
            Toast.makeText(context, "Data updated Successfully" ,Toast.LENGTH_SHORT).show();
        }
        else
            if (v == btnDelete){
                userId = edtUsername.getText().toString().trim();
                DatabaseManager.deleteData(context, userId);
                Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
            }
    }
}
