package com.gerquinn.heritagevancouver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WalkingTourActivity extends Activity {
	
	TextView titleText;
	ImageView westHastingsImage, japantownImage, chinatownImage, carrallStreetImage, strathconaImage, molehillImage;
	
	private void CarrallStreetLoad(){
		Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","carrall_street_tour");
		startActivity(i);
	}
	
	private void ChinatownLoad(){
		Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","chinatown_tour");
		startActivity(i);
	}
	
	public void ImageViewSetup(){
		
		//Text setup
		titleText = (TextView)findViewById(R.id.WalkingTourTitle);
		titleText.setText("Walking Tours");
		
		//Image setup
		westHastingsImage = (ImageView)findViewById(R.id.westHastingsImage);
		japantownImage = (ImageView)findViewById(R.id.japantownImage);
		chinatownImage = (ImageView)findViewById(R.id.chinatownImage);
		carrallStreetImage = (ImageView)findViewById(R.id.carrallStreetImage);
		strathconaImage = (ImageView)findViewById(R.id.strathconaImage);
		molehillImage = (ImageView)findViewById(R.id.molehillImage);	
		
		westHastingsImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				WestHastingsLoad();
			}
		});
		
		japantownImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				JapantownLoad();
			}
		});
		
		chinatownImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ChinatownLoad();
			}
		});
		
		carrallStreetImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CarrallStreetLoad();
			}
		});
		
		strathconaImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO
			}
		});
		
		molehillImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO
			}
		});
		
	}
	
	private void JapantownLoad(){
		Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","japantown");
		startActivity(i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.activity_walkingtours);
		
		ImageViewSetup();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}
	
	/**
	 * Launching new Walking Tours
	 * */
	private void WestHastingsLoad(){
		Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
		i.putExtra("databaseName","heritage_14");
		i.putExtra("tableName","west_hastings_tour");
		startActivity(i);
	}
}


