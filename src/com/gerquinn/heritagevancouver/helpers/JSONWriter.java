package com.gerquinn.heritagevancouver.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONWriter{
	
	class DataObject{
		public String filename;
		public String lat;
		public String lon;
	}
	private String jsonUrl = "http://quinntechssential.com/heritage_vancouver/user_img_upload/fileinfo.json";
	private String jsonResponse;
	
	private static String TAG = JSONWriter.class.getSimpleName();
	private Activity activity;
	 
	 private Context context;
	
	public JSONWriter(Activity activity, Context context){
	    this.activity = activity;
		this.context = context;
	}
	
	/**
     * Method to make json array request where response starts with [
     * */
	public void makeJsonArrayRequest(){
		System.out.println("JSON Array Method");
		JsonArrayRequest req = new JsonArrayRequest(jsonUrl,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString());
				System.out.println("Response 1: " + response.toString());
				try{
					//Parsing JSON Array Response
					//Loop through each JSON Object
					jsonResponse = "";
					for(int i = 0; i < response.length(); i++){
						JSONObject fileNameObject = (JSONObject) response.get(i);
						
						String name = fileNameObject.getString("name");
						String lat = fileNameObject.getString("lat");
						String lon = fileNameObject.getString("lon");
						
						jsonResponse += "Name: " + name + "\n\n";
						jsonResponse += "Lat: " + lat + "\n\n";
						jsonResponse += "Lon: " + lon + "\n\n";
					}
					System.out.println("Response end: " + jsonResponse);
					Log.d(TAG, jsonResponse);
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
				}, new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error){
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						
					}
				});
		
		//Adding Request to Request queue
		AppController.getInstance().addToRequestQueue(req);
	}
	
	/**
     * Method to make json object request where json response starts wtih {
     * */
	public void makeJsonObjectRequest(){
		System.out.println("JSON Object Method");
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				jsonUrl,null, new Response.Listener<JSONObject>() {
			
			@Override
			public void onResponse(JSONObject response){
				Log.d(TAG, response.toString());
				System.out.println("Response 1: " + response.toString());
				try{
					//Parsing JSON Object Response
					//Response will be a JSON Object
					String name = response.getString("name");
					String lat = response.getString("lat");
					String lon = response.getString("long");
					
					jsonResponse = "";
					jsonResponse += "Name: " + name + "\n\n";
					jsonResponse += "Lat: " + lat + "\n\n";
					jsonResponse += "Long: " + lon + "\n\n";
					
					System.out.println("Response end: " + response.toString());
					Log.d(TAG, jsonResponse);
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			} 
				}, new Response.ErrorListener() {
					
					@Override
					public void onErrorResponse(VolleyError error){
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						
					}
				});
		
		//Adding Request to Request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}
	
	public void writeToJSON(String filename, String lat, String lon){
		
		DataObject dataObject = new DataObject();
		dataObject.filename = filename;
		dataObject.lat = lat;
		dataObject.lon = lon;
		
		//Convert the Object to a JSON String
		GsonBuilder builder = new GsonBuilder();
		
		String json = new Gson().toJson(dataObject);
		System.out.println(json);
		
		HttpRequestWithEntity request = new HttpRequestWithEntity(activity, context);
		request.execute(json);
	}

	
	
}
