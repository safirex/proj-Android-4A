package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.example.projetandroid4a.servercomm.SRExternalClassAbs;
import com.example.projetandroid4a.servercomm.ServerConnection;
import com.example.projetandroid4a.servercomm.ServerResponseInterface;
import com.example.projetandroid4a.servercomm.ServerTokenedResponseInterface;

import org.json.JSONObject;

public class SensorCreationActivity extends AppCompatActivity {
    ServerConnection server = ServerConnection.getInstance();
    SensorCreationRequestBehavior creationBehavior = new SensorCreationRequestBehavior(this);
    String idRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_creation);

        Intent intent = getIntent();
        idRoom = intent.getStringExtra("token");
    }

    public void onCreateSensor(View view){
        String name = ((EditText)findViewById(R.id.editTextSensorName)).getText().toString();
        AndroidNetworking.post(server.getSensorCreateUrl())
                .addHeaders("Authorization","Bearer "+server.getConnexionToken())
                .addBodyParameter("name",name)
                .addBodyParameter("idSensorType","1")
                .addBodyParameter("idRoom",idRoom)
                .build()
                .getAsOkHttpResponse(server.getResponseListener(this,creationBehavior));
    }

    public void onCancelCreation(View view){
        finish();
    }


}

class SensorCreationRequestBehavior extends SRExternalClassAbs implements ServerResponseInterface {
    public SensorCreationRequestBehavior(Activity acitivity) {
        super(acitivity);
    }

    @Override
    public void onSuccessfulRequest() {
        activity.finish();
    }

    @Override
    public void onFailedRequest() {

    }
}