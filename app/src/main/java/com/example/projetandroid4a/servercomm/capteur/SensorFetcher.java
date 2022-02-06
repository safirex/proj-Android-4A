package com.example.projetandroid4a.servercomm.capteur;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.R;
import com.example.projetandroid4a.Utils;
import com.example.projetandroid4a.servercomm.SRExternalClassAbs;
import com.example.projetandroid4a.servercomm.ServerTokenedResponseInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorFetcher extends SRExternalClassAbs implements ServerTokenedResponseInterface {

    public SensorFetcher(Activity acitivity) {
        super(acitivity);
    }

    @Override
    public void onSuccessfulRequest(JSONObject response) {
        try {
            JSONArray sensorRawData = response.getJSONArray("sensors");
            ArrayList<Sensor> sensorList = new ArrayList<>();


            for (int count = 0; count < sensorRawData.length(); ++count) {
                // On récupère les données de la pizza
                final JSONObject sensor = sensorRawData.getJSONObject(count);
                // On ajoute les données à la liste des parfums
                sensorList.add(new Sensor(
                        sensor.getInt("id"),
                        sensor.getString("name"),
                        sensor.getString("picture")
                ));
                Utils.ShowInfo(sensor.toString(),activity);
            }



            Utils.ShowInfo(sensorRawData.getJSONObject(0).getString("name"),context);

            // Création d’un adaptateur permettant d’afficher les sensor dans un Spinner
            ArrayAdapter<Sensor> adapter = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_dropdown_item,
                    sensorList
            );

            ListView listView = activity.findViewById(R.id.capteurListView);
            listView.setAdapter(adapter);


        }catch (Exception e){
            Utils.ShowInfo("error server response",activity);
        }
    }

    @Override
    public void onFailedRequest(ANError anError) {

    }
}
