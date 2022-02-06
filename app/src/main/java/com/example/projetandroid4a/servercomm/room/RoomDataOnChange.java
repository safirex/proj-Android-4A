package com.example.projetandroid4a.servercomm.room;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.HomePageActivity;
import com.example.projetandroid4a.R;
import com.example.projetandroid4a.servercomm.SRExternalClassAbs;
import com.example.projetandroid4a.servercomm.ServerResponseInterface;
import com.example.projetandroid4a.servercomm.ServerTokenedResponseInterface;

import org.json.JSONObject;

public class RoomDataOnChange extends SRExternalClassAbs implements ServerResponseInterface {

    public RoomDataOnChange(Activity acitivity) {
        super(acitivity);
    }

    @Override
    public void onSuccessfulRequest() {
        Log.d("Debug", "onSuccessfulRequest: modification was succesful");
        HomePageActivity hp =   (HomePageActivity)(activity);
        hp.updateView();



    }

    @Override
    public void onFailedRequest() {
        Log.d("Debug", "onFailedRequest: modification yeeted");
    }
}
