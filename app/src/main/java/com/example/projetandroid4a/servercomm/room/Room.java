package com.example.projetandroid4a.servercomm.room;

import android.media.Image;

public class Room {
    String name;
    String pictureUrl;
    int id;

    public Room(int id,String name){
        this.id=id;
        this.name=name;
    }

    public Room(int id,String name,String urlPicture){
        this.id=id;
        this.name=name;
        pictureUrl = urlPicture;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Room " + name;
    }
}
