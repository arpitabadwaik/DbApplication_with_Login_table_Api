package com.example.admin1.dbapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable {

    @SerializedName("city")
    public String city;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("email_id")
    public String email_id;

    @SerializedName("profile_image")
    public String profile_image;

    @SerializedName("emp_type")
    public String emp_type;

    @SerializedName("contact_no")
    public String contact_no;

    @SerializedName("token")
    public String token;

    @SerializedName("address")
    public String address;

    @SerializedName("emp_id")
    public String emp_id;

    @SerializedName("user_name")
    public String user_name;

    public String getCity() {
        return city;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getEmp_type() {
        return emp_type;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getToken() {
        return token;
    }

    public String getAddress() {
        return address;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public String getUser_name() {
        return user_name;
    }
}
