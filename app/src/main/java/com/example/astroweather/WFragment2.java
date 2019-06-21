package com.example.astroweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;

public class WFragment2 extends Fragment {

    public static TextView windDeg;
    public static TextView windStr;
    public static TextView wilgotnosc;
    public static TextView zachm;

    WeatherInfo info;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TaskFragment", "onActivityCreated");
    }

    public static WFragment2 newInstance(WeatherInfo info) {
        WFragment2 fragmentFirst = new WFragment2();
        Bundle args = new Bundle();
        args.putSerializable("SomeTask", info);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weather_fragment2, container, false);

        windDeg = view.findViewById(R.id.wiatrStopien_value);
        windStr = view.findViewById(R.id.wiatrSila_value);
        wilgotnosc = view.findViewById(R.id.wilgotnosc_value);
        zachm = view.findViewById(R.id.zachm_value);


        Bundle bundle=getArguments();
        if(bundle !=null){
            info = (WeatherInfo)bundle.getSerializable("SomeTask");

            windStr.setText(info.getWindSpeed()+"");
            windDeg.setText(info.getWindDeg()+"");
            wilgotnosc.setText(info.getWilgotnosc()+"");
            zachm.setText(info.getZachm()+"");

        }


        return view;

    }

}

