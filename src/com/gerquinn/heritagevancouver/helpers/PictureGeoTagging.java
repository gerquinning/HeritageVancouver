/**************************************
 ********* Heritage Vancouver *********
 ********* Author: Ger Quinn **********
 *************** 2014 *****************
 **** http://quinntechssential.com ****
 **************************************/

package com.gerquinn.heritagevancouver.helpers;

import java.io.IOException;

import android.location.Location;
import android.media.ExifInterface;
import android.util.Log;

public class PictureGeoTagging {
	
	//protected String[] fileNameArray, fileDateArray;
	public double imgLatitude;
	public double imgLongitude;
	
	public PictureGeoTagging() {
	}
	
	public double getImgLatitude() {
		return imgLatitude;
	}

	public void setImgLatitude(double imgLatitude) {
		this.imgLatitude = imgLatitude;
	}

	public double getImgLongitude() {
		return imgLongitude;
	}

	public void setImgLongitude(double imgLongitude) {
		this.imgLongitude = imgLongitude;
	}

	/**
	 * Get the GeoTagging info from the image
	 */
	public Location readGeoTagImage(String imagePath)
	{
		Location loc = new Location("");
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			String attrLATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			String attrLONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			
			imgLatitude = convertToDegree(attrLATITUDE);
			imgLongitude  = convertToDegree(attrLONGITUDE);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return loc;
	}
	
	/**
	 * Setup the GeoTagging on the image to tag to a building later.
	 */
	public void writeGeoTagImage(String filename, double latitude, double longitude){
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
			System.out.println("Writing Geo Tag: " + latitude + " : " + longitude);
	    } catch (IOException e) {
	    	Log.e("PictureActivity", e.getLocalizedMessage());
	    }
	}
	
	/**
	 * Change the Lat and Long values from DMS to Degrees
	 */
	public double convertToDegree(String stringDMS){
		double result = 0.0;
		String[] DMS = stringDMS.split(",",3);
		
		String[] stringD = DMS[0].split("/", 2);
		double D0 = Double.valueOf(stringD[0]);
		double D1 = Double.valueOf(stringD[1]);
		double doubleD = D0/D1;
		
		String[] stringM = DMS[1].split("/", 2);
		double M0 = Double.valueOf(stringM[0]);
		double M1 = Double.valueOf(stringM[1]);
		double doubleM = M0/M1;
		
		String[] stringS = DMS[2].split("/", 2);
		double S0 = Double.valueOf(stringS[0]);
		double S1 = Double.valueOf(stringS[1]);
		double doubleS = S0/S1;
		
		result = (doubleD + (doubleM/60) + (doubleS/3600));
		
		return result;
	}

}
