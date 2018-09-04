package com.example.admin1.dbapplication.interfaces;

import com.android.volley.NetworkResponse;
import com.example.admin1.dbapplication.webservices.JsonResponse;

public interface ApiServiceCaller {

    void onAsyncSuccess(JsonResponse jsonResponse, String label);
    void onAsyncFail(String message, String label, NetworkResponse response);
    void onAsyncCompletelyFail(String message, String label);
}
