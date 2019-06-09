package com.example.astroweather;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroCalculator.SunInfo;
import com.astrocalculator.AstroCalculator.MoonInfo;
import com.astrocalculator.AstroDateTime;

//import static com.example.astroweather.AstroActivity.timeNow;

public class InfoSet {

    private static InfoSet sTasksSet;
    private ArrayList<Info> mTasks;

    //public static double longtitudeValue;
  //  public static double latitudeValue;
    public double refreshTimeValue= 10.0;

    static Calendar cal = AstroActivity.cal;

    public static InfoSet get() {
        if (sTasksSet == null || SettingsActivity.currentLat != SettingsActivity.latitudeValue || SettingsActivity.currentLomg != SettingsActivity.longtitudeValue ) {
            sTasksSet = new InfoSet(SettingsActivity.latitudeValue,SettingsActivity.longtitudeValue,cal);
        }
        if(AstroActivity.timesUp){
            cal = AstroActivity.cal;
            sTasksSet = new InfoSet(SettingsActivity.latitudeValue,SettingsActivity.longtitudeValue,cal);
        }
        return sTasksSet;
    }
    //Date dt =timeNow;


    private InfoSet(double longti, double lat, Calendar cal) {



        AstroDateTime dateTime = new AstroDateTime(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND),2,false);
        //AstroDateTime dateTime = new AstroDateTime(2019,6,8,20,7,1,2,false);
        AstroCalculator.Location location = new AstroCalculator.Location(lat,longti);
        AstroCalculator astro = new AstroCalculator(dateTime,location);

        Log.d("tag","Data z calendara: "+cal.get(Calendar.YEAR)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DAY_OF_MONTH)+" , "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));

       // longtitudeValue=SettingsActivity.longtitudeValue;
      //  latitudeValue= SettingsActivity.latitudeValue;
        Log.d("tag","****************************************************************   Wspolzedne  :::::::::: "+longti+" , "+lat);


        mTasks = new ArrayList<Info>();
        for (int i = 0; i < 2; i++) {
            Info info = new Info();

            if(i==0){
                getMoonInfo(info,astro);
            }

            if(i==1){
                getSunInfo(info,astro);

            }

            mTasks.add(info);
        }
    }


    public void getMoonInfo(Info info, AstroCalculator astro){

        int moonriseHour = astro.getMoonInfo().getMoonrise().getHour();
        String moonriseMinute = String.valueOf(astro.getMoonInfo().getMoonrise().getMinute());
        if(moonriseMinute.length()==1){
            moonriseMinute ="0"+moonriseMinute;
        }

        int moonsetHour = astro.getMoonInfo().getMoonset().getHour();
        String moonsetMinute = String.valueOf(astro.getMoonInfo().getMoonset().getMinute());
        if(moonsetMinute.length()==1){
            moonsetMinute ="0"+moonsetMinute;
        }

        int newMoonHour = astro.getMoonInfo().getNextNewMoon().getDay();
        String newMoonMinute = String.valueOf(astro.getMoonInfo().getNextNewMoon().getMonth());
        if(newMoonMinute.length()==1){
            newMoonMinute ="0"+newMoonMinute;
        }
        String newmoonDate = newMoonHour+"."+ newMoonMinute+"."+astro.getMoonInfo().getNextNewMoon().getYear();

        String fullMoonHour = String.valueOf(astro.getMoonInfo().getNextFullMoon().getDay());

        String fullMoonMinute = String.valueOf(astro.getMoonInfo().getNextFullMoon().getMonth());
        if(fullMoonMinute.length()==1){
            fullMoonMinute ="0"+fullMoonMinute;
        }

        String fullmoonDate = fullMoonHour+"."+ fullMoonMinute+"."+astro.getMoonInfo().getNextFullMoon().getYear();

        double month = round(astro.getMoonInfo().getAge());
        double phase = round(astro.getMoonInfo().getIllumination()*100) ;

        info.setMoonRise(moonriseHour+" : "+moonriseMinute);
        info.setMoonSet(moonsetHour+" : "+moonsetMinute);
        info.setNewMoon(newmoonDate);
        info.setFullMoon(fullmoonDate);
        info.setMonth(String.valueOf(month));
        info.setFaza(phase+"%");
    }

    public void getSunInfo(Info info, AstroCalculator astro){

        double sunsetAzymut =round(astro.getSunInfo().getAzimuthSet());
        double sunriseAzymut = round(astro.getSunInfo().getAzimuthRise());


        int sunsetHour = astro.getSunInfo().getSunset().getHour();
        String sunsetMin = String.valueOf(astro.getSunInfo().getSunset().getMinute());
        if(sunsetMin.length()==1){
            sunsetMin ="0"+sunsetMin;
        }

        int sunriseHour = astro.getSunInfo().getSunrise().getHour();
        String sunriseMin = String.valueOf(astro.getSunInfo().getSunrise().getMinute());
        if(sunriseMin.length()==1){
            sunriseMin ="0"+sunriseMin;
        }

        int downHour = astro.getSunInfo().getTwilightMorning().getHour();
        String downMin = String.valueOf(astro.getSunInfo().getTwilightMorning().getMinute());
        if(downMin .length()==1){
            downMin  ="0"+downMin;
        }

        int twilightHour = astro.getSunInfo().getTwilightEvening().getHour();
        String twilightMin = String.valueOf(astro.getSunInfo().getTwilightEvening().getMinute());
        if(twilightMin .length()==1){
            twilightMin ="0"+twilightMin;
        }

        info.setSunRise(sunriseHour +" : "+sunriseMin+" , azymut: "+sunriseAzymut);
        info.setSunSet(sunsetHour +" : "+sunsetMin+" , azymut: "+sunsetAzymut);
        info.setDown(downHour +" : "+downMin);
        info.setTwilight(twilightHour +" : "+twilightMin);
    }

    public ArrayList<Info> getZadania() {
        return mTasks;
    }

    double round(double num){
        num *= 10000;
        num = Math.round(num);
        num /= 10000;

        return  num;
    }
//    public Info getZadanie(UUID id) {
//        for (Info zadanie : mTasks) {
//            if (zadanie.getUUID().equals(id)) {
//                return zadanie;
//            }
//        }
//        return null;
//    }
}
