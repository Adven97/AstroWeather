package com.example.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static double longtitudeValue=19.457503;
    public static double latitudeValue = 51.788974;
    public static double refreshTimeValue=15;
    public static double currentLomg = longtitudeValue;
    public static double currentLat = latitudeValue;

    EditText longtitude;
    EditText latitude;
    EditText refresh;
    boolean isOk;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        isOk =true;
        longtitude = findViewById(R.id.longtitude);
        latitude = findViewById(R.id.latitude);
        refresh = findViewById(R.id.refresh);
        save = findViewById(R.id.savebutt);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (longtitude.getText().toString().length() > 0) {
                    double lngt = Double.parseDouble(longtitude.getText().toString());
                    if(lngt >180 || lngt < -180){
                        Toast.makeText(getBaseContext(), "Longtitude shall be between -180 and 180", Toast.LENGTH_LONG).show();
                        isOk =false;
                    }
                    else {
                        longtitudeValue = lngt;
                        isOk =true;
                    }
                }

                if (latitude.getText().toString().length() > 0) {
                    double lat = Double.parseDouble(latitude.getText().toString());
                    if(lat >90 || lat < -90){
                        Toast.makeText(getBaseContext(), "Latitude shall be between -90 and 90", Toast.LENGTH_LONG).show();
                        isOk =false;
                    }
                    else {
                        latitudeValue = lat;
                        isOk =true;
                    }

                }

                if (refresh.getText().toString().length() > 0) {
                    refreshTimeValue = Double.parseDouble(refresh.getText().toString());
                }
                if(isOk){
                    startActivity(new Intent(getApplicationContext(), AstroActivity.class));
                }

            }
    });
}
}

