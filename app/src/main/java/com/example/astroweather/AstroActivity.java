package com.example.astroweather;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.astroweather.SettingsActivity.refreshTimeValue;

public class AstroActivity extends AppCompatActivity {

    private FragmentPagerAdapter adapterViewPager;
    TextView dateTime, pos;
   // public static Date timeNow;
    //Thread tMyLoc;
    public static Calendar cal;
    String min, sec;
    public static int counter;
    public static boolean timesUp;

    InfoSet tasksSet;
    Thread tMyLoc;

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("counter", counter);
        outState.putBoolean("timeup", timesUp);
      //  outState.putParcelable("frd",tMyLoc);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(IsTablet()){
            setContentView(R.layout.actitity_main_tablet);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        dateTime = findViewById(R.id.maintime);
        pos = findViewById(R.id.pos);
        pos.setText("Wsp. geograficzne: "+SettingsActivity.latitudeValue+" , "+SettingsActivity.longtitudeValue);
        cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

        timesUp=false;

        min = String.valueOf(cal.get(Calendar.MINUTE));
        if(min.length()==1){
            min="0"+min;
        }

        sec = String.valueOf(cal.get(Calendar.SECOND));
        if(sec.length()==1){
            sec="0"+sec;
        }
        dateTime.setText("Czas: "+cal.get(Calendar.HOUR_OF_DAY) +" : "+ min+" : "+ sec);

        counter =0;

        tasksSet = InfoSet.get();

        if(savedInstanceState !=null){
          //  counter =savedInstanceState.getInt("counter");
            //timesUp = savedInstanceState.getBoolean("timeup");
        }

        tMyLoc=new Thread(){
            @Override public void run(){
                try {
                    while(!isInterrupted()){
                        Thread.sleep(1000); //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                min = String.valueOf(cal.get(Calendar.MINUTE));
                                if(min.length()==1){
                                    min="0"+min;
                                }

                                sec = String.valueOf(cal.get(Calendar.SECOND));
                                if(sec.length()==1){
                                    sec="0"+sec;
                                }
                                dateTime.setText("Czas: "+cal.get(Calendar.HOUR_OF_DAY) +" : "+ min+" : "+ sec);

                                if(counter <refreshTimeValue*60){
                                    counter++;
                                    Log.d("---COUNTER TAG---","%^$%$%^$%$%^%$%^%$%#%%^%^   COUNTER:  "+counter);
                                }
                                else {
                                    timesUp=true;
                                    tasksSet = InfoSet.get();
                                    counter=0;
                                    timesUp=false;
                                }



                            } }); } }catch (InterruptedException e) {} } };
        tMyLoc.start();




        ArrayList<Info> lista_tasks = tasksSet.getZadania();

        ViewPager vPager = findViewById(R.id.vp);

        adapterViewPager = new CustomAdapter(getSupportFragmentManager(), lista_tasks);

        vPager.setAdapter(adapterViewPager);


        // dateTime.append(timeNow.toString());


//            Thread t = new Thread();
//            try {
//                t.sleep(1000);
//                // progressDialog.hide();
//                try {
//                    timeNow = Calendar.getInstance().getTime();
//                    dateTime.append(timeNow.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                t.interrupt();
//            } catch (InterruptedException e) { }




    }

    public static boolean IsTablet()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        //getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return true;
        }else{
            // smaller device
            return false;
        }
    }

}

