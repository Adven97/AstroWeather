package com.example.astroweather;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.amitshekhar.DebugDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.astroweather.SettingsActivity.refreshTimeValue;
import static com.example.astroweather.WeatherInfoSet.isOnline;
import static com.example.astroweather.WeatherInfoSet.jsonData;
import static com.example.astroweather.WeatherInfoSet.wrongInp;

public class WeatherActivity extends AppCompatActivity {

    private FragmentPagerAdapter adapterViewPager;
    public static TextView pos2;

    Thread tMyLoc;
    public static Calendar cal;
    String min, sec;
    public static int counter;
    public static boolean timesUp;

    Button startForecast;
    Button startSettigs;
    Button refreshNow;
    WeatherInfoSet tasksSet;
    TimerTask timerTaskObj;
    boolean allowed;
    double ref;
    public ArrayList<String> ulubione;

    boolean doOnce;
    public static DatabaseHelper mDatabaseHelper;

    public static boolean makeToast;
    ArrayAdapter<String> adapter;
    Spinner spinner;

    @Override
    public void onSaveInstanceState(Bundle outState) {

      //  outState.putInt("counter", counter);
       // outState.putBoolean("timeup", timesUp);
        outState.putBoolean("allowed", allowed);
       // outState.putDouble("val",ref);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);
        pos2 = findViewById(R.id.pos2);

        mDatabaseHelper = new DatabaseHelper(this);
        doOnce =true;
        Log.d("BAZABAZABAZA", DebugDB.getAddressLog());

        startForecast = findViewById(R.id.startForecast);
        startSettigs = findViewById(R.id.startSettings);
        refreshNow = findViewById(R.id.refreshButt);

        ulubione = getAllCities();
        makeToast=false;
        allowed=true;
        //SettingsForecastActivity.cityValue = getLastCity();

        spinner = (Spinner) findViewById(R.id.umubionee);
        if(ulubione != null) {
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ulubione);
            // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if (doOnce) {
                        //  SettingsForecastActivity.cityValue = parent.getItemAtPosition(pos).toString();
                        tasksSet = WeatherInfoSet.get();
                        doOnce = false;
                    }
                    else {
                        SettingsForecastActivity.cityValue = parent.getItemAtPosition(pos).toString();

                        tasksSet = WeatherInfoSet.get();
                        ulubione = getAllCities();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        tasksSet = WeatherInfoSet.get();
        Thread t = new Thread();
        try {
            t.sleep(1000);

            if(isOnline){
                Toast.makeText(getBaseContext(), "jestes online", Toast.LENGTH_SHORT).show();
            }
            if(!isOnline){
                Toast.makeText(getBaseContext(), "BŁĄD POŁĄCZENIA, SPRÓBUJ PONOWNIE", Toast.LENGTH_SHORT).show();
            }
            if(wrongInp){
                Toast.makeText(getBaseContext(), "NIE MOZNA POŁĄCZYC Z INTERNETEM, SPRÓBUJ PONOWNIE", Toast.LENGTH_SHORT).show();
            }

            t.interrupt();
        } catch (InterruptedException e) {}




        if(savedInstanceState !=null){
            allowed = savedInstanceState.getBoolean("allowed");
            allowed=false;
        }


        ArrayList<WeatherInfo> lista_tasks = tasksSet.getZadania();

        ViewPager vPager = findViewById(R.id.vp2);

        adapterViewPager = new OtherAdapter(getSupportFragmentManager(), lista_tasks);

        vPager.setAdapter(adapterViewPager);

        startForecast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!isOnline || wrongInp){
                    Toast.makeText(getBaseContext(), "NIE MOZNA ZOBACZYC PROGNOZY", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), ForecastActivity.class));

                }
            }
        });

        startSettigs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsForecastActivity.class));

            }
        });

        refreshNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tasksSet = WeatherInfoSet.get();
                ulubione = getAllCities();
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ulubione);
                // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                if(!isOnline){
                    Toast.makeText(getBaseContext(), "JESTES OFFLINE", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "JESTES ONLINE, Dane zostały odświeżone", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public ArrayList<String> getAllCities(){
        Cursor dataa = mDatabaseHelper.getAllCities();
        if(dataa.getCount() ==0){
            Log.d("TUBDJSON","PIZDA W CZEKSIE TABELA BPUUSTAA 214323234243243");
            dataa.close();
            return null;
        }
        ArrayList<String> listData = new ArrayList<>();
        while(dataa.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(dataa.getString(0));
        }
        Collections.reverse(listData);
      //  dataa.moveToFirst();
      //  Log.d("TUBDJSON",dataa.getString(0));
        dataa.close();
        return listData;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

