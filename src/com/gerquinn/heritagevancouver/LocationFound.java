package com.gerquinn.heritagevancouver;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class LocationFound extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_found);
		
		//Get Action Bar
		ActionBar actionBar = getActionBar();
		
		//Enabling Up/Back Navigation
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

}
