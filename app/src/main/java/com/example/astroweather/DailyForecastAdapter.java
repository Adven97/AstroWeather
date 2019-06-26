package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DailyForecastAdapter extends FragmentPagerAdapter {

    private int numDays;
    private FragmentManager fm;
    private WeatherForecast forecast;
    private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");

    public DailyForecastAdapter(int numDays, FragmentManager fm, WeatherForecast forecast) {
        super(fm);
        this.numDays = numDays;
        this.fm = fm;
        this.forecast = forecast;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // We calculate the next days adding position to the current date
        Date d = new Date();
        Calendar gc =  new GregorianCalendar();
        gc.setTime(d);
        gc.add((GregorianCalendar.DAY_OF_MONTH), position+1);
        Log.d("tubedziejabanadata",GregorianCalendar.DAY_OF_MONTH+" xd ");
        Log.d("tubedziejabanadata",gc.getTime()+" xddddddd ");
        return sdf.format(gc.getTime());


    }

    @Override
    public Fragment getItem(int num) {
        ForecastInfo dayForecast = forecast.getForecast(num);
        DailyforecastFragment f = DailyforecastFragment.newInstance(dayForecast);
        f.setForecast(dayForecast);
        return f;
    }

    /*
     * Number of the days we have the forecast
     */
    @Override
    public int getCount() {

        return numDays;
    }

}
