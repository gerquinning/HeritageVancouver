package com.gerquinn.heritagevancouver;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

import com.gerquinn.heritagevancouver.adapters.RemoteGridViewAdapter;
import com.gerquinn.heritagevancouver.helpers.DirectoryReader;
import com.gerquinn.heritagevancouver.helpers.Utils;

public class RemoteImageGalleryActivity extends Activity{
	
	private Utils utils;
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private GridView gridView;
	private RemoteGridViewAdapter customGridAdapter;
	private int columnWidth;
	private static int index;
	//private int position;
	DirectoryReader dir;
	Activity activity;
	
	
	/**
	 * Getter and Setter methods for the 
	 * List of Images from the Server
	 */
	public ArrayList<String> getFilesArray() {
		return imagePaths;
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

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_gallery);
		
		gridView = (GridView)findViewById(R.id.gridView);
		utils = new Utils(this);
		activity = this;
		
		//Initializing GridView
		InitializeGridLayout();
		
		dir = new DirectoryReader();
		dir.execute();
		
		//Loading all Image Paths from SDCard
		//imagePaths = utils.getFilePaths();
		
	}
	
	@Override
	public void onPause(){  
	    index = gridView.getFirstVisiblePosition();
	    super.onPause();
	}
	
	@Override
	public void onResume(){

	    gridView.setSelection(index);
	    super.onResume();
	}

	public void setFilesArray(ArrayList<String> imagePaths) {
		
		this.imagePaths = imagePaths;
		
		for(int i = 0; i <= imagePaths.size() - 1; i++){
			System.out.println("Yo FILES: " + this.imagePaths.get(i));
		}
		System.out.println("columnWidth: " + columnWidth);
		customGridAdapter = new RemoteGridViewAdapter(activity, this.imagePaths, 328);
		gridView.setAdapter(customGridAdapter);
		
	}
	
}
