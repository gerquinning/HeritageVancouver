package com.gerquinn.heritagevancouver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Ger on 2017-03-02.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //All static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "buildingsDatabase";

    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_BUILDING_AREA = "area";
    private static final String KEY_BUILDING_NAME = "building_name";
    private static final String KEY_BUILDING_TYPE = "building_type";
    private static final String KEY_BUILDING_YEAR = "building_year";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_THUMB_URL = "thumb_url";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(String tableName, JSONArray jsonArray) throws JSONException {

        SQLiteDatabase db = this.getWritableDatabase();

        Log.v("version ", "Version number is "+db.getVersion());
        Log.d("DB OPEN", "Open or not: " + db.isOpen());
        Boolean tableExists = isTableExists(tableName, db.isOpen());

        if(tableExists) {
            db.delete(tableName, null,null);
        }else {

            String CREATE_TABLE;

            if(tableName == "all_buildings") {
                CREATE_TABLE = "CREATE TABLE " + tableName + "("
                        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADDRESS + " TEXT," + KEY_BUILDING_NAME + " TEXT,"
                        + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_BUILDING_TYPE + " TEXT," + KEY_BUILDING_AREA + " TEXT )";
            }else {
                CREATE_TABLE = "CREATE TABLE " + tableName + "("
                        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADDRESS + " TEXT," + KEY_BUILDING_NAME + " TEXT,"
                        + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_BUILDING_TYPE + " TEXT,"
                        + KEY_BUILDING_YEAR + " TEXT,"  + KEY_DESCRIPTION + " TEXT," + KEY_IMAGE_URL + " TEXT,"
                        + KEY_THUMB_URL + " TEXT )";
            }

            Log.d("TABLE CREATE", CREATE_TABLE);
            db.execSQL(CREATE_TABLE);
        }

        try {
            populateTable(tableName, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateTable(String tableName, JSONArray jsonArray) throws JSONException {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            values.put(KEY_ADDRESS, jsonObject.optString(KEY_ADDRESS)); //Building Address
            values.put(KEY_BUILDING_NAME, jsonObject.optString(KEY_BUILDING_NAME)); //Building Name
            values.put(KEY_LATITUDE, jsonObject.optString(KEY_LATITUDE)); //Building Latitude
            values.put(KEY_LONGITUDE, jsonObject.optString(KEY_LONGITUDE)); //Building Longitude
            values.put(KEY_BUILDING_TYPE, jsonObject.optString(KEY_BUILDING_TYPE)); //Building Type

            if(tableName == "all_buildings") {
                values.put(KEY_BUILDING_AREA, jsonObject.optString(KEY_BUILDING_AREA)); //Building Area
            }else {
                values.put(KEY_BUILDING_YEAR, jsonObject.optString(KEY_BUILDING_YEAR)); //Building Year
                values.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION)); //Building Latitude
                values.put(KEY_IMAGE_URL, jsonObject.optString(KEY_IMAGE_URL)); //Building Image URL
                values.put(KEY_THUMB_URL, jsonObject.optString(KEY_THUMB_URL)); //Building Image URL
            }

            // Inserting Row
            db.insert(tableName, null, values);
        }

        Log.d("TABLE LENGTH", " " + jsonArray.length());
        //String tableResults = getTableAsString(db, tableName);
        int tableRowCount = getProfilesCount(tableName);
        Log.d("TABLE RESULTS:", "There are " + tableRowCount + " rows");

        db.close();
    }

    public boolean isTableExists(String tableName, boolean openDb) {

        SQLiteDatabase db = this.getWritableDatabase();

        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                //db.close();
                db = getReadableDatabase();
            }
        }

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d("GET TABLE VALUES", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        allRows.close();
        return tableString;
    }

    public int getProfilesCount(String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        String countQuery = "SELECT  * FROM " + tableName;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}
