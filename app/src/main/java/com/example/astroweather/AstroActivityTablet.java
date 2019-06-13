package com.example.astroweather;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import java.util.Timer;
import java.util.TimerTask;

import static com.example.astroweather.SettingsActivity.refreshTimeValue;

public class AstroActivityTablet extends FragmentActivity {

    private FragmentPagerAdapter adapterViewPager;
    TextView dateTime, pos;
    // public static Date timeNow;
    //Thread tMyLoc;
    public static Calendar cal;
    String min, sec;
    public static int counter;
    public static boolean timesUp;

    InfoSet tasksSet;
    Info info;

    private ArrayList<Info> tasks;

    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment myFragment;
    private Fragment mySecondFragment;
    Thread tMyLoc;

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("counter", counter);
        outState.putBoolean("timeup", timesUp);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitity_main_tablet);

        info = new Info();
        dateTime = findViewById(R.id.maintime2);
        pos = findViewById(R.id.pos2);
        pos.setText("Wsp. geograficzne: "+SettingsActivity.latitudeValue+" , "+SettingsActivity.longtitudeValue);
        cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

        tasks = new ArrayList<Info>();



        timesUp=false;

        min = String.valueOf(cal.get(Calendar.MINUTE));
        if(min.length()==1){
            min="0"+min;
        }

        sec = String.valueOf(cal.get(Calendar.SECOND));
        if(sec.length()==1){
            sec="0"+sec;
        }

        counter =0;

        dateTime.setText("Czas: "+cal.get(Calendar.HOUR_OF_DAY) +" : "+ min+" : "+ sec);
        tasksSet = InfoSet.get(cal);

        if(savedInstanceState !=null){
            counter =savedInstanceState.getInt("counter");
            timesUp = savedInstanceState.getBoolean("timeup");
        }
//
        Timer refresh = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                tasksSet = InfoSet.update(cal);
            }
        };
        refresh.schedule(timerTaskObj, 0, (int)(60000*refreshTimeValue));
//
//
        tMyLoc = new Thread(){
            @Override public void run(){
                try {
                    while(!isInterrupted()){
                        Thread.sleep(1000); //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                min = String.valueOf(cal.get(Calendar.MINUTE));
                                if (min.length() == 1) {
                                    min = "0" + min;
                                }

                                sec = String.valueOf(cal.get(Calendar.SECOND));
                                if (sec.length() == 1) {
                                    sec = "0" + sec;
                                }
                                dateTime.setText("Czas: "+cal.get(Calendar.HOUR_OF_DAY) +" : "+ min+" : "+ sec);
                            } }); } }catch (InterruptedException e) {}  }};

        tMyLoc.start();

        adapterViewPager = new CustomAdapter(getSupportFragmentManager(), tasksSet.getZadania());

//        ArrayList<Info> lista_tasks = tasksSet.getZadania();
//
//        ViewPager vPager = findViewById(R.id.vp);
//
//        adapterViewPager = new CustomAdapter(getSupportFragmentManager(), lista_tasks);
//
//        vPager.setAdapter(adapterViewPager);


        if (savedInstanceState == null) {
            FragmentTransaction ft = this.fm.beginTransaction();

            myFragment = MoonFragment.newInstance(tasksSet.getZadania().get(0));
            ft.replace(R.id.moon_fragmentt, myFragment);

            mySecondFragment = SunFragment.newInstance(tasksSet.getZadania().get(1));
            ft.replace(R.id.sun_fragmentt, mySecondFragment);

            ft.commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("eloooooooooooo", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("eloooooooooooo", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("eloooooooooooo", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("eloooooooooooo", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

