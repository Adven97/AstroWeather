package com.example.astroweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static WeatherInfo getWeather(String data) throws JSONException {
        WeatherInfo weather = new WeatherInfo();

        // We create out JSONObject from the data
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
//        weather.currentCondition.setDescr(getString("description", JSONWeather));
//        weather.currentCondition.setCondition(getString("main", JSONWeather));
//        weather.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        weather.setWilgotnosc(getInt("humidity", mainObj));
        weather.setCis(getInt("pressure", mainObj));
      //  weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
      //  weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.setTemperatura(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jObj);
        weather.setWindSpeed(getFloat("speed", wObj));
        weather.setWindDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jObj);
        weather.setZachm(getInt("all", cObj));

        // We download the icon to show


        return weather;
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
