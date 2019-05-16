package com.example.astroweather;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroCalculator.SunInfo;
import com.astrocalculator.AstroCalculator.MoonInfo;
import com.astrocalculator.AstroDateTime;

public class InfoSet {

    private static InfoSet sTasksSet;
    private ArrayList<Info> mTasks;


    public static InfoSet get() {
        if (sTasksSet == null) {
            sTasksSet = new InfoSet();
        }

        return sTasksSet;
    }

    AstroDateTime dateTime = new AstroDateTime();
    AstroCalculator.Location location = new AstroCalculator.Location(57,26);
    AstroCalculator astro = new AstroCalculator(dateTime,location);
   // MoonInfo moon= new MoonInfo(astro,1,1,1,1,1,1,1);
   // double x =  moon.getAge();

    private InfoSet() {

        mTasks = new ArrayList<Info>();
        for (int i = 0; i < 2; i++) {
            Info info = new Info();
            info.setMoonRise("21:37");
            info.setMoonSet("22:37");
            info.setNewMoon("12:37");
            info.setFullMoon("5:37");
            info.setMonth("maj");
            info.setFaza("Chłosta");

            if(i==1){
                info.setMoonRise("1:37");
                info.setMoonSet("2:37");
                info.setNewMoon("3:37");
                info.setFullMoon("4:37");
                info.setMonth("Czwartek");
                info.setFaza("Zgon");

                info.setSunRise("4");
                info.setSunSet("22");
                info.setDown("3:33");
                info.setTwilight("20:31");
            }

            mTasks.add(info);
        }
    }

    public String getSunInfo(int id){
        String wynik="";

        if(id==4){

        }
        return wynik;
    }

    public ArrayList<Info> getZadania() {
        return mTasks;
    }


    public Info getZadanie(UUID id) {
        for (Info zadanie : mTasks) {
            if (zadanie.getUUID().equals(id)) {
                return zadanie;
            }
        }
        return null;
    }
}
