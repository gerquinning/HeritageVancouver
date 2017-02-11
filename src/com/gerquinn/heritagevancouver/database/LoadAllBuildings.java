package com.gerquinn.heritagevancouver.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.gerquinn.heritagevancouver.MainActivity;
import com.gerquinn.heritagevancouver.helpers.JSONParser;

/**
 * Background Async Task to Load all buildings by making HTTP Request
 * */
public class LoadAllBuildings extends AsyncTask<String, String, String> {

	//Which Activity is calling the Database
	Activity activity;
	
	// Progress Dialog
    private ProgressDialog pDialog;
	
	// Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    // url to get all products list
    private static String url_all_buildings = "http://quinntechssential.com/heritage_vancouver/databases/android_connect/get_all_buildings.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BUILDINGS = "buildings";
    
    public String DB_NAME = "heritage_14";
    public String TABLE_NAME = "all_buildings";
    
    //Tags for Database
    private static final String TAG_BUILDING_NAME = "building_name";
    private static final String TAG_BUILDING_TYPE = "building_type";
    private static final String TAG_BUILDING_ADDRESS = "address";
    private static final String TAG_BUILDING_LATITUDE = "latitude";
    private static final String TAG_BUILDING_LONGITUDE = "longitude";
    private static final String TAG_AREA = "area";
    
    // products JSONArray
    JSONArray buildings = null;
    
    public LoadAllBuildings(Activity activity){
    	this.activity = activity;
    }
    
	/**
	 * getting All products from url
	 * */
	@Override
	protected String doInBackground(String... args) {

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// Getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest(url_all_buildings
				+ "?tableName=" + TABLE_NAME, "POST", params);

		// Check your log cat for JSON response
		// Log.d("All Buildings: ", json.toString());

		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// Buildings found
				// Getting Array of Buildings
				buildings = json.getJSONArray(TAG_BUILDINGS);

				// Looping through all Buildings
				for (int i = 0; i < buildings.length(); i++) {
					JSONObject c = buildings.getJSONObject(i);

					// Storing each json item in varible
					String buildingName = c.getString(TAG_BUILDING_NAME);
					String buildingType = c.getString(TAG_BUILDING_TYPE);
					String address = c.getString(TAG_BUILDING_ADDRESS);
					double latitude = c.getDouble(TAG_BUILDING_LATITUDE);
					double longitude = c.getDouble(TAG_BUILDING_LONGITUDE);
					String area = c.getString(TAG_AREA);
					
					//DatabaseFunctions.DatabaseFunctions(address, buildingName, latitude, longitude, buildingType,  area);
					DatabaseFunctions.setArrays(address, buildingName, latitude, longitude, buildingType,  area);
				}
			} else {
				// No buildings found
				pDialog.setMessage("No buildings found. Please check your connection.");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();

			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			pDialog.setMessage("No buildings found. Please check your connection.");
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String file_url) {
		// Dismiss the dialog after getting all buildings
		pDialog.dismiss();
		Intent i = new Intent(activity, MainActivity.class);
		activity.startActivity(i);
		activity.finish();
		// Updating UI from Background Thread
		/*runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buildingListSetup();
			}
		});*/
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("Loading buildings. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
}