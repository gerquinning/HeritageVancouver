package com.gerquinn.heritagevancouver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.helpers.DirectoryReader;
import com.gerquinn.heritagevancouver.helpers.JSONWriter;
import com.gerquinn.heritagevancouver.helpers.PictureGeoTagging;
import com.gerquinn.heritagevancouver.location.GPSTracker;
import com.google.android.gms.location.LocationClient;

public class CapturePhotoActivity extends Activity{
	
	//Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	//private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	
	//Directory name to store captured images
	private static final String IMAGE_DIRECTORY_NAME = "Heritage Vancouver";
	
	/**
	 * Setup the geotagging on the image to tag to a building later.
	 */
	public static void geoTag(String filename, double latitude, double longitude){
		ExifInterface exif;
		
		try{
			exif = new ExifInterface(filename);
			
			int num1Lat = (int)Math.floor(latitude);
			int num2Lat = (int)Math.floor((latitude - num1Lat) * 60);
			double num3Lat = (latitude - (num1Lat+((double)num2Lat/60))) * 3600000;
			
			int num1Lon = (int)Math.floor(longitude);
			int num2Lon = (int)Math.floor((longitude - num1Lon) * 60);
			double num3Lon = (longitude - (num1Lon+((double)num2Lon/60))) * 3600000;
			
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat+"/1,"+num2Lat+"/1,"+num3Lat+"/1000");
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon+"/1,"+num2Lon+"/1,"+num3Lon+"/1000");	
			
			exif.saveAttributes();
			System.out.println("GEO TAGGING!");
	    } catch (IOException e) {
	    	Log.e("PictureActivity", e.getLocalizedMessage());
	    }
	}
	
	/**
     * returning image / video
     */
    private static File getOutputMediaFile(int type, double latitude, double longitude){
    	//External SDcard location
    	File mediaStorageDir = new File(
    			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
    			IMAGE_DIRECTORY_NAME);
    	
    	//Create the storage location if it does not exist
    	if(!mediaStorageDir.exists()){
    		if(!mediaStorageDir.mkdirs()){
    			System.out.println("Failure");
    			Log.d(IMAGE_DIRECTORY_NAME, "Failed to create " + IMAGE_DIRECTORY_NAME + " directory");
    			return null;
    		}else{
    			System.out.println("Success");
    			Log.d(IMAGE_DIRECTORY_NAME, "created " + IMAGE_DIRECTORY_NAME + " directory");
    		}
    	}
    	
    	//Create a media file name 
    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
    			Locale.getDefault()).format(new Date());
    	//String address = CapturePhotoActivity.renamePhoto();
    	File mediaFile;
    	System.out.println("File name: " + timeStamp);
    	if(type == MEDIA_TYPE_IMAGE){
    		mediaFile = new File(mediaStorageDir.getPath() + File.separator
    				+ "IMG_" + timeStamp + ".jpg");
    		System.out.println("File name: " + mediaFile);
    	}else{
    		return null;
    	}
    	
    	return mediaFile;
    	
    }
	//File Url to store image
	private Uri fileUri;
	
	private ImageView imgPreview;
	private Button btnCapturePicture, btnUploadPicture, btnViewGallery;
	double latitude = 0;
	double longitude = 0;
	
	Location mCurrentLocation;
	LocationClient mLocationClient;
	PictureGeoTagging picGeoTag = new PictureGeoTagging();
	DirectoryReader dir;
	String remoteDirectory = "http://quinntechssential.com/heritage_vancouver/user_img_upload";
	String [] listOfFiles;
	
	JSONWriter jsonWriter;
	
	Activity activity;

	/**
     * Capturing Camera Image will launch camera app request image capture
     */
	private void captureImage(){
		
		//Setup location
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, latitude, longitude);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		
		//Start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	/**
     * Get the coordinates for the picture
     * */
	public void getCoordinates(){
		
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
	
	/**
	 * Getter and Setter methods for the 
	 * List of Images from the Server
	 */
	public String[] getListOfFiles() {
		return listOfFiles;
	}
	
	/**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type, double latitude, double longitude){
    	System.out.println("Uri: " + type);
    	return Uri.fromFile(getOutputMediaFile(type, latitude, longitude));
    }
	
	/**
     * Checking device has camera hardware or not
     * */
	private boolean isDeviceSupportCamera(){
		if(getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)){
			//This device has a camera
			return true;
		}else{
			//No camera on this device
			return false;
		}
	}
	
	/**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	//If the result is capturing Image
    	if(requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
    		if(resultCode == RESULT_OK){
    			//Successfully captured the Image
    			//Display it in the Image View
    			previewCapturedImage();
    			picGeoTag.writeGeoTagImage(fileUri.getPath(),latitude,longitude);
    			//geoTag(fileUri.getPath(),latitude,longitude);
    		}else if(resultCode == RESULT_CANCELED){
    			//User cancelled the Image capture
    			Toast.makeText(getApplicationContext(), "User cancelled Image capture", Toast.LENGTH_SHORT).show();
    		}else{
    			//Failed to capture Image
    			Toast.makeText(getApplicationContext(), "Failed to capture image", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		activity = this;
		
		/*listOfFiles = dir.getListOfFiles();
		
		for(int i = 0; i <= listOfFiles.length - 1; i++){
			System.out.println("FILES" + listOfFiles[i]);
		}*/
		
		/*String serverFile = remoteDirectory + "/listfiles.php";
		String listOfFiles = dir.DownloadText(serverFile);
		String [] linesOfFiles = listOfFiles.split("<br>");*/
		
		getCoordinates();
		
		btnCapturePicture = (Button)findViewById(R.id.btnCapturePicture);
		btnUploadPicture = (Button)findViewById(R.id.btnUploadPicture);
		btnViewGallery = (Button)findViewById(R.id.btnViewGallery);
		imgPreview = (ImageView)findViewById(R.id.imgPreview);
		
		/**
         * Capture image button click event
         */
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v){
				//Capture picture
				captureImage();
			}
			
		});
		
		btnUploadPicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v){
				
				/*if(fileUri != null){
					String fileStringUri = fileUri.toString();*/
				
					//Upload picture
					//Intent i = new Intent(CapturePhotoActivity.this, UploadPictureActivity.class);
					Intent i = new Intent(CapturePhotoActivity.this, ImageGalleryActivity.class);
					//i.putExtra("Photo file name", fileStringUri);
					startActivity(i);
				/*}else{
					Toast.makeText(getApplicationContext(), "No file selected. Please select one again", Toast.LENGTH_SHORT).show();
				}*/
			}
			
		});
		
		btnViewGallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v){
				//View Gallery
				//jsonWriter = new JSONWriter(activity, getApplicationContext());
				//jsonWriter.writeToJSON("IMG_BLALALA.jpg", "49.99999", "-123.876543");
				Intent i = new Intent(CapturePhotoActivity.this, RemoteImageGalleryActivity.class);
				//i.putExtra("Photo file name", fileStringUri);
				startActivity(i);
			}
			
		});
		
		//Checking camera availability
		if(!isDeviceSupportCamera()){
			Toast.makeText(getApplicationContext(), "Sorry,  your device doesn't suport camera", Toast.LENGTH_LONG).show();
			//Will close the appif the device doesn't have a camera
			//finish();
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		//Get the file uri
		fileUri = savedInstanceState.getParcelable("file_uri");
	}
    
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		
		//Save file url in bundle as it will be null on screen orientation changes
		outState.putParcelable("file_uri", fileUri);
	}
    
    /**
     * ------------ Helper Methods ----------------------
     * */
 
    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage(){
    	try{
    		imgPreview.setVisibility(View.VISIBLE);
    		
    		//Bitmap Factory
    		BitmapFactory.Options options = new BitmapFactory.Options();
    		
    		//Downsizing image as it throws OutOfMemory Exception for large images
    		options.inSampleSize = 4;
    		
    		final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
    		
    		imgPreview.setImageBitmap(bitmap);
    	}catch(NullPointerException e){
    		e.printStackTrace();
    	}catch(OutOfMemoryError e){
    		e.printStackTrace();
    	}
    }
    
    public void setListOfFiles(String[] listOfFiles) {
		this.listOfFiles = listOfFiles;
		
		for(int i = 0; i <= listOfFiles.length - 1; i++){
			System.out.println("FILES: " + listOfFiles[i]);
		}
	}
    
}
