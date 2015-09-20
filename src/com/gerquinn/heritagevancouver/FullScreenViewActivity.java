package com.gerquinn.heritagevancouver;

import com.gerquinn.heritagevancouver.adapters.FullScreenImageAdapter;
import com.gerquinn.heritagevancouver.helpers.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class FullScreenViewActivity extends Activity{
	private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	//private File fileUri;
	private Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		utils = new Utils(getApplicationContext());
		
		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, utils.getFilePaths());
		viewPager.setAdapter(adapter);
		
		//Displayong selected Image first
		viewPager.setCurrentItem(position);
	}
	
	/**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		//fileUri = adapter.getFileUri();
		bitmap = adapter.getBitmap();
		//Save file url in bundle as it will be null on screen orientation changes
		outState.putParcelable("bitmap", bitmap);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		//Get the file uri
		bitmap = savedInstanceState.getParcelable("bitmap");
	}

}
