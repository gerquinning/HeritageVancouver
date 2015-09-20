package com.gerquinn.heritagevancouver;

import java.util.ArrayList;

import com.gerquinn.heritagevancouver.adapters.GridViewAdapter;
import com.gerquinn.heritagevancouver.helpers.Utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

public class ImageGalleryActivity extends Activity{
	
	private Utils utils;
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private GridView gridView;
	private GridViewAdapter customGridAdapter;
	private int columnWidth;
	private static int index;
	//private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_gallery);
		
		gridView = (GridView)findViewById(R.id.gridView);
		utils = new Utils(this);
		
		//Initializing GridView
		InitializeGridLayout();
		
		//Loading all Image Paths from SDCard
		imagePaths = utils.getFilePaths();
		
		customGridAdapter = new GridViewAdapter(this, imagePaths, columnWidth);
		gridView.setAdapter(customGridAdapter);
		
	}
	
	@Override
	public void onResume(){

	    gridView.setSelection(index);
	    super.onResume();
	}

	@Override
	public void onPause(){  
	    index = gridView.getFirstVisiblePosition();
	    super.onPause();
	}
	
	/**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
	/*@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		
		//Save file url in bundle as it will be null on screen orientation changes
		outState.putParcelable("file_uri", fileUri);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		//Get the file uri
		fileUri = savedInstanceState.getParcelable("file_uri");
	}*/
	
	private void InitializeGridLayout(){
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Utils.GRID_PADDING, r.getDisplayMetrics());
		
		columnWidth = (int)((utils.getScreenWidth() - ((Utils.NUM_OF_COLUMNS + 1) * padding)) / Utils.NUM_OF_COLUMNS);
		System.out.println("columnWidth: " + columnWidth);
		gridView.setNumColumns(Utils.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
	
}
