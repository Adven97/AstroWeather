package com.example.astroweather;


import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FragmentPagerAdapter adapterViewPager;
    TextView dateTime;
    public static Date timeNow;
    //Thread tMyLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeNow = Calendar.getInstance().getTime();
        dateTime.setText("Czas: "+timeNow.toString());

        Thread tMyLoc=new Thread(){
            @Override public void run(){
                try {
                    while(!isInterrupted()){
                        Thread.sleep(1000); //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override public void run() {

                                timeNow = Calendar.getInstance().getTime();
                                dateTime.setText("Czas: "+timeNow.toString());
                                // getFriendLocation();

                            } }); } }catch (InterruptedException e) {} } };
        tMyLoc.start();



        InfoSet tasksSet = InfoSet.get();
        ArrayList<Info> lista_tasks = tasksSet.getZadania();

        ViewPager vPager = findViewById(R.id.vp);

        adapterViewPager = new CustomAdapter(getSupportFragmentManager(), lista_tasks);

        vPager.setAdapter(adapterViewPager);

        dateTime = findViewById(R.id.maintime);
        timeNow = Calendar.getInstance().getTime();
        // dateTime.append(timeNow.toString());


//            Thread t = new Thread();
//            try {
//                t.sleep(1000);
//                // progressDialog.hide();
//                try {
//                    timeNow = Calendar.getInstance().getTime();
//                    dateTime.append(timeNow.toString());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                t.interrupt();
//            } catch (InterruptedException e) { }




        }

}

