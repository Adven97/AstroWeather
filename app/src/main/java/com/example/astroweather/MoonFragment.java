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

public class MoonFragment extends Fragment {

    TextView rise;
    TextView moonSet;
    TextView fullMoon;
    TextView newMoon;
    TextView faza;
    TextView month;
    Info info;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TaskFragment", "onActivityCreated");
    }

    public static MoonFragment newInstance(Info info) {
       MoonFragment fragmentFirst = new MoonFragment();
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

        View view = inflater.inflate(R.layout.moon_fragment, container, false);

        rise = view.findViewById(R.id.rise_value);
        moonSet = view.findViewById(R.id.moonset_value);
        newMoon = view.findViewById(R.id.new_moon_value);
        fullMoon = view.findViewById(R.id.full_moon_value);
        faza = view.findViewById(R.id.faza_value);
        month = view.findViewById(R.id.month_value);

        Bundle bundle=getArguments();
        if(bundle !=null){
            info = (Info)bundle.getSerializable("SomeTask");

            rise.setText(info.getMoonRise());
            moonSet.setText(info.getMoonRSet());
            newMoon.setText(info.getNewMoon());
            fullMoon.setText(info.getFullMoon());
            faza.setText(info.getFaza());
            month.setText(info.getMonth());

        }


        return view;

    }

}

