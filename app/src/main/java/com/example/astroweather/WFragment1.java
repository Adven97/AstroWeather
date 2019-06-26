package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class WFragment1 extends Fragment {

    public static TextView miasto;
    public static TextView temperatura;
    public static TextView cis;
    public static TextView opis;
    public static ImageView pogoda1;

    WeatherInfo info;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TaskFragment", "onActivityCreated");
    }

    public static WFragment1 newInstance(WeatherInfo info) {
        WFragment1 fragmentFirst = new WFragment1();
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

        View view = inflater.inflate(R.layout.weather_fragment1, container, false);

        miasto = view.findViewById(R.id.miasto_value);
        temperatura = view.findViewById(R.id.temperatura_value);
        cis = view.findViewById(R.id.cis_value);
        opis = view.findViewById(R.id.opis);
        pogoda1 = (ImageView) view.findViewById(R.id.imageViewPogoda1);

        Bundle bundle=getArguments();
        if(bundle !=null){
            info = (WeatherInfo)bundle.getSerializable("SomeTask");

//            miasto.setText(info.getMiasto());
//            temperatura.setText(info.getTemperatura()+" C");
//            cis.setText(info.getCis()+" hPa");
//            opis.setText(info.getOpis()+" no i chuj");

           /// String tem = info.getIcon();
           // setIcon(tem,pogoda1);
        }


        return view;

    }


}

