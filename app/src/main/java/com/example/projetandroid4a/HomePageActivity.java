package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements ServerTokenedResponseInterface {

    String userConnexionToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        userConnexionToken = intent.getStringExtra("token");

        fetchRooms();
    }

    public void fetchRooms(){
        ServerConnection server = ServerConnection.getInstance();
        AndroidNetworking.get(server.getRoomUrl())
                .addHeaders("Authorization",userConnexionToken)
                .build()
                .getAsJSONObject(server.getJSONRequestListener(this,this));
    }



    public void onSuccessfulRequest(JSONObject response)  {
        try {
            // Récupération du tableau d'objet
            JSONArray flavours = response.getJSONArray("rooms");
            ArrayList<Room> flavourList = new ArrayList<>();


            for(int iFlavour = 0; iFlavour < flavours.length(); ++iFlavour)
            {
                // On récupère les données de la pizza
                final JSONObject flavour = flavours.getJSONObject(iFlavour);
                // On ajoute les données à la liste des parfums
                flavourList.add(new Room(
                        flavour.getInt("id"),
                        flavour.getString("name")
                ));
            }

            Utils.ShowInfo(flavours.getJSONObject(0).getString("name"),this);

            // Création d’un adaptateur permettant d’afficher les Flavour dans un Spinner
            ArrayAdapter<Room> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    flavourList
            );

            //set data to GUI
            Spinner spinner = findViewById(R.id.spinner);
            spinner.setAdapter(adapter);


        }   catch (JSONException e) {
                e.printStackTrace();
        }
    }

    public void onFailedRequest(ANError anError) {
        Utils.ShowInfo("problm " + anError.getErrorCode(), this);
    }
}