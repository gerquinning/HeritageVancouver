package com.gerquinn.heritagevancouver.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

	//All static variables
	//Database Version
	private static final int DATABASE_VERSION = 1;
	
	//Database Name
	private static final String DATABASE_NAME = "buildingContructorTest_01";
	//private static final String DATABASE_NAME = "";
	
	//West Hastings table name
	private static final String TABLE_WEST_HASTINGS = "west_hastings_test_table_01";
	//private static final String TABLE_WEST_HASTINGS = "";
	
	//West Hastings Table Column names
	private static final String KEY_ID = "id";
	private static final String KEY_BUILDING_NAME = "building_name";
	private static final String KEY_BUILDING_YEAR = "building_year";
	private static final String KEY_BUILDING_TYPE = "building_type";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_IMAGE_URL = "image_url";
	private static final String KEY_THUMB_URL = "thumb_url";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	 // Adding new building
    public void addBuilding(BuildingConstructors hastings) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_BUILDING_NAME, hastings.getBuildingName()); //Building Name
        values.put(KEY_ADDRESS, hastings.getAddress()); //Building Address
        values.put(KEY_LATITUDE, hastings.getLatitude()); //Building Latitude
        values.put(KEY_LONGITUDE, hastings.getLongitude()); //Building Longitude
        values.put(KEY_DESCRIPTION, hastings.getDescription()); //Building Description
        values.put(KEY_IMAGE_URL, hastings.getImageURL()); //Building Image
        values.put(KEY_THUMB_URL, hastings.getThumbURL()); //Building Thumbnail
 
        // Inserting Row
        db.insert(TABLE_WEST_HASTINGS, null, values);
        db.close(); // Closing database connection
    }
 
    // Deleting single contact
    public void deleteContact(BuildingConstructors buildingContructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEST_HASTINGS, KEY_ID + " = ?",
                new String[] { String.valueOf(buildingContructor.getID()) });
        db.close();
    }
    
    // Getting All Buildings
    public List<BuildingConstructors> getAllBuildings() {
        List<BuildingConstructors> buildingList = new ArrayList<BuildingConstructors>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEST_HASTINGS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BuildingConstructors buildingContructor = new BuildingConstructors();
                buildingContructor.setID(Integer.parseInt(cursor.getString(0)));
                buildingContructor.setBuildingName(cursor.getString(1));
                buildingContructor.setAddress(cursor.getString(2));
                buildingContructor.setLatitude(Double.parseDouble(cursor.getString(3)));
                buildingContructor.setLongitude(Double.parseDouble(cursor.getString(4)));
                buildingContructor.setDescription(cursor.getString(5));
                buildingContructor.setImageURL(cursor.getString(6));
                buildingContructor.setThumbURL(cursor.getString(7));
                // Adding contact to list
                buildingList.add(buildingContructor);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return buildingList;
    }
    
    // Getting single building
    BuildingConstructors getBuilding(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_WEST_HASTINGS, new String[] { KEY_ID,
        		KEY_BUILDING_NAME, KEY_BUILDING_YEAR, KEY_BUILDING_TYPE, KEY_ADDRESS, KEY_LATITUDE, KEY_LONGITUDE, KEY_DESCRIPTION, KEY_IMAGE_URL, KEY_THUMB_URL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        BuildingConstructors buildingContructor = new BuildingConstructors(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), Double.parseDouble(cursor.getString(5)), 
                Double.parseDouble(cursor.getString(6)), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        // return contact
        return buildingContructor;
    }
    
    // Getting contacts Count
    public int getBuildingsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WEST_HASTINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WHASTINGS_TABLE = "CREATE TABLE " + TABLE_WEST_HASTINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BUILDING_NAME + " TEXT," + KEY_BUILDING_NAME + " NUMBER," + KEY_BUILDING_TYPE + " TEXT,"
                + KEY_ADDRESS + " TEXT," +  KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_DESCRIPTION + " TEXT," + KEY_IMAGE_URL + " TEXT," + KEY_THUMB_URL + " TEXT )";
        db.execSQL(CREATE_WHASTINGS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEST_HASTINGS);
 
        // Create tables again
        onCreate(db);
    }
    
    // Updating single Building
    public int updateBuilding(BuildingConstructors buildingContructor) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_BUILDING_NAME, buildingContructor.getBuildingName());
        values.put(KEY_BUILDING_YEAR, buildingContructor.getBuildingYear());
        values.put(KEY_BUILDING_TYPE, buildingContructor.getBuildingType());
        values.put(KEY_ADDRESS, buildingContructor.getAddress());
        values.put(KEY_LATITUDE, buildingContructor.getLatitude());
        values.put(KEY_LONGITUDE, buildingContructor.getLongitude());
        values.put(KEY_DESCRIPTION, buildingContructor.getDescription());
        values.put(KEY_IMAGE_URL, buildingContructor.getImageURL());
        values.put(KEY_THUMB_URL, buildingContructor.getThumbURL());
 
        // updating row
        return db.update(TABLE_WEST_HASTINGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(buildingContructor.getID()) });
    }
	
}
