package com.gerquinn.heritagevancouver.database;

public class BuildingConstructors {
	
	//private variables
	int _id, _buildingYear;
	String _buildingName,_buildingType,_address,_description,_imageURL,_thumbURL;
	double _latitude,_longitude;
	
	//Empty constructor
	public BuildingConstructors(){
		
	}
	
	//Constructor
	public BuildingConstructors(int id, String buildingName, int buildingYear, String buildingType, String address, double latitude, double longitude, String description, String imageURL, String thumbURL){
		this._id = id;
		this._buildingName = buildingName;
		this._buildingYear = buildingYear;
		this._buildingType = buildingType;
		this._address = address;
		this._latitude = latitude;
		this._longitude = longitude;
		this._description = description;
		this._imageURL = imageURL;
		this._thumbURL = thumbURL;
	}
	
	public BuildingConstructors(String buildingName, int buildingYear, String buildingType, String address, double latitude, double longitude, String description, String imageURL, String thumbURL){
		this._buildingName = buildingName;
		this._buildingYear = buildingYear;
		this._buildingType = buildingType;
		this._address = address;
		this._latitude = latitude;
		this._longitude = longitude;
		this._description = description;
		this._imageURL = imageURL;
		this._thumbURL = thumbURL;
	}
	
	public String getAddress(){
		return this._address;
	}
	
	public String getBuildingName(){
		return this._buildingName;
	}
	
	public int getBuildingYear(){
		return this._buildingYear;
	}
	
	public String getBuildingType(){
		return this._buildingType;
	}
	
	public String getDescription(){
		return this._description;
	}

	public int getID(){
		return this._id;
	}

	public String getImageURL(){
		return this._imageURL;
	}

	public double getLatitude(){
		return this._latitude;
	}
	
	public double getLongitude(){
		return this._longitude;
	}
	
	public String getThumbURL(){
		return this._thumbURL;
	}
	
	public void setAddress(String address) {
		this._address = address;
	}
	
	public void setBuildingName(String buildingName) {
		this._buildingName = buildingName;
		
	}
	
	public void setBuildingYear(int buildingYear) {
		this._buildingYear = buildingYear;
		
	}
	public void setBuildingType(String buildingType) {
		this._buildingType = buildingType;
		
	}
	
	public void setDescription(String description) {
		this._description = description;
		
	}
	
	public void setID(int id)
	{
		this._id = id;
	}
	
	public void setImageURL(String imageURL) {
		this._imageURL = imageURL;
		
	}
	
	public void setLatitude(double latitude) {
		this._latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this._longitude = longitude;
	}
	
	public void setThumbURL(String thumbURL) {
		this._thumbURL = thumbURL;
		
	}

}
