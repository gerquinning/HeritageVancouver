package com.gerquinn.heritagevancouver.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gerquinn.heritagevancouver.MakePhotoActivity;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback{
	
	private final Context context;
	
	public PhotoHandler(Context context){
		this.context = context;
	}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera){
		
		File pictureFileDir = getDir();
		
		if(!pictureFileDir.exists() && !pictureFileDir.mkdirs()){
			
			Log.d(MakePhotoActivity.DEBUG_TAG, "Can't create directory to save image.");
			Toast.makeText(context, "Can't create directory to save image.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		String photoFile = "Picture_" + date + ".jpg";
		
		String filename = pictureFileDir.getPath() + File.separator + photoFile;
		
		File pictureFile = new File(filename);
		
		try{
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			Toast.makeText(context, "New Image saved:" + photoFile, Toast.LENGTH_LONG).show();
		}catch(Exception e){
			Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename + "not saved: " + e.getMessage());
			Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private File getDir(){
		File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "CameraAPIDemo");
	}

}