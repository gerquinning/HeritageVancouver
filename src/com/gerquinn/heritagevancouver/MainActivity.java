package com.gerquinn.heritagevancouver;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity{

	ImageView searchNearbyImage, walkingTourImage, listImage, mapImage, photosImage, virtualImage;
	Button listButton, mapButton, photosButton, virtualButton;
	TextView mainTitle;
	
	/**
    * Load the Map Activity
    * */
	private void BuildingListLoad(){
		
		// Start your map activity
		//Intent i = new Intent(MainActivity.this, MapActivity.class);
		//Intent i = new Intent(MainActivity.this, WestHastingsActivity.class);
		//startActivity(i);
	}
	
	private void CameraLoad(){
		Intent i = new Intent(MainActivity.this, CapturePhotoActivity.class);
		startActivity(i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.activity_main);
		
		//Text Setup
		//mainTitle = (TextView)findViewById(R.id.MainTitle);
		//mainTitle.setText("Welcome to Vancouver's Heritage Buildings");
		
		//ImageView set up
		searchNearbyImage = (ImageView)findViewById(R.id.SearchNearbyButton);
		walkingTourImage = (ImageView)findViewById(R.id.WalkingTourButton);
		listImage = (ImageView)findViewById(R.id.ListButton);
		//mapImage = (ImageView)findViewById(R.id.MapButton);
		photosImage = (ImageView)findViewById(R.id.PhotosButton);
		//virtualImage = (ImageView)findViewById(R.id.VirtualButton);
		
		walkingTourImage.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				WalkingToursLoad();
			}
			
		});
		
		
		searchNearbyImage.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				SearchNearby();
			}
		});
		
		listImage.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				BuildingListLoad();
			}
		});
		
		photosImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CameraLoad();
			}
		});
		
		/*mapImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MapLoad();
			}
		});
		
		virtualImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO
			}
		});*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		
		//Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
		/*searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));*/
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
     * On selecting action bar icons
     * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		int itemId = item.getItemId();
		
		switch(itemId){
		
		case R.id.action_search:
			//search action
			return true;
			
		case R.id.action_location_found:
			//location found
			SearchNearby();
			return true;
			
		case R.id.action_refresh:
			//refresh
			return true;
			
		case R.id.action_help:
			//help action
			return true;
			
		case R.id.action_check_updates:
			//check for updates action
			return true;
			
		case R.id.action_map:
			//map action
			SearchNearby();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
				
		}
		
		/*
		if (itemId == R.id.action_search) {
			//search action
			return true;
		} else if (itemId == R.id.action_location_found) {
			//location found
			//LocationFound();
			MapLoad();
			return true;
		} else if (itemId == R.id.action_refresh) {
			//refresh
			return true;
		} else if (itemId == R.id.action_help) {
			//help action
			return true;
		} else if (itemId == R.id.action_check_updates) {
			//check for updates action
			return true;
		} else if(itemId == R.id.action_map){
			//map action
			MapLoad();
			return true;
		}else {
			return super.onOptionsItemSelected(item);
		}*/
	}
	
	/**
     * Launching new Location Found activity
     * */
	private void SearchNearby(){
		//Intent i = new Intent(MainActivity.this, LocationFound.class);
		Intent i = new Intent(MainActivity.this, SearchNearbyActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","all_buildings");
		startActivity(i);
	}
	
	/**
	* Load the Walking Tours Activity
	* */
	private void WalkingToursLoad(){
		// Start your map activity
		//Intent i = new Intent(MainActivity.this, MapActivity.class);
		Intent i = new Intent(MainActivity.this, WalkingTourActivity.class);
		startActivity(i);
		
		
	} 

}
