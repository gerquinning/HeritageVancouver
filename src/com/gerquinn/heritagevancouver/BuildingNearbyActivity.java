package com.gerquinn.heritagevancouver;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.database.DatabaseHandler;
import com.gerquinn.heritagevancouver.database.BuildingConstructors;
import com.gerquinn.heritagevancouver.location.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuildingNearbyActivity extends Activity implements LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

private GoogleMap googleMap;
	
	//Address, Latitude & Longitude
	String buildingName, address = "";
	double latitude = 0;
	double longitude = 0;
	
	Location mCurrentLocation;
	LocationClient mLocationClient;
	double distanceBetween = 0;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	Toast toast;
	CharSequence text;
	int duration = Toast.LENGTH_SHORT;
	
	public ArrayList<String> building_name_array  = new ArrayList<String>();
	public ArrayList<String> address_array = new ArrayList<String>();
	public ArrayList<Double> latitude_array = new ArrayList<Double>();
	public ArrayList<Double> longitude_array = new ArrayList<Double>();
	public ArrayList<String> description_array = new ArrayList<String>();
	public ArrayList<String> image_URI_array = new ArrayList<String>();
	public ArrayList<String> thumb_URI_array = new ArrayList<String>();
	public int buildingCount = 0;
	
	public void buildingListSetup(){
		
		DatabaseHandler db = new DatabaseHandler(this);
		List<BuildingConstructors> westHastings = db.getAllBuildings();
        
        for (BuildingConstructors wh : westHastings) {
            
        	String log = "Id: " + wh.getID() + " ,Building Name: " + wh.getBuildingName() + ", Address: " + wh.getAddress();
            // Writing Contacts to log
        	Log.d("Name: ", log);
        	
            building_name_array.add(wh.getBuildingName());
			address_array.add(wh.getAddress());
			latitude_array.add(wh.getLatitude());
			longitude_array.add(wh.getLongitude());
			description_array.add(wh.getDescription());
			image_URI_array.add(wh.getImageURL());
			thumb_URI_array.add(wh.getThumbURL());
        }
	}
	
	public double checkNearby(double lat1, double lng1, double lat2, double lng2){
		double earthRadius = 6371;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    //int meterConversion = 1000;

	    return dist;
	}
	
	public void getCoordinates(){
		/*buildingName = getIntent().getExtras().getString("Building Name");
		address = getIntent().getExtras().getString("Building Address");
		latitude  = getIntent().getExtras().getDouble("Latitude");
		longitude  = getIntent().getExtras().getDouble("Longitude");*/
		
		 // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(this);
        
        if (gpsTracker.canGetLocation())
        {
            latitude = gpsTracker.latitude;

            longitude = gpsTracker.longitude;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
	}
	
	//Function to load map. If map is not created it will create it for you
	private void initilizeMap(){
		
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
		
		for(int i = 0; i < building_name_array.size(); i++){
			
			distanceBetween = checkNearby(latitude, longitude, latitude_array.get(i), longitude_array.get(i));
			System.out.println("Distance Between: " + distanceBetween);
			
			if(distanceBetween <= 3){
				//Create marker
				MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude_array.get(i), longitude_array.get(i))).title(building_name_array.get(i));
				
				//Rose color icon
				marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
				
				//Adding Marker
				googleMap.addMarker(marker);
				
				buildingCount++;
			}
		}
		
		if(buildingCount <= 0){
			
			text = "No buildings nearby";	
		}
		else{
			text = buildingCount + " Buildings found";
		}
		
		Context context = getApplicationContext();
		toast = Toast.makeText(context, text, duration);
		toast.show();
		
		//Focusing Map to Location
		CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
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
	public void onConnected(Bundle arg0) {
		// Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //showErrorDialog(connectionResult.getErrorCode());
        }
    }
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		getCoordinates();
		buildingListSetup();
		
		//try {
	        MapsInitializer.initialize(getApplicationContext());
	    /*} catch (GooglePlayServicesNotAvailableException e) {
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
	public void onDisconnected() {
		// Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume(){
		super.onResume();
		initilizeMap();
	}

	@Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
       // mLocationClient.connect();
    }

	@Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        //mLocationClient.disconnect();
        super.onStop();
        
    }

}
