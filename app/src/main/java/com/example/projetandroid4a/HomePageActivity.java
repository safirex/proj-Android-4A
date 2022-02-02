package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.servercomm.room.Room;
import com.example.projetandroid4a.servercomm.ServerConnection;
import com.example.projetandroid4a.servercomm.ServerTokenedResponseInterface;
import com.example.projetandroid4a.servercomm.room.RoomDataFetcher;
import com.example.projetandroid4a.servercomm.room.RoomDataOnChange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity  {

    String userConnexionToken;
    RoomDataFetcher roomFetcher = new RoomDataFetcher(this);
    RoomDataOnChange roomOnChange = new RoomDataOnChange(this);
    ServerConnection server = ServerConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        userConnexionToken = intent.getStringExtra("token");
        Log.d( "Debug", "my token = "+userConnexionToken);
        fetchRooms();
    }

    public void fetchRooms(){
        Log.d("Debug", server.getRoomUrl());
        Log.d("Debug", "fetchRooms: " + userConnexionToken);
        AndroidNetworking.get(server.getRoomUrl())
                .addHeaders("Authorization","Bearer "+userConnexionToken)
                .build()
                .getAsJSONObject(server.getJSONRequestListener(roomFetcher));
    }

    public void createRooms(View view){
        AndroidNetworking.post(server.getRoomCreateUrl())
                .addHeaders("Authorization","Bearer "+userConnexionToken)
                .addBodyParameter("name","test")
                .addBodyParameter("idPicture","1")
                .build()
                .getAsOkHttpResponse(server.getResponseListener(this,roomOnChange));
    }


    public void deleteRooms(View view){
        AndroidNetworking.post(server.getRoomCreateUrl())
                .addHeaders("Authorization","Bearer "+userConnexionToken)
                .addBodyParameter("name","test")
                .addBodyParameter("idPicture","1")
                .build()
                .getAsOkHttpResponse(server.getResponseListener(this,roomOnChange));
    }



    public void updateView(){
        fetchRooms();
    }



}