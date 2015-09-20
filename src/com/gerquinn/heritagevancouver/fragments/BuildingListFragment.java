package com.gerquinn.heritagevancouver.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gerquinn.heritagevancouver.BuildingDetailsActivity;
import com.gerquinn.heritagevancouver.BuildingListActivity;
import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.WalkingTourMapActivity;
import com.gerquinn.heritagevancouver.adapters.CustomListAdapter;
import com.gerquinn.heritagevancouver.database.WalkingTourItems;
import com.gerquinn.heritagevancouver.helpers.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BuildingListFragment extends Fragment{
	
	public LinearLayout lLayout;
	
	//List that takes details from Walking Tour Database
		private List<WalkingTourItems> walkingTourItems = new ArrayList<WalkingTourItems>();
		
		private static final String MYLISTKEY = "myListLabels";
	    
	    // Progress Dialog
	    private ProgressDialog pDialog;
		
		// Creating JSON Parser object
	    JSONParser jParser = new JSONParser();
	 
	    // url to get all products list
	    private static String url_all_buildings = "http://quinntechssential.com/heritage_vancouver/databases/android_connect/get_all_buildings.php";
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_BUILDINGS = "buildings";
	    
	    public String DB_NAME;
	    public String TABLE_NAME;
	    
	    private static final String TAG_BUILDING_NAME = "building_name";
	    private static final String TAG_BUILDING_YEAR = "building_year";
	    private static final String TAG_BUILDING_TYPE = "building_type";
	    private static final String TAG_BUILDING_ADDRESS = "address";
	    private static final String TAG_BUILDING_LATITUDE = "latitude";
	    private static final String TAG_BUILDING_LONGITUDE = "longitude";
	    private static final String TAG_BUILDING_DESCRIPTION = "description";
	    private static final String TAG_IMAGE_URL = "image_url";
	    private static final String TAG_THUMB_URL = "thumb_url";
	    
	    private ArrayList<String> buildingNameArray, buildingYearArray, buildingTypeArray, addressArray, latitudeArray, longitudeArray, descriptionArray, imageUrlArray;
	    
	    // products JSONArray
	    JSONArray buildings = null;
		
		public String thumb_url, last_item = "";
		public ListView L1;
		public ImageView thumb;
		Button footerButton;
		public BuildingDetailsActivity bda;
		public static BuildingListActivity bla;
		private WalkingTourMapActivity wta;
		
		Activity activity;
		
		public CustomListAdapter myadp;
		
		public static Context mContext;
		Toast toast;
	
	//@Override
	public View onCreatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the layout for this fragment
		lLayout = (LinearLayout) inflater.inflate(R.layout.activity_building_lists, container, false);
		
		//mContext = this;
		//activity = this;
		
		DB_NAME = getActivity().getIntent().getExtras().getString("databaseName");
		TABLE_NAME= getActivity().getIntent().getExtras().getString("tableName");
		
		// Loading products in Background Thread
        new LoadAllBuildings().execute();
		
		/**
         * Updating parsed JSON data into ListView
         * */
		L1 = (ListView) lLayout.findViewById(R.id.list1);
	    myadp = new CustomListAdapter(activity,walkingTourItems);
		L1.setAdapter(myadp);
		
		L1.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int num, long lnum){
				
				Intent i = new Intent(getActivity(), BuildingDetailsActivity.class);
				i.putExtra("buildingName", walkingTourItems.get(num).getBuildingName());
				i.putExtra("buildingType", walkingTourItems.get(num).getBuildingType());
				i.putExtra("buildingYear", walkingTourItems.get(num).getYear());
				i.putExtra("address", walkingTourItems.get(num).getAddress());
				i.putExtra("latitude", walkingTourItems.get(num).getLatitude());
				i.putExtra("longitude", walkingTourItems.get(num).getLongitude());
				i.putExtra("description", walkingTourItems.get(num).getDescription());
				i.putExtra("imageUrl", walkingTourItems.get(num).getImageUrl());
				startActivity(i);
			}
		});
			
		return lLayout;
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedState){
		super.onSaveInstanceState(savedState);
		
	}
	
	/*@Override
	public Object onRetainNonConfigurationInstance() {
	    return myadp;
	}*/
	
	public static Context getContext() {
	    return mContext;
	}
	
	public void setArrays(){
		buildingNameArray = new ArrayList<String>();
		buildingYearArray = new ArrayList<String>();
		buildingTypeArray = new ArrayList<String>();
		addressArray = new ArrayList<String>();
		latitudeArray = new ArrayList<String>();
		longitudeArray = new ArrayList<String>();
		descriptionArray = new ArrayList<String>();
		imageUrlArray = new ArrayList<String>();
		
		for(int i = 0; i <= walkingTourItems.size() - 1; i++){
			buildingNameArray.add(walkingTourItems.get(i).getBuildingName());
			buildingYearArray.add(String.valueOf(walkingTourItems.get(i).getYear()));
			buildingTypeArray.add(walkingTourItems.get(i).getBuildingType());
			addressArray.add(walkingTourItems.get(i).getAddress());
			latitudeArray.add(String.valueOf(walkingTourItems.get(i).getLatitude()));
			longitudeArray.add(String.valueOf(walkingTourItems.get(i).getLongitude()));
			descriptionArray.add(walkingTourItems.get(i).getDescription());
			imageUrlArray.add(walkingTourItems.get(i).getImageUrl());
		}
	}
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
	class LoadAllBuildings extends AsyncTask<String, String, String>{
		
		/**
         * getting All products from url
         * */
        @Override
		protected String doInBackground(String... args){
        	
        	//Building Parameters
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	//Getting JSON string from URL
        	JSONObject json = jParser.makeHttpRequest(url_all_buildings + "?tableName=" + TABLE_NAME, "POST", params);
        	
        	//Check your log cat for JSON response
        	//Log.d("All Buildings: ", json.toString());
        	
        	try{
        		
        		//Checking for SUCCESS TAG
        		int success = json.getInt(TAG_SUCCESS);
        		
        		if(success == 1){
        			
        			//Buildings found
        			//Getting Array of Buildings
        			buildings = json.getJSONArray(TAG_BUILDINGS);
        			
        			//Looping through all Buildings
        			for(int i = 0; i < buildings.length(); i++){
        				JSONObject c = buildings.getJSONObject(i);
        				WalkingTourItems wti = new WalkingTourItems();
        				
        				//Storing each json item in varible
        				wti.setBuildingName(c.getString(TAG_BUILDING_NAME));
        				wti.setYear(c.getInt(TAG_BUILDING_YEAR));
        				wti.setBuildingType(c.getString(TAG_BUILDING_TYPE));
        				wti.setAddress(c.getString(TAG_BUILDING_ADDRESS));
        				wti.setLatitude(c.getDouble(TAG_BUILDING_LATITUDE));
        				wti.setLongitude(c.getDouble(TAG_BUILDING_LONGITUDE));
        				wti.setDescription(c.getString(TAG_BUILDING_DESCRIPTION));
        				wti.setImageUrl(c.getString(TAG_IMAGE_URL));
        				wti.setThumbUrl(c.getString(TAG_THUMB_URL));
        				
        				walkingTourItems.add(wti);
        				
        			}
        			// notifying list adapter about data changes
                    // so that it renders the list view with updated data
        			//myadp.notifyDataSetChanged();
        		}else{
        			//No buildings found
        			pDialog.setMessage("No buildings found. Please check your connection.");
                	pDialog.setIndeterminate(false);
                	pDialog.setCancelable(false);
                	pDialog.show();
        			
        		}
        	}catch(JSONException e){
        		e.printStackTrace();
        	}catch(NullPointerException e){
        		e.printStackTrace();
        	}
        	
        	// Adding request to request queue
            //AppController.getInstance().addToRequestQueue(json);
        	
        	return null;
        }
        
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
		protected void onPostExecute(String file_url){
        	
        	//Dismiss the dialog after getting all buildings
        	pDialog.dismiss();
        	
        	// notifying list adapter about data changes
            // so that it renders the list view with updated data
			myadp.notifyDataSetChanged();
			
			footerButton = (Button) lLayout.findViewById(R.id.btnViewMap);
			footerButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setArrays();
					Intent i = new Intent(getActivity().getBaseContext(), WalkingTourMapActivity.class);
					
					i.putExtra("buildingNameArray", buildingNameArray);
					i.putExtra("buildingYearArray", buildingYearArray);
					i.putExtra("buildingTypeArray", buildingTypeArray);
					i.putExtra("addressArray", addressArray);
					i.putExtra("latitudeArray", latitudeArray);
					i.putExtra("longitudeArray", longitudeArray);
					i.putExtra("descriptionArray", descriptionArray);
					i.putExtra("imageUrlArray", imageUrlArray);
					startActivity(i);
				}
			});
			
        }
        
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute(){
        	super.onPreExecute();
        	pDialog = new ProgressDialog(getActivity());
        	pDialog.setMessage("Loading buildings. Please wait...");
        	pDialog.setIndeterminate(false);
        	pDialog.setCancelable(false);
        	pDialog.show();
        }
	}

}
