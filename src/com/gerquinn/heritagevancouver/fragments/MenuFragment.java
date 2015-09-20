/**
 * 
 */
package com.gerquinn.heritagevancouver.fragments;

import com.gerquinn.heritagevancouver.CapturePhotoActivity;
import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.SearchNearbyActivity;
import com.gerquinn.heritagevancouver.WalkingTourActivity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Ger
 *
 */
public class MenuFragment extends Fragment {
	
	ImageView searchNearbyImage, walkingTourImage, listImage, mapImage, photosImage, virtualImage;
	Button listButton, mapButton, photosButton, virtualButton;
	TextView mainTitle;

	//@Override
	public View onCreatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the layout for this fragment
		LinearLayout lLayout = (LinearLayout) inflater.inflate(R.layout.activity_main, container, false);
		
		//ImageView set up
				searchNearbyImage = (ImageView)lLayout.findViewById(R.id.SearchNearbyButton);
				walkingTourImage = (ImageView)lLayout.findViewById(R.id.WalkingTourButton);
				listImage = (ImageView)lLayout.findViewById(R.id.ListButton);
				//mapImage = (ImageView)lLayout.findViewById(R.id.MapButton);
				photosImage = (ImageView)lLayout.findViewById(R.id.PhotosButton);
				//virtualImage = (ImageView)lLayout.findViewById(R.id.VirtualButton);
				
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
				
		return lLayout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		//MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		
		//Associate searchable configuration with the SearchView
		/*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();*/
		/*searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));*/
		
		//return super.onCreateOptionsMenu(menu, inflater);
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
	* Load the Map Activity
	* */
	private void BuildingListLoad(){
			
		// Start your map activity
		//Intent i = new Intent(MainActivity.this, MapActivity.class);
		//Intent i = new Intent(MainActivity.this, WestHastingsActivity.class);
		//startActivity(i);
	}
		
	/**
	* Launching new Location Found activity
	* */
	private void SearchNearby(){
		//Intent i = new Intent(MainActivity.this, LocationFound.class);
		Intent i = new Intent(getActivity(), SearchNearbyActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","all_buildings");
		startActivity(i);
	}
		
	private void CameraLoad(){
		Intent i = new Intent(getActivity(), CapturePhotoActivity.class);
		startActivity(i);
	}
	
	/**
	* Load the Walking Tours Activity
	* */
	private void WalkingToursLoad(){
		// Start your map activity
		//Intent i = new Intent(MainActivity.this, MapActivity.class);
		Intent i = new Intent(getActivity(), WalkingTourActivity.class);
		startActivity(i);
		
		
	} 

}
