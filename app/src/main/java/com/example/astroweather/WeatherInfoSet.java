package com.example.astroweather;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import static com.example.astroweather.SettingsForecastActivity.cityValue;
import static com.example.astroweather.SettingsForecastActivity.jednValue;
import static com.example.astroweather.WFragment1.cis;
import static com.example.astroweather.WFragment1.miasto;
import static com.example.astroweather.WFragment1.opis;
import static com.example.astroweather.WFragment1.pogoda1;
import static com.example.astroweather.WFragment1.temperatura;
import static com.example.astroweather.WFragment2.pogoda2;
import static com.example.astroweather.WFragment2.wilgotnosc;

import static com.example.astroweather.WFragment2.windStr;
import static com.example.astroweather.WFragment2.zachm;
import static com.example.astroweather.WeatherActivity.mDatabaseHelper;
import static com.example.astroweather.WeatherActivity.makeToast;
import static com.example.astroweather.WeatherActivity.pos2;

//import static com.example.astroweather.AstroActivity.timeNow;

public class WeatherInfoSet {

    public static String jsonData="xd";
    private static WeatherInfoSet sTasksSetW;
    private ArrayList<WeatherInfo> mTasks;

    static String stream =null;

    //DatabaseHelper mDatabaseHelper;

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String APPID = "198399517e1674da5e467031201103a7";

    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&q=";

    public static boolean isOnline;
    public static boolean wrongInp;
    boolean jeblo;
    String city = cityValue;

    public static WeatherInfoSet get() {

        sTasksSetW = new WeatherInfoSet();
        return sTasksSetW;
    }


    private WeatherInfoSet() {
        wrongInp=false;

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



    public ArrayList<WeatherInfo> getZadania() {
        return mTasks;
    }


    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;
        String url2 = "http://api.openweathermap.org/data/2.5/forecast?q";
        String urlSeckond ="&mode=json&units=metric&cnt=3";
        try {

            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&APPID=" + APPID)).openConnection();

            Log.d("adresStrony1",BASE_URL + location + "&APPID=" + APPID);

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
            return buffer.toString();
        }
        catch(Throwable t) {
            wrongInp=true;
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }


    public void setIcon2(String sr, ImageView iconWeather){
        if(sr.equals("01d")){
            iconWeather.setImageResource(R.drawable.sun);
        }
        else if(sr.equals("01n")){
            iconWeather.setImageResource(R.drawable.moonxd);
        }
        else if(sr.equals("02n") || sr.equals("02d")){
            iconWeather.setImageResource(R.drawable.few);
        }
        else if(sr.equals("03n") || sr.equals("03d")){
            iconWeather.setImageResource(R.drawable.scatter);
        }
        else if(sr.equals("04n") || sr.equals("04d")){
            iconWeather.setImageResource(R.drawable.broken);
        }
        else if(sr.equals("09n") || sr.equals("09d")){
            iconWeather.setImageResource(R.drawable.shower);
        }
        else if(sr.equals("10n") || sr.equals("10d")){
            iconWeather.setImageResource(R.drawable.rain);
        }
        else if(sr.equals("11n") || sr.equals("11d")){
            iconWeather.setImageResource(R.drawable.thunderstorm);
        }
        else if(sr.equals("13n") || sr.equals("13d")){
            iconWeather.setImageResource(R.drawable.snow);
        }
        else if(sr.equals("50n") || sr.equals("50d")){
            iconWeather.setImageResource(R.drawable.mist);
        }
    }

//    public boolean getjeblo(){
//        return jeblo;
//    }

    double round(double num, int ile){
        num *= ile;
        num = Math.round(num);
        num /= ile;

        return  num;
    }

    public void AddData(String newEntry, String ent) {
        boolean insertData = mDatabaseHelper.addData(newEntry,ent);

        if (insertData) {
            Log.d("czysieudalo","UDAWLO SIE");
        }
          //  Toast.makeText(getBaseContext(), "Podaj miasto", Toast.LENGTH_LONG).show();
         else {
        Log.d("czysieudalo","NIE NIE NIE NIE UDALO SIE");

    }
    }

    public String getDataFromDb(int i){
        Cursor dataa = mDatabaseHelper.getLastItem();
        if(dataa.getCount() ==0){
            Log.d("TUBDJSON","PIZDA W CZEKSIE TABELA BPUUSTAA");
            dataa.close();
            return null;
        }

        dataa.moveToFirst();
        Log.d("TUBDJSON",dataa.getString(i));
        String res = dataa.getString(i);
        dataa.close();
        return res;
    }

    private class JSONWeatherTask1 extends AsyncTask<String, Void, WeatherInfo> {

        @Override
        protected WeatherInfo doInBackground(String... params) {
            WeatherInfo weatherr = new WeatherInfo();
            String data = getWeatherData(params[0]);



            if(data ==null) {
                isOnline=false;
              //  cityValue = getDataFromDb(1);
                data = getDataFromDb(2);

            }
            else{
                isOnline=true;
                Log.d("tojesttadata",data);
                AddData(data,params[0]);
                //getDataFromDb();

            }


            try {
                weatherr = JSONWeatherParser.getWeather(data);

            } catch (JSONException e) {
                wrongInp=true;
                e.printStackTrace();
            }
            return weatherr;

        }

        @Override
        protected void onPostExecute(WeatherInfo weather) {
            super.onPostExecute(weather);

            if(weather != null) {
                // getWeather1(weather);
                pos2.setText("Wsp. geograficzne:\n" + weather.getLatitude() + " , " + weather.getLongtitude());
                miasto.setText(weather.getMiasto());
                Log.d("jednostkakurwamac", jednValue);
                if (jednValue.equals("Celsjusz")) {
                    temperatura.setText(round(weather.getTemperatura() - 273.15,100) + "°C");
                } else if (jednValue.equals("Farenheit")) {
                    temperatura.setText(round(weather.getTemperatura() * 1.8 - 459.67,100) + "°F");
                } else {
                    temperatura.setText(round(weather.getTemperatura(),100) + "°K");
                }
                cis.setText(round(weather.getCis(),1000) + " hPa");
                opis.setText(weather.getOpis().toUpperCase());
                setIcon2(weather.getIcon(), pogoda1);
            }
            else {
                wrongInp=true;
            }

        }
    }


    private class JSONWeatherTask2 extends AsyncTask<String, Void, WeatherInfo> {

        @Override
        protected WeatherInfo doInBackground(String... params) {
            WeatherInfo weatherr = new WeatherInfo();
            String data = getWeatherData(params[0]);
            if(data ==null) {
                isOnline=false;
                jeblo=true;
                wrongInp=true;
                Log.d("KURWACHUJ","KURWACHUJ------KURWACHUJ------------KURWACHUJ---------KURWACHUJ");
                cityValue = getDataFromDb(1);
                data = getDataFromDb(2);

                // Toast.makeText(WeatherActivity.getContext(), "Dane zostały odświeżone", Toast.LENGTH_SHORT).show();
            }
            else{
                jeblo=false;
                isOnline=true;
                Log.d("tojesttadata",data);
                AddData(data,params[0]);
                //getDataFromDb();

            }
            try {
                weatherr = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                //  weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                wrongInp=true;
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
            if (weather != null) {
                pos2.setText("Wsp. geograficzne:\n" + weather.getLatitude() + " , " + weather.getLongtitude());
                wilgotnosc.setText(round(weather.getWilgotnosc(),10000) + " %");
                //windDeg.setText(weather.getWindDeg() + "");
                windStr.setText(round(weather.getWindSpeed(),10000) + " m/s");
                zachm.setText(round(weather.getZachm(),10000) + " %");
                setIcon2(weather.getIcon(), pogoda2);
                // pogoda2.setImageResource(R.drawable.snow);
            }
        }
    }

}
