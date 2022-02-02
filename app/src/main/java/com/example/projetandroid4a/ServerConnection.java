package com.example.projetandroid4a;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.example.projetandroid4a.models.Room;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Function;

import okhttp3.Response;
/*
 connexion to the REST API
 */
public class ServerConnection {


    private static ServerConnection instance;
    private String rootUrl = "https://myhouse.lesmoulinsdudev.com";


    private void ServerConnection(){}

    /**
     * generate the instance with parameter, should be called only once for initiation
     * @return
     */
    public static ServerConnection generateInstance(String rootUrl){
        instance =  new ServerConnection();
        instance.rootUrl = rootUrl;
        return instance;
    }

    public static ServerConnection getInstance(){
        if(instance == null)
            instance = new ServerConnection();
        return instance;
    }

    public String getAuthUrl()          { return rootUrl+"/auth"        ;}
    public String getRegisterUrl()      { return rootUrl+"/register"    ;}
    public String getRoomUrl()          { return rootUrl+"/rooms"       ;}
    public String getRoomCreateUrl()    { return rootUrl+"/room-create" ;}
    public String getRoomDeleteUrl()    { return rootUrl+"/room-delete" ;}

    /**
     * Send user credentials for connexion to server
     * @param login email of the account
     * @param password
     * @param context
     * @param sr
     */
    public void logIn(String login,String password,Context context,ServerTokenedResponseInterface sr){
        AndroidNetworking.post(getAuthUrl())
                .addBodyParameter("login",login)
                .addBodyParameter("password",password)
                .build()
                .getAsJSONObject(getJSONRequestListener(context,sr));
    }

    /**
     * Send user credentials to register an account on the server
     * @param name name of the house
     * @param login email of the account
     * @param password
     * @param context
     * @param srAction
     */
    public void register(String name,String login,String password,Context context, ServerResponseInterface srAction){
        AndroidNetworking.post(getRegisterUrl())
                .addBodyParameter("name",name)
                .addBodyParameter("login",login)
                .addBodyParameter("password",password)
                .build()
                .getAsOkHttpResponse(getResponseListener(context,srAction));
    }

//    private String responseCodeManager(okhttp3.Response response ){}

    public JSONObjectRequestListener getJSONRequestListener(Context context, ServerTokenedResponseInterface sri){
        return new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                sri.onSuccessfulRequest(response);
            }

            @Override
            public void onError(ANError anError) {
                sri.onFailedRequest( anError);
            }
        };
    }

    public ArrayList<Room> fetchRooms(){

        return null;
    }

    public OkHttpResponseListener getResponseListener(Context context, ServerResponseInterface rpActions){
        return new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response)  {
                Toast toast;

                switch (response.code()){
                    case 200:
                        //ok
                        toast = Toast.makeText(context,"server rep ok",Toast.LENGTH_SHORT);
                        toast.show();
                        rpActions.onSuccessfulRequest();
                        break;

                    case 401:
                        //problem enregistrement
                        toast = Toast.makeText(context,"compte inconnu",Toast.LENGTH_SHORT);
                        toast.show();
                        rpActions.onFailedRequest();
                        break;

                    case 400:
                        //problem requete
                        toast = Toast.makeText(context,"err request",Toast.LENGTH_SHORT);
                        toast.show();
                        rpActions.onFailedRequest();
                        break;

                    case 500:
                        //problem enregistrement
                        toast = Toast.makeText(context,"err enregistrement",Toast.LENGTH_SHORT);
                        toast.show();
                        rpActions.onFailedRequest();
                        break;

                    default:
                        toast = Toast.makeText(context,"server mad lol",Toast.LENGTH_SHORT);
                        toast.show();
                        rpActions.onFailedRequest();
                        //problem
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast toast = Toast.makeText(context,"big comm problem",Toast.LENGTH_SHORT);
                toast.show();
                rpActions.onFailedRequest();
            }
        };
    }



}
