package com.example.astroweather;

import java.util.ArrayList;
import java.util.List;

class WeatherForecast {

    private List<ForecastInfo> daysForecast = new ArrayList<ForecastInfo>();

    public void addForecast(ForecastInfo forecast) {
        daysForecast.add(forecast);
        System.out.println("Add forecast ["+forecast+"]");
    }

    public ForecastInfo getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}