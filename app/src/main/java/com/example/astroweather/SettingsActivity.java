package com.example.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    public static double longtitudeValue=19.457503;
    public static double latitudeValue = 51.788974;
    public static double refreshTimeValue;
    public static double currentLomg = longtitudeValue;
    public static double currentLat = latitudeValue;

    EditText longtitude;
    EditText latitude;
    EditText refresh;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //refreshTimeValue = 10.0;

        longtitude = findViewById(R.id.longtitude);
        latitude = findViewById(R.id.latitude);
        refresh = findViewById(R.id.refresh);
        save = findViewById(R.id.savebutt);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (longtitude.getText().toString().length() > 0) {
                    longtitudeValue = Double.parseDouble(longtitude.getText().toString());
                }

                if (latitude.getText().toString().length() > 0) {
                    latitudeValue = Double.parseDouble(latitude.getText().toString());
                }

                if (refresh.getText().toString().length() > 0) {
                    refreshTimeValue = Double.parseDouble(refresh.getText().toString());
                }
                startActivity(new Intent(getApplicationContext(), AstroActivity.class));
            }
    });
}
}

