package com.example.astroweather;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

            Button start;
            Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act);

        start = findViewById(R.id.start);
        settings = findViewById(R.id.settings);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), AstroActivity.class));

        }});

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

            }});

    }
}




