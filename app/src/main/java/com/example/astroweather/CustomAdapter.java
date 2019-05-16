package com.example.astroweather;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class CustomAdapter extends FragmentPagerAdapter {

   private ArrayList<Info> tasks;
    public CustomAdapter (FragmentManager fragmentManager, ArrayList<Info> taskList) {
        super(fragmentManager);
        Log.i("MyCustomAdapter", "MyCustomAdapter constructor");
        tasks = taskList;

    }

    @Override
    public Fragment getItem(int position) {
        Log.i("MyCustomAdapter", "MyCustomAdapter getItem: " + position);

        if(position ==0) {
            return MoonFragment.newInstance(tasks.get(position));
        }
        else{
            return SunFragment.newInstance(tasks.get(position));
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
