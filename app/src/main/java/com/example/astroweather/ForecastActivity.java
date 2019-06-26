package com.example.astroweather;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.astroweather.SettingsForecastActivity.cityValue;
import static com.example.astroweather.WeatherActivity.mDatabaseHelper;
import static com.example.astroweather.WeatherInfoSet.isOnline;

public class ForecastActivity extends FragmentActivity {

    DatabaseHelperForecast mDatabaseHelper2;

    private TextView cityText;


    private TextView hum;
    private ImageView imgView;

    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?mode=json&q=";
    private static String APPID = "198399517e1674da5e467031201103a7";
    private static String forecastDaysNum = "5";
    private static String city;
    private static String lang;

    ViewPager pager;
    Button startSettin;
    Button refresh;
    public boolean isOnlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        city = cityValue;
        lang = "en";

        mDatabaseHelper2 = new DatabaseHelperForecast(this);
        pager = findViewById(R.id.myfPager);
        cityText = findViewById(R.id.city);
        cityText.setText(city);

        JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        task1.execute(new String[]{city,lang, forecastDaysNum});

        startSettin = findViewById(R.id.startSettings2);
        refresh = findViewById(R.id.refreshForcast);

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONForecastWeatherTask task11 = new JSONForecastWeatherTask();
                task11.execute(new String[]{city,lang, forecastDaysNum});

                if(!isOnlin){
                    Toast.makeText(getBaseContext(), "JESTES OFFLINE", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "JESTES ONLINE, Dane zostały odświeżone", Toast.LENGTH_LONG).show();
                }

            }
        });

        startSettin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsForecastActivity.class));

            }
        });
    }

    public String getForecastWeatherData(String location, String lang, String sForecastDayNum) {
        HttpURLConnection con = null ;
        InputStream is = null;
        int forecastDayNum = Integer.parseInt(sForecastDayNum);

        try {

            // Forecast
            String url = BASE_FORECAST_URL + location+ "&lang=" + lang;
            url = url + "&cnt=" + forecastDayNum+ "&APPID=" + APPID;
            Log.d("dskghdsflkjdsfgh",url);

            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer1 = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
            String line1 = null;
            while (  (line1 = br1.readLine()) != null )
                buffer1.append(line1 + "\r\n");

            is.close();
            con.disconnect();

            System.out.println("Buffer ["+buffer1.toString()+"]");
            return buffer1.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public void AddData(String newEntry, String ent) {
        boolean insertData = mDatabaseHelper2.addDataf(newEntry,ent);

        if (insertData) {
            Log.d("czysieudalo","UDAWLO SIE");
        }
        //  Toast.makeText(getBaseContext(), "Podaj miasto", Toast.LENGTH_LONG).show();
        else {
            Log.d("czysieudalo","NIE NIE NIE NIE UDALO SIE");

        }
    }

    public String getDataFromDb(){
        Cursor dataa = mDatabaseHelper2.getLastForecast();

        if(dataa.getCount() ==0){
            Log.d("TUBDJSON","PIZDA W CZEKSIE TABELA BPUUSTAA");
            dataa.close();
            return null;
        }

        dataa.moveToFirst();
        Log.d("TUBDJSON",dataa.getString(2));
        String res =dataa.getString(2);
        dataa.close();
        return res;
    }

    private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {
            WeatherForecast forecastt = new WeatherForecast();
            String data = getForecastWeatherData(params[0], params[1], params[2]);

            if(data != null) {

                isOnlin=true;
                Log.d("tojesttadata", data);
                AddData(data, params[0]);
                getDataFromDb();
            }
            else {
                isOnlin=false;
                data = getDataFromDb();
            }


            try {
                forecastt = JSONWeatherParser.getForecastWeather(data);
                System.out.println("asassaassasasa Weather ["+forecastt+"]");
                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                Log.e("Dupa","dupa dupa dupa");
                e.printStackTrace();
            }
            return forecastt;

        }


        @Override
        protected void onPostExecute(WeatherForecast forecastWeather) {
            super.onPostExecute(forecastWeather);

            DailyForecastAdapter adapter = new DailyForecastAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            pager.setAdapter(adapter);
        }

    }


}
