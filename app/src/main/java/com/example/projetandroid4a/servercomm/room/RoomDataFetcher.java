package com.example.projetandroid4a.servercomm.room;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidnetworking.error.ANError;
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
            JSONArray flavours = response.getJSONArray("rooms");
            ArrayList<Room> flavourList = new ArrayList<>();


            for(int iFlavour = 0; iFlavour < flavours.length(); ++iFlavour)
            {
                // On récupère les données de la pizza
                final JSONObject flavour = flavours.getJSONObject(iFlavour);
                // On ajoute les données à la liste des parfums
                flavourList.add(new Room(
                        flavour.getInt("id"),
                        flavour.getString("name"),
                        flavour.getString("picture")
                ));
            }

            Utils.ShowInfo(flavours.getJSONObject(0).getString("name"),context);

            // Création d’un adaptateur permettant d’afficher les Flavour dans un Spinner
            ArrayAdapter<Room> adapter = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_dropdown_item,
                    flavourList
            );


            //set data to GUI

            Spinner spinner = activity.findViewById(R.id.spinner);

            spinner.setAdapter(adapter);
        }   catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onFailedRequest(ANError anError) {
        Log.d("Debug", "onFailedRequest: "+ anError);
        Utils.ShowInfo("problm " + anError.getErrorCode(), context);
    }
}
