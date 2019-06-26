package com.example.astroweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperForecast extends SQLiteOpenHelper {

    private static final String TABLE = "json_forecast_table";

    public DatabaseHelperForecast(Context context){
        super(context,TABLE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE "+TABLE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, CITY TEXT, JSON_W TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // String temp = "DROP IF TABLE EXISTS "+TABLE;
        // db.execSQL(temp);
        //  onCreate(db);
    }

    public boolean addDataf(String jason, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("JSON_W", jason);
        contentValues.put("CITY", city);

        Log.d("auuuuuuuuuuuuu", "addData: Adding " + jason + " to " + TABLE);

        long result = db.insert(TABLE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getForecastByCity(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT JSON_W FROM " + TABLE+
                " WHERE CITY = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLastForecast(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM json_forecast_table ORDER BY ID DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
