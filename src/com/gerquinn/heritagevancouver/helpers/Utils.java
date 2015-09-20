package com.gerquinn.heritagevancouver.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class Utils {
	
	private Context context;
	
	// Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;
 
    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp
 
    // SD card image directory
    public static final String PHOTO_ALBUM = "Heritage Vancouver";
 
    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
	
	public Utils(Context context){
		this.context = context;
	}
	
	//Reading file Paths from SDCard
	public ArrayList<String> getFilePaths(){
		ArrayList<String> filePaths = new ArrayList<String>();
		
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),PHOTO_ALBUM);
		
		//Check for Directory
		if(directory.isDirectory()){
			//Getting List of File Paths
			File[] listFiles = directory.listFiles();
			
			//Check for count
			if(listFiles.length > 0){
				//Loop through all files
				for(int i = 0; i < listFiles.length; i++){
					//Get File Path
					String filePath = listFiles[i].getAbsolutePath();
					
					//Check for Supported file Extension
					if(IsSupportedFile(filePath)){
						//Add Image Path to Array list
						filePaths.add(filePath);
					}
				}
			}else{
				//Image Directory is empty
				Toast.makeText(context, PHOTO_ALBUM + " is empty. Please load some images", Toast.LENGTH_LONG).show();
			}
		}else{
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle("Error!");
			alert.setMessage(PHOTO_ALBUM + " directory path is not valid! Please set the image directory path");
			alert.setPositiveButton("OK", null);
			alert.show();
		}
		
		return filePaths;
	}
	
	//Check supported file Extensions
	private boolean IsSupportedFile(String filePath){
		String ext = filePath.substring((filePath.lastIndexOf(".") + 1), filePath.length());
		
		if(FILE_EXTN.contains(ext.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;
	}
	
	//Getting Screen Width
	public int getScreenWidth(){
		int columnWidth;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		final Point point = new Point();
		try{
			display.getSize(point);
		}catch(java.lang.NoSuchMethodError ignore){ //Older Devices
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
	}

}
