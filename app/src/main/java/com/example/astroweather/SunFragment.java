package com.example.astroweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SunFragment extends Fragment {

    TextView rise;
    TextView sunSet;
    TextView twilight;
    TextView down;

    Info info;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TaskFragment", "onActivityCreated");
    }

    public static SunFragment newInstance(Info info) {
        SunFragment fragmentFirst = new SunFragment();
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

        View view = inflater.inflate(R.layout.sun_fragment, container, false);

        rise = view.findViewById(R.id.sunrise_value);
        sunSet = view.findViewById(R.id.sunset_value);
        down = view.findViewById(R.id.down_value);
        twilight = view.findViewById(R.id.twilight_value);


        Bundle bundle=getArguments();
        if(bundle !=null){
            info = (Info)bundle.getSerializable("SomeTask");

            rise.setText(info.getSunRise());
            sunSet.setText(info.getSunSet());
            down.setText(info.getDown());
            twilight.setText(info.getTwilight());


        }


        return view;

    }

}