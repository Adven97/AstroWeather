package com.example.astroweather;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ForecastInfo implements Serializable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //public Weather weather = new Weather();
  //  public ForecastTemp forecastTemp = new ForecastTemp();
    public long timestamp;

 //   public class ForecastTemp {
        public float day;
        public float min;
        public float max;
        public float cis;
        public float zachmu;
        public float morning;
        public String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String title) {
            icon = title;
        }



        public String opiss;

        public String getOpiss() {
            return opiss;
        }

        public void setOpiss(String title) {
            opiss = title;
        }
  //  }

    public String getStringDate() {
        return sdf.format(new Date(timestamp));
    }
}



