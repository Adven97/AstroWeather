package com.example.astroweather;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Info implements Serializable {

    private String moonRise;
    private String moonSet;
    private String newMoon;
    private String fullMoon;
    private String month;
    private String faza;


    private String sunRise;
    private String sunSet;
    private String twilight;
    private String down;

    private UUID mUUID;

    public Info()
    {
        mUUID = UUID.randomUUID();
       // mDateReg = new Date();
    }


    public UUID getUUID() {
        return mUUID;
    }

    ////////////////////MOON///////////////

    public String getMoonRise() {
        return moonRise;
    }

    public void setMoonRise(String title) {
        moonRise = title;
    }

    public String getMoonRSet() {
        return moonSet;
    }

    public void setMoonSet(String title) {
        moonSet = title;
    }

    public String getNewMoon() {
        return newMoon;
    }

    public void setNewMoon(String title) {
        newMoon = title;
    }

    public String getFullMoon() {
        return fullMoon;
    }

    public void setFullMoon(String title) {
        fullMoon = title;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String title) {
        month = title;
    }

    public String getFaza() {
        return faza;
    }

    public void setFaza(String title) {
        faza = title;
    }


    ///////////////SUN ///////////////////////////


    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String title) {
        sunRise = title;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String title) {
        sunSet = title;
    }

    public String getTwilight() {
        return twilight;
    }

    public void setTwilight(String title) {
        twilight = title;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String title) {
        down = title;
    }


}

