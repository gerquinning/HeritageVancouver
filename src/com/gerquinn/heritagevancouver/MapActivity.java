package com.gerquinn.heritagevancouver;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	
	boolean arrayBool = false;
	
	//Address, Latitude & Longitude
	String buildingName, address, buildingArea, buildingType, markerUrl = "";
	double latitude = 0;
	double longitude = 0;
	
	ArrayList<String> buildingNameArray = new ArrayList<String>();
	ArrayList<String> addressArray = new ArrayList<String>();
	ArrayList<String> latitudeArray = new ArrayList<String>();
	ArrayList<String> longitudeArray = new ArrayList<String>();
	ArrayList<String> areaArray = new ArrayList<String>();
	ArrayList<String> buildingTypeArray = new ArrayList<String>();
	
	public void getCoordinates(){
		arrayBool = getIntent().getExtras().getBoolean("Array Boolean");
		
		if(arrayBool){
			buildingNameArray = getIntent().getExtras().getStringArrayList("Building Name Array");
			addressArray = getIntent().getExtras().getStringArrayList("Building Address Array");
			latitudeArray = getIntent().getExtras().getStringArrayList("Latitude Array");
			longitudeArray = getIntent().getExtras().getStringArrayList("Longitude Array");
			areaArray = getIntent().getExtras().getStringArrayList("Area Array");
			buildingTypeArray = getIntent().getExtras().getStringArrayList("Building Type Array");
		}else{
			buildingName = getIntent().getExtras().getString("Building Name");
			address = getIntent().getExtras().getString("Building Address");
			latitude  = getIntent().getExtras().getDouble("Latitude");
			longitude  = getIntent().getExtras().getDouble("Longitude");
		}
	}
	
	//Function to load map. If map is not created it will create it for you
	private void initilizeMap(){
		
		MarkerOptions marker = null;
		
		if(googleMap == null){
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
			//SupportMapFragment mapFrag = (SupportMapFragment) getFragmentManager()
					//.findFragmentById(R.id.map);
			
			//googleMap = mapFrag.getMap();
			
			//Check if map is created successfully or not
			if(googleMap == null){
				Toast.makeText(getApplicationContext(),
						"Sorry! Unable to create maps", Toast.LENGTH_SHORT).show();
			}
		}
		
		if(arrayBool){
			
			for(int i = 0; i <= buildingNameArray.size() - 1; i++){
				
				latitude = Double.valueOf(latitudeArray.get(i));
				longitude = Double.valueOf(longitudeArray.get(i));
				
				switch(areaArray.get(i)){
				case "Arbutus Ridge":
					buildingArea = "arbutus_ridge";
					break;
				case "Downtown":
					buildingArea = "downtown";
					break;
				case "Downtown Eastside":
					buildingArea = "downtown_eastside";
					break;
				case "Dunbar Southlands":
					buildingArea = "dunbar_southlands";
					break;
				case "Fairview":
					buildingArea = "fairview";
					break;
				case "Grandview-Woodland":
					buildingArea = "grandview_woodland";
					break;
				case "Hastings-Sunrise":
					buildingArea = "hastings_sunrise";
					break;
				case "Kensington-Cedar Cottage":
					buildingArea = "kensington_cedar_cottage";
					break;
				case "Kerrisdale":
					buildingArea = "kerrisdale";
					break;
				case "Killarney":
					buildingArea = "killarney";
					break;
				case "Kitsilano":
					buildingArea = "kitsilano";
					break;
				case "Marpole":
					buildingArea = "marpole";
					break;
				case "Mount Pleasant":
					buildingArea = "mount_pleasant";
					break;
				case "Oakridge":
					buildingArea = "oakridge";
					break;
				case "Renfrew-Collingwood":
					buildingArea = "renfrew_collingwood";
					break;
				case "Riley Park":
					buildingArea = "riley_park";
					break;
				case "Shaughnessy":
					buildingArea = "shaughnessy";
					break;
				case "South Cambie":
					buildingArea = "south_cambie";
					break;
				case "Strathcona":
					buildingArea = "strathcona";
					break;
				case "Sunset":
					buildingArea = "sunset";
					break;
				case "Victoria-Fraserview":
					buildingArea = "victoria_fraserview";
					break;
				case "West End":
					buildingArea = "west_end";
					break;
				case "West Point Grey":
					buildingArea = "west_point_grey";
					break;
				}
				
				switch(buildingTypeArray.get(i)){	
				case "Commercial":
					buildingType = "commercial";
					break;
				case "Community":
					buildingType = "community";
					break;
				case "Educational":
					buildingType = "educational";
					break;				
				case "Government": 
					buildingType = "government";
					break;
				case "Hospital":
					buildingType = "hospital";
					break;
				case "Industrial":
					buildingType = "industrial";
					break;					
				case "Recreational":
					buildingType = "recreational";
					break;					
				case "Religious":
					buildingType = "religious";
					break;					
				case "Residential":
					buildingType = "residential";
					break;					
				case "Scenic":
					buildingType = "scenic";
					break;
					
				}
				
				markerUrl = buildingArea+ "_" + buildingType + "_pin.png";
				
				//Create marker
				marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(buildingNameArray.get(i));
			
				//Setting the Marker according to Neighbourhood and Building Type
				marker.icon(BitmapDescriptorFactory.fromAsset(markerUrl));
				
				//Adding Marker
				googleMap.addMarker(marker);
			}
			
		}else{
		
			//Create marker
			marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(buildingName);
		
			//Rose color icon
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
			
			//Adding Marker
			googleMap.addMarker(marker);
		
		}
		
		//Focusing Map to Location
		CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
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
		
		getCoordinates();
		
		//try {
	        MapsInitializer.initialize(getApplicationContext());
	   /*}catch (GooglePlayServicesNotAvailableException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }*/
		
		try{
			//Loading map
			initilizeMap();
		}catch(Exception e){
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
	protected void onResume(){
		super.onResume();
		initilizeMap();
	}
	
}
