package com.gerquinn.heritagevancouver.helpers;

import java.net.URL;

public class BuildingDescription {

	//private variables
	int _id;
	String _buildingName;
	String _address;
	double _latitude;
	double _longitude;
	String _description;
	String _image_url;
	URL url;
	
	public BuildingDescription() {
	}
	
	public BuildingDescription(int id, String buildingName, String address, double latitude, double longitude, String description, String image_url) {
		
		this._id = id;
		this._buildingName = buildingName;
		this._address = address;
		this._latitude = latitude;
		this._longitude = longitude;
		this._description = description;
		this._image_url = image_url;
	}
	
	public BuildingDescription(String buildingName, String address, double latitude, double longitude, String description, String image_url) {
		
		this._buildingName = buildingName;
		this._address = address;
		this._latitude = latitude;
		this._longitude = longitude;
		this._description = description;
		this._image_url = image_url;
	}
	
	public String getAddress(){
		return this._address;
	}
	
	public String getBuildingName(){
		return this._buildingName;
	}

	public String getDescription(){
		return this._description;
	}

	public int getID(){
		return this._id;
	}

	public double getLatitude(){
		return this._latitude;
	}

	public double getLongitude(){
		return this._longitude;
	}
	
	public void setAddress(String address) {
		this._address = address;
	}
	
	public void setBuildingName(String buildingName) {
		this._buildingName = buildingName;
		
	}
	
	public void setDescription(String description) {
		this._description = description;
		
	}
	
	public void setID(int id)
	{
		this._id = id;
	}
	
	public void setLatitude(double latitude) {
		this._latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this._longitude = longitude;
	}

}
