package com.example.astroweather;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class OtherAdapter extends FragmentPagerAdapter {

    private ArrayList<WeatherInfo> tasks;
    public OtherAdapter (FragmentManager fragmentManager, ArrayList<WeatherInfo> taskList) {
        super(fragmentManager);
        Log.i("MyCustomAdapter", "MyCustomAdapter constructor");
        tasks = taskList;

    }

    @Override
    public Fragment getItem(int position) {
        Log.i("MyCustomAdapter", "MyCustomAdapter getItem: " + position);

        if(position ==0) {
            return WFragment1.newInstance(tasks.get(position));
        }
        else{
            return WFragment2.newInstance(tasks.get(position));
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
