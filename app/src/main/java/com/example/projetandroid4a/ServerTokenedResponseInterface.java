package com.example.projetandroid4a;

import com.androidnetworking.error.ANError;

import org.json.JSONException;
import org.json.JSONObject;

public interface ServerTokenedResponseInterface{
    void onSuccessfulRequest(JSONObject json) ;
    void onFailedRequest(ANError anError);
}