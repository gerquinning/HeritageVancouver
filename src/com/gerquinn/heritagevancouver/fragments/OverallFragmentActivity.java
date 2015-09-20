package com.gerquinn.heritagevancouver.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class OverallFragmentActivity extends FragmentActivity{
	
	public String buildingName, address, description, shortImageUrl, imageUrl = " ";
	public double latitude,longitude = 0.0;

	public OverallFragmentActivity() {
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	            // During initial setup, plug in the details fragment.
	    	BuildingDetailsFragment details = new BuildingDetailsFragment();
        
	    	/*buildingName = details.setArguments(getIntent().getExtras().getString("buildingName"));
	    	address = details.setArguments(getIntent().getExtras().getString("address"));
	    	latitude = details.setArguments(getIntent().getExtras().getDouble("latitude"));
	    	longitude = details.setArguments(getIntent().getExtras().getDouble("longitude"));
	    	description = details.setArguments(getIntent().getExtras().getString("description"));
	    	imageUrl = details.setArguments(getIntent().getExtras().getString("imageUrl"));*/
	    	
	    	getSupportFragmentManager().beginTransaction().add(
                    android.R.id.content, details).commit();
	    }
		
	}

}
