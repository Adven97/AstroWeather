package com.example.astroweather;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class WeatherInfo implements Serializable {

    private String miasto;
    private double temperatura;
    private int cis;
    private int wilgotnosc;
    private String opis;
    private String kraj;
    private double latitude;
    private double longtitude;

    private double windSpeed;
    private double windDeg;
    private int zachm;


    private UUID mUUID2;

    public WeatherInfo()
    {
        mUUID2 = UUID.randomUUID();
        // mDateReg = new Date();
    }


    public UUID getUUID() {
        return mUUID2;
    }

    ////////////////////MOON///////////////

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String title) {
        miasto = title;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double title) {
        temperatura = title;
    }

    public int getCis() {
        return cis;
    }

    public void setCis(int title) {
        cis = title;
    }


    public int getWilgotnosc() {
        return wilgotnosc;
    }

    public void setWilgotnosc(int title) {
        wilgotnosc = title;
    }



    public String getOpis() {
        return opis;
    }

    public void setOpis(String title) {
        opis = title;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String title) {
        kraj = title;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double title) {
        latitude = title;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double title) {
        longtitude = title;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double title) {
        windSpeed = title;
    }


    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double title) {
        windDeg = title;
    }

    public int getZachm() {
        return zachm;
    }

    public void setZachm(int title) {
        zachm = title;
    }
}

