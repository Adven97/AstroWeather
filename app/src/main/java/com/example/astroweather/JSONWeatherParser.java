package com.example.astroweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static WeatherInfo getWeather(String data) throws JSONException {
        WeatherInfo weather = new WeatherInfo();

        if (data != null) {
            JSONObject jObj = new JSONObject(data);

            JSONObject coordObj = getObject("coord", jObj);
            weather.setLatitude(getFloat("lat", coordObj));
            weather.setLongtitude(getFloat("lon", coordObj));

            JSONObject sysObj = getObject("sys", jObj);
            weather.setKraj(getString("country", sysObj));
            weather.setMiasto(getString("name", jObj));

            // We get weather info (This is an array)
            JSONArray jArr = jObj.getJSONArray("weather");

            // We use only the first value
            JSONObject JSONWeather = jArr.getJSONObject(0);
//        weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
            weather.setOpis(getString("description", JSONWeather));
//        weather.currentCondition.setCondition(getString("main", JSONWeather));
            weather.setIcon(getString("icon", JSONWeather));

            JSONObject mainObj = getObject("main", jObj);
            weather.setWilgotnosc(getInt("humidity", mainObj));
            weather.setCis(getInt("pressure", mainObj));
            //  weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
            //  weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
            weather.setTemperatura(getFloat("temp", mainObj));


            // Clouds
            JSONObject cObj = getObject("clouds", jObj);
            weather.setZachm(getInt("all", cObj));

            // Wind
            JSONObject wObj = getObject("wind", jObj);
            weather.setWindSpeed(getFloat("speed", wObj));
//        weather.setWindDeg(getFloat("deg", wObj));

            // We download the icon to show


            return weather;
        }
        else {
            return null;
        }
    }

    public static WeatherForecast getForecastWeather(String data) throws JSONException {

        WeatherForecast forecast = new WeatherForecast();

        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        JSONArray jArr = jObj.getJSONArray("list"); // Here we have the forecast for every day

        // We traverse all the array and parse the data
        for (int i=0; i < jArr.length(); i++) {
            JSONObject jDayForecast = jArr.getJSONObject(i);

            // Now we have the json object so we can extract the data
            ForecastInfo df = new ForecastInfo();

            // We retrieve the timestamp (dt)
            df.timestamp = jDayForecast.getLong("dt");

            // Temp is an object
            JSONObject jTempObj = jDayForecast.getJSONObject("main");

            df.day = (float) jTempObj.getDouble("temp");
            df.min = (float) jTempObj.getDouble("temp_min");
            df.max = (float) jTempObj.getDouble("temp_max");
            df.cis = (float) jTempObj.getDouble("pressure");
            df.zachmu = (float) jTempObj.getDouble("humidity");
            df.morning = (float) jTempObj.getDouble("pressure");

            // Pressure and Humidity
        //    df.weather.setCis((int) jDayForecast.getDouble("pressure"));
         //   df.weather.setZachm((int) jDayForecast.getDouble("humidity"));

            // ...and now the weather
            JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
            JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
          ///  df.weather.currentCondition.setWeatherId(getInt("id", jWeatherObj));
            //df.weather.currentCondition.setDescr(getString("description", jWeatherObj));
            df.setOpiss((getString("description", jWeatherObj)));
         //   df.weather.currentCondition.setCondition(getString("main", jWeatherObj));
            df.setIcon(getString("icon", jWeatherObj));

            forecast.addForecast(df);

        }

        return forecast;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
