package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.example.projetandroid4a.servercomm.ServerConnection;
import com.example.projetandroid4a.servercomm.ServerResponseInterface;

public class RegistrationActivity extends AppCompatActivity implements ServerResponseInterface {
    ServerConnection server = ServerConnection.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onRegistrationButtonClick(View view){
        AndroidNetworking.initialize(this);
        registerUser();

    }

    private void registerUser(){
        String name = ((EditText)findViewById(R.id.editTextRegisterUserName)).getText().toString();
        String pass = ((EditText)findViewById(R.id.editTextRegisterPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.editTextRegisterEmail)).getText().toString();

        server.register(name,email,pass,RegistrationActivity.this,this);
    }


    public void onSuccessfulRequest() {

    }

    public void onFailedRequest() {

    }
}