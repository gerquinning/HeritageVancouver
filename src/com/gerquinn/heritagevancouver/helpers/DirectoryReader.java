package com.gerquinn.heritagevancouver.helpers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.gerquinn.heritagevancouver.CapturePhotoActivity;
import com.gerquinn.heritagevancouver.RemoteImageGalleryActivity;
import com.gerquinn.heritagevancouver.adapters.RemoteGridViewAdapter;

public class DirectoryReader extends AsyncTask<String, Void, String>{
	
	private static final String TAG = "";
	String imagePath = "http://quinntechssential.com/heritage_vancouver/user_img_upload/";
	String [] listOfFiles;
	ArrayList<String> filesArray = new ArrayList<String>();
	CapturePhotoActivity capturePhotoActivity = new CapturePhotoActivity();
	RemoteGridViewAdapter remoteGridViewAdapter = new RemoteGridViewAdapter();
	RemoteImageGalleryActivity remActivity = new RemoteImageGalleryActivity();
	
	@Override
	protected String doInBackground(String... params){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(imagePath + "listfiles.php");
		//HttpHost targetHost = new HttpHost("http://quinntechssential.com/heritage_vancouver/user_img_upload");
		//HttpGet targetGet = new HttpGet("/listfiles.php");
		try{
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String listString = EntityUtils.toString(entity);
			listOfFiles = listString.split("<br>");
			capturePhotoActivity.setListOfFiles(listOfFiles);
			for(int i = 0; i <= listOfFiles.length - 1; i++){
				filesArray.add((imagePath + listOfFiles[i]));
				Log.d(TAG, "LIST OF FILES: " + listOfFiles[i]);
			}
			
			remActivity.setFilesArray(filesArray);
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(String result){
		super.onPostExecute(result);
	}
	
	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
}