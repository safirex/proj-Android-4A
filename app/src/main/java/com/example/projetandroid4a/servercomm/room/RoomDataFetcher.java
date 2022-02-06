package com.example.projetandroid4a.servercomm.room;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.HomePageActivity;
import com.example.projetandroid4a.R;
import com.example.projetandroid4a.Utils;
import com.example.projetandroid4a.servercomm.SRExternalClassAbs;
import com.example.projetandroid4a.servercomm.ServerTokenedResponseInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * app behavior upon server response of fetching of room data
 */
public class RoomDataFetcher extends SRExternalClassAbs implements ServerTokenedResponseInterface {

    public RoomDataFetcher(Activity acitivity) {
        super(acitivity);
    }

    public void onSuccessfulRequest(JSONObject response)  {
        try {
            // Récupération du tableau d'objet
            JSONArray roomRawData = response.getJSONArray("rooms");
            ArrayList<Room> roomList = new ArrayList<>();


            for(int count = 0; count < roomRawData.length(); ++count)
            {
                // On récupère les données de la pizza
                final JSONObject room = roomRawData.getJSONObject(count);
                // On ajoute les données à la liste des parfums
                roomList.add(new Room(
                        room.getInt("id"),
                        room.getString("name"),
                        room.getString("picture")
                ));
            }

            Utils.ShowInfo(roomRawData.getJSONObject(0).getString("name"),context);

            // Création d’un adaptateur permettant d’afficher les room dans un Spinner
            ArrayAdapter<Room> adapter = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_dropdown_item,
                    roomList
            );


            //set data to GUI
            Spinner spinner = activity.findViewById(R.id.spinner);
            spinner.setAdapter(adapter);

        }   catch (JSONException e) {
            e.printStackTrace();
        }

        HomePageActivity hp =   (HomePageActivity)(activity);
        hp.updateDeleteButtonState();
    }

    public void onFailedRequest(ANError anError) {
        Log.d("Debug", "onFailedRequest: "+ anError);
        Utils.ShowInfo("problm " + anError.getErrorCode(), context);
    }
}
