package com.gerquinn.heritagevancouver;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.database.WalkingTourItems;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WalkingTourMapActivity extends Activity {
	
	private GoogleMap googleMap;
	private WalkingTourItems wti  = new WalkingTourItems();
	private ArrayList<String> buildingNameArray, buildingYearArray, buildingTypeArray, addressArray, latitudeArray, longitudeArray, descriptionArray, imageUrlArray;
	boolean arrayBool = false;
	
	BuildingListActivity buildListActivity = new BuildingListActivity();
	
	int position = 1;

	// Address, Latitude & Longitude
	String buildingName, address, buildingArea, buildingType, markerUrl = "";
	double latitude = 0;
	double longitude = 0;
	
	public void getCoordinates(int position) {
		
		latitude = Double.valueOf(latitudeArray.get(position));
		longitude = Double.valueOf(longitudeArray.get(position));

	}

	// Function to load map. If map is not created it will create it for you
	private void initilizeMap() {

		MarkerOptions marker = null;

		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// SupportMapFragment mapFrag = (SupportMapFragment)
			// getFragmentManager()
			// .findFragmentById(R.id.map);

			// googleMap = mapFrag.getMap();

			// Check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! Unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

		for (int i = 0; i <= buildingNameArray.size() - 1; i++) {
			
			getCoordinates(i);
			
			markerUrl = "walking_tour_pin_" + (i + 1) + ".png";
			// Create marker
			marker = new MarkerOptions().position(
					new LatLng(latitude, longitude)).title(
					wti.getBuildingName());
			String[] splitAddressArray = addressArray.get(i).split(",");
			String splitAddressString = splitAddressArray[0];
			marker.title(buildingNameArray.get(i)).snippet(
					splitAddressString);
			// Setting the Marker according to Neighbourhood and Building Type
			marker.icon(BitmapDescriptorFactory.fromAsset(markerUrl));
			
			// Adding Marker
			googleMap.addMarker(marker);
			
			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
				
				@Override
				public void onInfoWindowClick(Marker clickMarker){
					if(clickMarker != null){
						System.out.println("CLICK! " + clickMarker.getTitle());
						for(int i = 0; i <= buildingNameArray.size() - 1; i++){
							if(clickMarker.getTitle().equals(buildingNameArray.get(i))){
								setValues(buildingNameArray.get(i), buildingYearArray.get(i), buildingTypeArray.get(i), addressArray.get(i), latitudeArray.get(i), longitudeArray.get(i), descriptionArray.get(i), imageUrlArray.get(i));
							}
						}
					}
				}
					
			});

		}

		// Focusing Map to Location
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
				latitude, longitude));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);

		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		googleMap.setMyLocationEnabled(true);

		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		googleMap.getUiSettings().setRotateGesturesEnabled(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		getValues();
		getCoordinates(position);

		// try {
		MapsInitializer.initialize(getApplicationContext());
		/*
		 * }catch (GooglePlayServicesNotAvailableException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		try {
			// Loading map
			initilizeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	public void getValues(){
		buildingNameArray = new ArrayList<String>();
		buildingYearArray = new ArrayList<String>();
		buildingTypeArray = new ArrayList<String>();
		addressArray = new ArrayList<String>();
		latitudeArray = new ArrayList<String>();
		longitudeArray = new ArrayList<String>();
		descriptionArray = new ArrayList<String>();
		imageUrlArray = new ArrayList<String>();
		
		buildingNameArray = getIntent().getExtras().getStringArrayList("buildingNameArray");
		buildingYearArray = getIntent().getExtras().getStringArrayList("buildingYearArray");
		buildingTypeArray = getIntent().getExtras().getStringArrayList("buildingTypeArray");
		addressArray = getIntent().getExtras().getStringArrayList("addressArray");
		latitudeArray = getIntent().getExtras().getStringArrayList("latitudeArray");
		longitudeArray = getIntent().getExtras().getStringArrayList("longitudeArray");
		descriptionArray = getIntent().getExtras().getStringArrayList("descriptionArray");
		imageUrlArray = getIntent().getExtras().getStringArrayList("imageUrlArray");
	}
	
	public void setValues(String buildingName, String buildingYear, String buildingType, String address, String latitude, String longitude, String description, String imageUrl){
		
		Intent i = new Intent(WalkingTourMapActivity.this, BuildingDetailsActivity.class);
		i.putExtra("buildingName", buildingName);
		i.putExtra("buildingType", buildingType);
		i.putExtra("buildingYear", buildingYear);
		i.putExtra("address", address);
		i.putExtra("latitude", latitude);
		i.putExtra("longitude", longitude);
		i.putExtra("description", description);
		i.putExtra("imageUrl", imageUrl);
		startActivity(i);
	}
}


