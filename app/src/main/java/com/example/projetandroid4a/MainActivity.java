package com.example.projetandroid4a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;


import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements  ServerResponseInterface{
    ServerConnection server = ServerConnection.getInstance();
    EditText pass;
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);

        pass = findViewById(R.id.editTextTextPassword);
        name = findViewById(R.id.editTextTextPersonName);
    }


    public void sendConnexionData(){
        String nom = name.getText().toString();
        String passw = pass.getText().toString();
        server.logIn(nom,passw,this,this);
    }



    ///////////////////// Actions on server response ////////////////////////
    public void onSuccessfulRequest() {
        Intent connexionIntent = new Intent(MainActivity.this,HomePageActivity.class);
        startActivity(connexionIntent);
    }

    public void onFailedRequest() {

    }




    /////////////////  GUI Interaction functions /////////////////////

    public void onConnexionButtonClick(View view){
        sendConnexionData();

    }

    public void onRegisterButtonClick(View view){
        Intent registrationIntent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(registrationIntent);
    }

}