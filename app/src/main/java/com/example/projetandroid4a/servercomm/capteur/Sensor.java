package com.example.projetandroid4a.servercomm.capteur;

public class Sensor {
    int id;
    String name;
    String pictureUrl;

    public Sensor(int id,String name,String pictureUrl){
        this.id=id;
        this.name=name;
        this.pictureUrl=pictureUrl;

    }

    @Override
    public String toString() {
        return "Sensor : " + name ;
    }
}
