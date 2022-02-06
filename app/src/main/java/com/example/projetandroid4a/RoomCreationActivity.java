package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.example.projetandroid4a.servercomm.SRExternalClassAbs;
import com.example.projetandroid4a.servercomm.ServerConnection;
import com.example.projetandroid4a.servercomm.ServerResponseInterface;
import com.example.projetandroid4a.servercomm.room.RoomDataOnChange;

public class RoomCreationActivity extends AppCompatActivity {
    private ServerConnection server = ServerConnection.getInstance();
    private RoomCreationBehavior creationBehavior = new RoomCreationBehavior(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_creation);
    }

    public void onCreateRoom(View view){
        String name = ((EditText)findViewById(R.id.editTextCreateRoom)).getText().toString();
        AndroidNetworking.post(server.getRoomCreateUrl())
                .addHeaders("Authorization","Bearer "+server.getConnexionToken())
                .addBodyParameter("name",name)
                .addBodyParameter("idPicture","1")
                .build()
                .getAsOkHttpResponse(server.getResponseListener(this,creationBehavior));
    }

    public void onCancelCreation(View view){
        finish();
    }
}




// behavior upon request response
class RoomCreationBehavior extends SRExternalClassAbs implements ServerResponseInterface {
    public RoomCreationBehavior(Activity acitivity) {
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