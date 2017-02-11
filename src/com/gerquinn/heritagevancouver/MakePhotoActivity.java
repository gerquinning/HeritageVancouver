package com.gerquinn.heritagevancouver;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.helpers.PhotoHandler;

public class MakePhotoActivity extends Activity {
	
	public final static String DEBUG_TAG = "MakePhotoActivity";
	private Camera camera;
	private int cameraId = 0;
	
	private int findFrontFacingCamera(){
		int cameraId = -1;
		
		//Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for(int i = 0; i < numberOfCameras; i++){
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if(info.facing == CameraInfo.CAMERA_FACING_FRONT){
				Log.d(DEBUG_TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	
	public void onClick(View view){
		if (camera != null) {
		    Log.d("camera state","camera is NOT null");  
		  }else{
		    Log.d("camera state","camera is null");
		    camera = android.hardware.Camera.open(cameraId);
		  }
		camera.startPreview();
		camera.takePicture(null, null,
	            new PhotoHandler(getApplicationContext()));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		//Do we have a camera?
		if(!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			Toast.makeText(this, "No camera found on this device", Toast.LENGTH_SHORT).show();
		}else{
			cameraId = findFrontFacingCamera();
			if(cameraId < 0){
				Toast.makeText(this, "No front facing camera found.", Toast.LENGTH_SHORT).show();
			}else{
				camera = Camera.open(cameraId);
			}
		}
	}
	
	@Override
	protected void onPause(){
		if(camera != null){
			camera.release();
			camera = null;
		}
		super.onPause();
	}

}
