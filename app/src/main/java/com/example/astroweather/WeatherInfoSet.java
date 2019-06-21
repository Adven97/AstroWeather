package com.example.astroweather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroCalculator.SunInfo;
import com.astrocalculator.AstroCalculator.MoonInfo;
import com.astrocalculator.AstroDateTime;

import org.json.JSONException;

import static com.example.astroweather.WFragment1.cis;
import static com.example.astroweather.WFragment1.miasto;
import static com.example.astroweather.WFragment1.temperatura;
import static com.example.astroweather.WFragment2.wilgotnosc;
import static com.example.astroweather.WFragment2.windDeg;
import static com.example.astroweather.WFragment2.windStr;
import static com.example.astroweather.WFragment2.zachm;

//import static com.example.astroweather.AstroActivity.timeNow;

public class WeatherInfoSet {

    private static WeatherInfoSet sTasksSetW;
    private ArrayList<WeatherInfo> mTasks;

    static String stream =null;

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String APPID = "198399517e1674da5e467031201103a7";
    String city = "rome,it";

    public static WeatherInfoSet get() {

        sTasksSetW = new WeatherInfoSet();
        return sTasksSetW;
    }


    private WeatherInfoSet() {


        //Log.d("WHJDHLKHDSFLKHDSFKJD",getHTTPData("http://api.openweathermap.org/data/2.5/weather?q=ROME&APPID=198399517e1674da5e467031201103a7"));
        mTasks = new ArrayList<WeatherInfo>();
        for (int i = 0; i < 2; i++) {
            WeatherInfo info = new WeatherInfo();
            JSONWeatherTask1 task1 = new JSONWeatherTask1();
            JSONWeatherTask2 task2 = new JSONWeatherTask2();

            if(i==0){
                //getWeather1(info);

                task1.execute(new String[]{city});
            }

            if(i==1){
                //getWeather1(info);
                task2.execute(new String[]{city});
              //  task.execute(new String[]{city});
            }
            mTasks.add(info);
        }
    }


    public void getWeather1(WeatherInfo info){

//        info.setMiasto("Zgierz");
//        info.setCis("22 hPa");
//        info.setTemperatura("35 C");
//        info.setOpis("zadupie");

    }


    public ArrayList<WeatherInfo> getZadania() {
        return mTasks;
    }


    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&APPID=" + APPID)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
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


    private class JSONWeatherTask1 extends AsyncTask<String, Void, WeatherInfo> {

        @Override
        protected WeatherInfo doInBackground(String... params) {
            WeatherInfo weatherr = new WeatherInfo();
            String data = getWeatherData(params[0]);

            try {
                weatherr = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
              //  weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherr;

        }

        @Override
        protected void onPostExecute(WeatherInfo weather) {
            super.onPostExecute(weather);

//            if (weather.iconData != null && weather.iconData.length > 0) {
//                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
//                imgView.setImageBitmap(img);
//            }

           // getWeather1(weather);
                miasto.setText(weather.getMiasto());
                temperatura.setText(weather.getTemperatura()+ " C");
                cis.setText(weather.getCis()+ " hpa");

//            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
//            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
//            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
//            hum.setText("" + weather.currentCondition.getHumidity() + "%");
//            press.setText("" + weather.currentCondition.getPressure() + " hPa");
//            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
//            windDeg.setText("" + weather.wind.getDeg() + "�");

        }
    }



    /////////////////

    private class JSONWeatherTask2 extends AsyncTask<String, Void, WeatherInfo> {

        @Override
        protected WeatherInfo doInBackground(String... params) {
            WeatherInfo weatherr = new WeatherInfo();
            String data = getWeatherData(params[0]);

            try {
                weatherr = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                //  weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherr;

        }

        @Override
        protected void onPostExecute(WeatherInfo weather) {
            super.onPostExecute(weather);

//            if (weather.iconData != null && weather.iconData.length > 0) {
//                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
//                imgView.setImageBitmap(img);
//            }

           // getWeather1(weather);

                wilgotnosc.setText(weather.getWilgotnosc()+"");
                windDeg.setText(weather.getWindDeg()+"");
                windStr.setText(weather.getWindSpeed()+"");
                zachm.setText(weather.getZachm()+"");



        }
    }


}
