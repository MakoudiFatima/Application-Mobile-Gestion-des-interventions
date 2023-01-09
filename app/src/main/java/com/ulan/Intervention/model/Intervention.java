package com.ulan.Intervention.model;


import android.widget.RadioGroup;

public class Intervention {
    private String type, client, time, date, number,adresse,prix, recette, status;
    private int id, color;

    public Intervention() {}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Intervention(String type, String client, String number, String adresse, String prix, String recette, String status, String time, String date, int color) {
        this.type = type;
        this.client = client;
        this.number = number;
        this.adresse = adresse;
        this.prix = prix;
        this.time = time;
        this.date = date;
        this.color = color;
    }




    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getAddress() {
        return adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String adresse) {
        this.adresse = adresse;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
