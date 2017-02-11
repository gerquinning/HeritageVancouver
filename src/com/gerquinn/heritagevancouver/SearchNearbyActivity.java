package com.gerquinn.heritagevancouver;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.adapters.NothingSelectedSpinnerAdapter;
import com.gerquinn.heritagevancouver.database.DatabaseFunctions;
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

public class SearchNearbyActivity extends Activity implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private GoogleMap googleMap;

	private SeekBar distanceBar = null;
	public TextView distanceLabel, buildingsFoundLabel;
	public Button listButton, mapButton;
	public LinearLayout layout1;

	// Address, Latitude & Longitude
	String buildingName, address, buildingType, buildingArea, markerUrl = "";
	double latitude = 0;
	double longitude = 0;

	Location mCurrentLocation;
	LocationClient mLocationClient;
	double distanceBetween = 0;
	public int buildingCount = 0;
	public int progressChanged = 1;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	Toast toast;
	CharSequence text;
	int duration = Toast.LENGTH_SHORT;
	int THREAD_TIME_OUT = 3000;
	
	//Get Values from all_buildings Table in Database
	DatabaseFunctions databaseFunctions = new DatabaseFunctions();

	// Strings that deal with Neighbourhood and Building Type Choices from
	// Spinner
	public String areaString = "All";
	public String categoryString = "All";

	// List populated from Database
	public ArrayList<String> idArray = new ArrayList<String>();
	public ArrayList<String> addressArray = new ArrayList<String>();
	public ArrayList<String> buildingNameArray = new ArrayList<String>();
	public ArrayList<Double> latitudeArray = new ArrayList<Double>();
	public ArrayList<Double> longitudeArray = new ArrayList<Double>();
	public ArrayList<String> buildingTypeArray = new ArrayList<String>();
	public ArrayList<String> areaArray = new ArrayList<String>();

	// Temp Lists populated by conditions
	public ArrayList<String> idNearArray;
	public ArrayList<String> addressNearArray = new ArrayList<String>();
	public ArrayList<String> buildingNearNameArray = new ArrayList<String>();
	public ArrayList<Double> latitudeNearArray = new ArrayList<Double>();
	public ArrayList<Double> longitudeNearArray = new ArrayList<Double>();
	public ArrayList<String> latitudeNearStringArray = new ArrayList<String>();
	public ArrayList<String> longitudeNearStringArray = new ArrayList<String>();
	public ArrayList<String> buildingNearTypeArray = new ArrayList<String>();
	public ArrayList<String> areaNearArray = new ArrayList<String>();

	// public final String[] neighbourhoodArray = [""]
	// public final ArrayList<String> categoryArray = new ArrayList<String>();

	public void buildingListSetup() {
		
		buildingCount = 0;
		
		addressNearArray = new ArrayList<String>();
		buildingNearNameArray = new ArrayList<String>();
		latitudeNearArray = new ArrayList<Double>();
		longitudeNearArray = new ArrayList<Double>();
		latitudeNearStringArray = new ArrayList<String>();
		longitudeNearStringArray = new ArrayList<String>();
		buildingNearTypeArray = new ArrayList<String>();
		areaNearArray = new ArrayList<String>();
		
		for (int i = 0; i < addressArray.size(); i++) {

			distanceBetween = checkNearby(latitude, longitude,
					latitudeArray.get(i), longitudeArray.get(i));
			if (distanceBetween <= progressChanged) {
				
				if (areaString.equals("All") && categoryString.equals("All")) {
					
					addressNearArray.add(addressArray.get(i));
					buildingNearNameArray.add(buildingNameArray.get(i));
					latitudeNearArray.add(latitudeArray.get(i));
					latitudeNearStringArray.add(String.valueOf(latitudeArray
							.get(i)));
					longitudeNearArray.add(longitudeArray.get(i));
					longitudeNearStringArray.add(String.valueOf(longitudeArray
							.get(i)));
					buildingNearTypeArray.add(buildingTypeArray.get(i));
					areaNearArray.add(areaArray.get(i));

					buildingCount++;
				} else if (areaArray.get(i).equals(areaString)
						&& (buildingTypeArray.get(i).contains(categoryString) || categoryString
								.equals("All"))) {
					
					addressNearArray.add(addressArray.get(i));
					buildingNearNameArray.add(buildingNameArray.get(i));
					latitudeNearArray.add(latitudeArray.get(i));
					latitudeNearStringArray.add(String.valueOf(latitudeArray
							.get(i)));
					longitudeNearArray.add(longitudeArray.get(i));
					longitudeNearStringArray.add(String.valueOf(longitudeArray
							.get(i)));
					buildingNearTypeArray.add(buildingTypeArray.get(i));
					areaNearArray.add(areaArray.get(i));

					buildingCount++;

				} else if (areaString.equals("All")
						&& buildingTypeArray.get(i).contains(categoryString)) {
					
					addressNearArray.add(addressArray.get(i));
					buildingNearNameArray.add(buildingNameArray.get(i));
					latitudeNearArray.add(latitudeArray.get(i));
					latitudeNearStringArray.add(String.valueOf(latitudeArray
							.get(i)));
					longitudeNearArray.add(longitudeArray.get(i));
					longitudeNearStringArray.add(String.valueOf(longitudeArray
							.get(i)));
					buildingNearTypeArray.add(buildingTypeArray.get(i));
					areaNearArray.add(areaArray.get(i));

					buildingCount++;
				}
			}
		}
		
		try{
			buildingsFoundLabel.setText("Buildings Found: " + buildingCount);

			initilizeMap();
		}catch(NullPointerException e){
			e.printStackTrace();
		}

		/*
		 * if(buildingCount <= 0){ listButton.setVisibility(View.GONE);
		 * mapButton.setVisibility(View.GONE); }else{
		 * listButton.setVisibility(View.VISIBLE);
		 * mapButton.setVisibility(View.VISIBLE); }
		 */
	}

	public double checkNearby(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		// int meterConversion = 1000;

		return dist;
	}

	public void getCoordinates() {
		/*
		 * buildingName = getIntent().getExtras().getString("Building Name");
		 * address = getIntent().getExtras().getString("Building Address");
		 * latitude = getIntent().getExtras().getDouble("Latitude"); longitude =
		 * getIntent().getExtras().getDouble("Longitude");
		 */

		// check if GPS enabled
		GPSTracker gpsTracker = new GPSTracker(this);

		if (gpsTracker.canGetLocation()) {
			latitude = gpsTracker.latitude;

			longitude = gpsTracker.longitude;

			// String country = gpsTracker.getCountryName(this);

			// String city = gpsTracker.getLocality(this);

			// String postalCode = gpsTracker.getPostalCode(this);

			// String addressLine = gpsTracker.getAddressLine(this);
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gpsTracker.showSettingsAlert();
		}
	}

	// Function to load map. If map is not created it will create it for you
	private void initilizeMap() {
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

		googleMap.clear();
		
		System.out.println("Nearby Addresses: " + addressNearArray);
		
		if (addressNearArray.size() >= 1) {
			for (int i = 0; i < addressNearArray.size(); i++) {

				MarkerOptions marker = new MarkerOptions().position(new LatLng(
						latitudeNearArray.get(i), longitudeNearArray.get(i)));
				String[] splitAddressArray = addressNearArray.get(i).split(",");
				String splitAddressString = splitAddressArray[0];
				if (buildingNearNameArray.get(i).length() > 1) {
					marker.title(buildingNearNameArray.get(i)).snippet(
							splitAddressString);
				} else {
					marker.title(splitAddressString);
				}

				switch (areaNearArray.get(i)) {
				case "Arbutus Ridge":
					buildingArea = "arbutus_ridge";
					break;
				case "Downtown":
					buildingArea = "downtown";
					break;
				case "Downtown Eastside":
					buildingArea = "downtown_eastside";
					break;
				case "Dunbar-Southlands":
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
				case "Strathcona":
					buildingArea = "strathcona";
					break;
				case "South Cambie":
					buildingArea = "south_cambie";
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
				default:
					buildingArea = "arbutus_ridge";
					break;
				}

				switch (buildingNearTypeArray.get(i)) {
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
				default:
					break;
				}

				markerUrl = buildingArea + "_" + buildingType + "_pin.png";
				//System.out.println("Marker URL: " + markerUrl);
				marker.icon(BitmapDescriptorFactory.fromAsset(markerUrl));

				// Rose color icon
				// marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

				// Adding Marker
				googleMap.addMarker(marker);
			}
		}

		int zoomToInt = (int) Math.floor(15 - progressChanged / 2);

		// Focusing Map to Location
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
				latitude, longitude));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomToInt);
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
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
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
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			// showErrorDialog(connectionResult.getErrorCode());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_nearby);

		distanceLabel = (TextView) findViewById(R.id.distanceAwayText);
		buildingsFoundLabel = (TextView) findViewById(R.id.resultsText);
		listButton = (Button) findViewById(R.id.listButton);
		mapButton = (Button) findViewById(R.id.mapButton);
		distanceBar = (SeekBar) findViewById(R.id.distanceBar);

		distanceLabel.setText("Distance Away: " + progressChanged);

		// listButton.setVisibility(View.GONE);
		// mapButton.setVisibility(View.GONE);

		SpinnerSetup();

		distanceBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress > 0) {
					progressChanged = progress;
					distanceLabel.setText("Distance Away: " + progressChanged);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(SearchNearbyActivity.this,
						"Distance Away: " + progressChanged + "KM",
						Toast.LENGTH_SHORT).show();
				getCoordinates();
				buildingListSetup();
			}
		});

		listButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Intent i = new Intent(SearchNearbyActivity.this,
				 * NearbyListActivity.class); i.putExtra("Building Name Array",
				 * buildingNearNameArray); i.putExtra("Building Address Array",
				 * addressNearArray); i.putExtra("Latitude Array",
				 * latitudeNearArray); i.putExtra("Longitude Array",
				 * longitudeNearArray); startActivity(i);
				 * 
				 */
			}
		});

		mapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchNearbyActivity.this,
						MapActivity.class);
				i.putExtra("Array Boolean", true);
				i.putExtra("Building Name Array", buildingNearNameArray);
				i.putExtra("Building Address Array", addressNearArray);
				i.putExtra("Latitude Array", latitudeNearStringArray);
				i.putExtra("Longitude Array", longitudeNearStringArray);
				i.putExtra("Area Array", areaNearArray);
				i.putExtra("Building Type Array", buildingNearTypeArray);
				startActivity(i);
			}
		});

		MapsInitializer.initialize(getApplicationContext());
		setArrayLists();
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
	protected void onResume() {
		super.onResume();
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
		// mLocationClient.disconnect();
		super.onStop();

	}

	public void setArrayLists() {
		addressArray = databaseFunctions.getAddressArray();
		buildingNameArray = databaseFunctions.getBuildingNameArray();
		latitudeArray = databaseFunctions.getLatitudeArray();
		longitudeArray = databaseFunctions.getLongitudeArray();
		buildingTypeArray = databaseFunctions.getBuildingTypeArray();
		areaArray = databaseFunctions.getArea();
		
		if(addressArray.size() > 0){
			getCoordinates();
			buildingListSetup();
			initilizeMap();
		}
	}

	private void SpinnerSetup() {

		Spinner areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
		Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

		ArrayAdapter<CharSequence> adapterArea = ArrayAdapter
				.createFromResource(this, R.array.area_array,
						android.R.layout.simple_spinner_item);
		adapterArea
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter
				.createFromResource(this, R.array.category_array,
						android.R.layout.simple_spinner_item);
		adapterCategory
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		areaSpinner.setAdapter(new NothingSelectedSpinnerAdapter(adapterArea,
				R.layout.area_spinner_row_nothing_selected,
				// R.layout.contact_spinner_nothing_selected_dropdown, //
				// Optional
				this));
		// createSpinner.setPrompt("Select a Neighbourhood");

		categorySpinner.setAdapter(new NothingSelectedSpinnerAdapter(
				adapterCategory,
				R.layout.category_spinner_row_nothing_selected,
				// R.layout.contact_spinner_nothing_selected_dropdown, //
				// Optional
				this));

		areaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Object item = arg0.getItemAtPosition(arg2);
				if (item != null) {
					areaString = item.toString();
					buildingListSetup();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Object item = arg0.getItemAtPosition(arg2);
				if (item != null) {
					categoryString = item.toString();
					buildingListSetup();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
