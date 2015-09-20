/**************************************
 ********* Heritage Vancouver *********
 ********* Author: Ger Quinn **********
 *************** 2014 *****************
 **** http://quinntechssential.com ****
 **************************************/

package com.gerquinn.heritagevancouver;

import java.util.ArrayList;

import com.gerquinn.heritagevancouver.adapters.CheckListAdapter;
import com.gerquinn.heritagevancouver.database.DatabaseFunctions;
import com.gerquinn.heritagevancouver.helpers.PictureGeoTagging;
import com.gerquinn.heritagevancouver.helpers.UploadTask;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

public class UploadPictureActivity extends Activity{
	
	Uri uriFile;
	String fileUri = "";
	protected String[] fileNameArray;
	String last_item = "";
	double latitude, longitude, imgLatitude, imgLongitude = 0;
	String address;
	int uploadNum;
	
	//Get Values from all_buildings Table in Database
	DatabaseFunctions databaseFunctions = new DatabaseFunctions();
	
	PictureGeoTagging picGeoTag = new PictureGeoTagging();
    
    public String DB_NAME = "heritage_14";
    public String TABLE_NAME = "all_buildings";
    
	public ArrayList<String> addressArray = new ArrayList<String>();
	public ArrayList<String> latitudeArray = new ArrayList<String>();
	public ArrayList<String> longitudeArray = new ArrayList<String>();
	
	public ListView L1;
	CheckListAdapter adapter;
	CheckBox checkBox;
	View footerView;
	Button footerButton;
	public ImageView thumb;
	PackageManager packageManager;
	private Activity activity;
	public static Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_picture);
		
		activity = this;
		
		fileUri = getIntent().getExtras().getString("fileUri");
		packageManager = getPackageManager();
		
		//Get Coordinates from Photo
		picGeoTag.readGeoTagImage(fileUri);
		imgLatitude = picGeoTag.getImgLatitude();
		imgLongitude = picGeoTag.getImgLongitude();
		
		// Loading Nearby Address
		addressListSetup();
        
		getContext();
		
		/**
         * Updating Nearby Addresses into ListView
         * 
         * */
		L1 = (ListView)findViewById(R.id.list2);
		L1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE); 
		adapter = new CheckListAdapter(UploadPictureActivity.this, addressArray);       			
		L1.setAdapter(adapter);
		
		//Select which building you want to tag image to
		L1.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int num, long lnum){
				
				ViewGroup item;
				CheckBox checkbox;
				
				for(int i = 0; i < L1.getChildCount(); i++){
			        item = (ViewGroup)L1.getChildAt(i);
			        checkbox = (CheckBox)item.findViewById(R.id.checkBox1);
			        checkbox.setChecked(false);
			    }
				item = (ViewGroup)L1.getChildAt(num);
				uploadNum = num;
				adapter.removeAllChecks(num);
		        checkbox = (CheckBox)item.findViewById(R.id.checkBox1);
		        checkbox.setChecked(true);
				
			}
		});
		
		
		footerButton = (Button)findViewById(R.id.btnFooterUpload);
		footerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(addressArray != null && addressArray.size() > 0){
					//Set the new Lat and Long for the image and then Upload it to the remote folder
					picGeoTag.writeGeoTagImage(fileUri, Double.valueOf(latitudeArray.get(uploadNum)), Double.valueOf(longitudeArray.get(uploadNum)));
					UploadTask uploadTask = new UploadTask(activity, getApplicationContext());
					uploadTask.execute(fileUri);
					Intent i = new Intent(UploadPictureActivity.this, ImageGalleryActivity.class);
					startActivity(i);
					activity.finish();
				}
			}
		});
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	/*
	 * Get the Context
	 */
	public static Context getContext() {
	    return mContext;
	}
	
	/*public static class AddressViewHolder{
		private CheckBox checkBox ;  
	    private TextView textView ;  
	    public AddressViewHolder() {}  
	    public AddressViewHolder( TextView textView, CheckBox checkBox ) {  
	      this.checkBox = checkBox ;  
	      this.textView = textView ;  
	    }  
	    public CheckBox getCheckBox() {  
	      return checkBox;  
	    }  
	    public void setCheckBox(CheckBox checkBox) {  
	      this.checkBox = checkBox;  
	    }  
	    public TextView getTextView() {  
	      return textView;  
	    }  
	    public void setTextView(TextView textView) {  
	      this.textView = textView;  
	    }
	}*/
	
	/**
	 * Gets the buildings that are near to where the image was taken
	 */
	public void addressListSetup() {
		for (int i = 0; i < databaseFunctions.getLatitudeArray().size(); i++) {
			latitude = Double.valueOf(databaseFunctions.getLatitudeArray().get(i));
			longitude = Double.valueOf(databaseFunctions.getLongitudeArray().get(i));
			
			double latDiff = imgLatitude - latitude;
			double lonDiff = imgLongitude - longitude;
			
			if((latDiff <=  0.001 && latDiff >=  -0.001) && (lonDiff <= 0.0034 && lonDiff >=  -0.0034)){
				String[] splitAddressArray = databaseFunctions.getAddressArray().get(i).split(",");
				String splitAddressString = splitAddressArray[0];
				addressArray.add(splitAddressString);
				latitudeArray.add(String.valueOf(latitude));
				longitudeArray.add(String.valueOf(longitude));
			}
		}
	}
}