package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    String              userConnexionToken;
    RoomDataFetcher     roomFetcher = new RoomDataFetcher(this);
    RoomDataOnChange    roomOnChange = new RoomDataOnChange(this);
    ServerConnection    server = ServerConnection.getInstance();

    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        userConnexionToken = intent.getStringExtra("token");
        Log.d( "Debug", "my token = "+userConnexionToken);

        //initialize the obj attrib
        spinner =  findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Room selectedRoom = (Room)spinner.getSelectedItem();
                fetchSensors(selectedRoom.getId());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        updateView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //update when back from creation activity
        updateView();

    }

    public void updateView(){
        spinner.setAdapter(null);
        fetchRooms();

        //disable button till successful fetch request
        updateDeleteButtonState();

    }
    public void updateDeleteButtonState(){
        Button deleteButton = findViewById(R.id.deleteRoomButton);
        Log.d("Debug", "updateDeleteButtonState: "+spinner.getAdapter());
        deleteButton.setEnabled (spinner.getAdapter() != null);
    }



    public void onCreateSensor(View view){
        Intent intent = new Intent(HomePageActivity.this, SensorCreationActivity.class);
        intent.putExtra("idRoom",((Room)spinner.getSelectedItem()).getId());
        startActivity(intent);
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
        Intent intent = new Intent(HomePageActivity.this, RoomCreationActivity.class);
        startActivity(intent);
    }


    public void deleteRooms(View view){
        String roomId = String.valueOf(((Room)spinner.getSelectedItem()).getId());

        AndroidNetworking.post(server.getRoomDeleteUrl())
                .addHeaders("Authorization","Bearer "+server.getConnexionToken())
                .addBodyParameter("idRoom",roomId)
                .build()
                .getAsOkHttpResponse(server.getResponseListener(this,roomOnChange));
    }


    public void fetchSensors(int idRoom){
        Log.d("Debug", server.getSensorUrl());
        AndroidNetworking.get(server.getSensorUrl())
                .addHeaders("Authorization","Bearer "+server.getConnexionToken())
                .addQueryParameter("idRoom",String.valueOf(idRoom))
                .build()
                .getAsJSONObject(server.getJSONRequestListener(roomFetcher));
    }



}