package com.example.astroweather;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.astroweather.SettingsForecastActivity.jednValue;
import static com.example.astroweather.WFragment1.temperatura;

public class DailyforecastFragment extends Fragment {

    private ForecastInfo dayForecast=null;
    private ImageView iconWeather;
    double min,max,day;
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i("TaskFragment", "onActivityCreated");
    }

    public DailyforecastFragment() {}


    public static DailyforecastFragment newInstance(ForecastInfo info) {
        //dayForecast=info;
        DailyforecastFragment fragmentFirst = new DailyforecastFragment();
        Bundle args = new Bundle();
        args.putSerializable("SomeTask", info);
       // args.putParcelable("SomeTask", info.forecastTemp);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public void setForecast(ForecastInfo dayForecast) {

        this.dayForecast = dayForecast;
//        DailyforecastFragment fragmentFirst = new DailyforecastFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("SomeTask", this.dayForecast);
//        fragmentFirst.setArguments(args);
      //  return fragmentFirst;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    double round(double num){
        num *= 100;
        num = Math.round(num);
        num /= 100;

        return  num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);

        TextView tempView = (TextView) v.findViewById(R.id.tempForecast);
        TextView descView = (TextView) v.findViewById(R.id.skydescForecast);
        TextView cis2 = v.findViewById(R.id.cis2);
        TextView zachm2 = v.findViewById(R.id.zachm2);
        TextView opiss = v.findViewById(R.id.opis2);
        Bundle bundle=getArguments();
        if(bundle !=null) {


            min = dayForecast.min;
            max = dayForecast.max;
            day = dayForecast.day;

            if (jednValue.equals("Celsjusz")) {
//            temperatura.setText(round(min)+ "C");
//            temperatura.setText(round(max)+ "C");
//            temperatura.setText(round(day)+ "C");
                tempView.setText(round(min - 273.15) + "°C" + " - " + round(max - 273.15) + "°C");
                descView.setText("Temperatura: " + round(day - 273.15) + "C");
            } else if (jednValue.equals("Farenheit")) {
                //temperatura.setText(round(weather.getTemperatura() *1.8 -459.67)+ "F");
                tempView.setText(round(min * 1.8 - 459.67) + "°F" + " - " + round(max * 1.8 - 459.67) + "°F");
                descView.setText("Temperatura: " + round(day * 1.8 - 459.67) + "F");
            } else {
                tempView.setText(round(min) + "°K" + " - " + round(max) + "°K");
                descView.setText("Temperatura: " + round(day) + "K");
            }

            // tempView.setText( min  + " - " +max );
            // descView.setText("Actual weather: "+day);

            cis2.setText("Cisnienie: " + round(dayForecast.cis) + " hPa");
            zachm2.setText("Wilgotność: " + round(dayForecast.zachmu) + " %");
            opiss.setText(dayForecast.getOpiss().toUpperCase());
            iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);
            // Now we retrieve the weather icon
            // String xdd = "sun";

            //JSONIconWeatherTask task = new JSONIconWeatherTask();
            //task.execute(new String[]{dayForecast.forecastTemp.getIcon()});
            setIcon(dayForecast.getIcon());
            Log.d("IKONKAAAAA", dayForecast.getIcon());
        }
        return v;
    }

    public void setIcon(String sr){
        if(sr.equals("01d")){
            iconWeather.setImageResource(R.drawable.sun);
        }
        else if(sr.equals("01n")){
            iconWeather.setImageResource(R.drawable.moonxd);
        }
        else if(sr.equals("02n") || sr.equals("02d")){
            iconWeather.setImageResource(R.drawable.few);
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

    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code+".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
            Log.e("CHUJ_PIZDAAAA","PIZDAAAAAAAAAAA");
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }



    private class JSONIconWeatherTask extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... params) {

            byte[] data = null;

            try {

                // Let's retrieve the icon
                data = getImage(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }




        @Override
        protected void onPostExecute(byte[] data) {
            super.onPostExecute(data);

            if (data != null) {
                Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
                iconWeather.setImageBitmap(img);
            }
        }

    }


}
