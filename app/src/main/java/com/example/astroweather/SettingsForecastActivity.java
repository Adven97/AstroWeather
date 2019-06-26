package com.example.astroweather;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.astroweather.MainActivity.isTablet;
import static com.example.astroweather.WeatherActivity.mDatabaseHelper;
import static com.example.astroweather.WeatherInfoSet.isOnline;
import static com.example.astroweather.WeatherInfoSet.wrongInp;

public class SettingsForecastActivity extends AppCompatActivity {

    public static String cityValue=getLastCity();
    public static String jednValue="Kelvin";
   // public static String jedn;


    EditText giveCity;
    public boolean isOk;
    public boolean dobreMiasto;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_forecast);

        Spinner spinner = (Spinner) findViewById(R.id.jedn_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jednostki, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                parent.getItemAtPosition(pos);
                if(parent.getItemAtPosition(pos).toString().equals("Celsjusz")){
                    Log.d("JEDNOSTKAAA","CELSJUSZ");
                    jednValue="Celsjusz";
                }

                else if(parent.getItemAtPosition(pos).toString().equals("Kelvin")){
                    Log.d("JEDNOSTKAAA","CELSJUSZ");
                    jednValue="Kelvin";
                }
                else if(parent.getItemAtPosition(pos).toString().equals("Farenheit")){
                    Log.d("JEDNOSTKAAA","CELSJUSZ");
                    jednValue="Farenheit";
                }
                Log.d("uguleeeeem",parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        isOk = true;
        giveCity = findViewById(R.id.giveCity);
        //   refresh = findViewById(R.id.refresh);
        save = findViewById(R.id.savebutt2);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (giveCity.getText().toString().length() > 0) {

                    cityValue = giveCity.getText().toString();
                } else {
                    Toast.makeText(getBaseContext(), "Podaj miasto", Toast.LENGTH_LONG).show();
                    isOk = false;
                }


                if (isOk) {
                    //Toast.makeText(getBaseContext(), "Walidacja miasta, prosze czekać", Toast.LENGTH_SHORT).show();

                    testCon test = new testCon();

                    test.execute(cityValue);
                    Thread t = new Thread();
                        try {
                            t.sleep(2000);
                            if(dobreMiasto){
                        startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                    }
                    else {
                        Toast.makeText(getBaseContext(), "NIEPRAWIDŁOWA NAZWA MIASTA", Toast.LENGTH_LONG).show();
                    }
                            t.interrupt();
                        } catch (InterruptedException e) {}




                } else {
                    Toast.makeText(getBaseContext(), "COS NIE TAK POSZLO", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public static String getLastCity(){
        Cursor dataa = mDatabaseHelper.getLastCity();
        if(dataa.getCount() ==0){
            Log.d("TUBDJSON","PIZDA W CZEKSIE TABELA BPUUSTAA 214323234243243");
            return "lodz";
        }

        dataa.moveToNext();
        return dataa.getString(0);
    }

    public String tryToGetWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;
        String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        String APPID = "198399517e1674da5e467031201103a7";

        try {

            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&APPID=" + APPID)).openConnection();
            Log.d("adresStrony1111",BASE_URL + location + "&APPID=" + APPID);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            Log.d("RESPONSEEE","status = "+con.getResponseCode());

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return "dobrze";

        }
        catch(Throwable t) {
            //wrongInp=true;
            Log.d("RESPONSEEE","pizdaaaaaaaaaaa");
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return "NIE";

    }

    private class testCon extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
           String data = tryToGetWeatherData(params[0]);
           if(data.equals("NIE")){
               dobreMiasto=false;
               return false;
           }
           else {
               dobreMiasto=true;
               Log.d("RESPONSEEE","trueeeeeeeeeeeeeeeeeeeeeeeee");
               return true;
           }

        }
    }
}

