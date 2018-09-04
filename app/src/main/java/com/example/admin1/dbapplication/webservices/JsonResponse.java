package com.example.admin1.dbapplication.webservices;

import com.example.admin1.dbapplication.models.LoginData;

public class JsonResponse {

    public static String SUCCESS = "success";
    public static String FAILURE = "failure";

    public String result;
    public String message;

    public LoginData responsedata;
}
