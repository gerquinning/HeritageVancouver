package com.gerquinn.heritagevancouver;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gerquinn.heritagevancouver.helpers.AppController;

public class BuildingDetailsActivity extends Activity {
	
	public String buildingName, address, description, shortImageUrl, imageUrl = " ";
	public double latitude,longitude = 0.0;
	public ArrayList<String> building_array =  new ArrayList<String>();
	public URL url;
	
	public File imgFile;
	public ScrollView Sc1;
	public TextView T1, T2, T3;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public Button but1;
	
	public void BuildingDetails(){
		buildingName = getIntent().getExtras().getString("buildingName");
		address = getIntent().getExtras().getString("address");
		latitude = getIntent().getExtras().getDouble("latitude");
		longitude = getIntent().getExtras().getDouble("longitude");
		description = getIntent().getExtras().getString("description");
		imageUrl = getIntent().getExtras().getString("imageUrl");
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_description);
		
		BuildingDetails();
		
		//TableLayout Table1 = (TableLayout) findViewById(R.id.descriptionTable);
		
		if(imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView image = (NetworkImageView) findViewById(R.id.image1);
		
		// Loading image with placeholder and error image
		imageLoader.get(imageUrl, ImageLoader.getImageListener(
				image, R.drawable.img_placeholder, R.drawable.img_placeholder));
		
		image.setImageUrl(imageUrl, imageLoader);
		
		
		T1 = (TextView) findViewById(R.id.description1);
		T2 = (TextView) findViewById(R.id.description2);
		T3 = (TextView) findViewById(R.id.description3);
		but1 = (Button) findViewById(R.id.button1);
		
		T1.setText(buildingName);
		T2.setText(address);
		T3.setText(description);
		
		but1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				Intent i = new Intent(BuildingDetailsActivity.this, MapActivity.class);
				i.putExtra("Array Boolean", false);
				i.putExtra("Building Name", buildingName);
				i.putExtra("Building Address", address);
				i.putExtra("Latitude", latitude);
				i.putExtra("Longitude", longitude);
				startActivity(i);
				
				finish();
			}
		});
		
	}
}
