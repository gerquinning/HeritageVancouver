package com.gerquinn.heritagevancouver.database;

import java.util.ArrayList;

public class DatabaseFunctions {
	
	//private variables
	String _id, _address, _buildingName, _buildingType,  _area;
	double _latitude, _longitude;
	
	//List populated from Database
    public static ArrayList<String> idArray = new ArrayList<String>();
	public static ArrayList<String> buildingNameArray  = new ArrayList<String>();
	public static ArrayList<String> buildingTypeArray = new ArrayList<String>();
	public static ArrayList<String> addressArray = new ArrayList<String>();
	public static ArrayList<Double> latitudeArray = new ArrayList<Double>();
	public static ArrayList<Double> longitudeArray = new ArrayList<Double>();
	public static ArrayList<String> areaArray = new ArrayList<String>();
	
	//Empty Constructor
	public DatabaseFunctions(){
		
	}
	
	public DatabaseFunctions(String id, String buildingName, String buildingType, String address, double latitude, double longitude, String area){
		this._id = id;
		this._buildingName = buildingName;
		this._buildingType = buildingType;
		this._address = address;
		this._latitude = latitude;
		this._longitude = longitude;
		this._area = area;
	}
	
	public DatabaseFunctions(String address, String buildingName, double latitude, double longitude, String buildingType, String area){
		this._address = address;
		this._buildingName = buildingName;
		this._latitude = latitude;
		this._longitude = longitude;
		this._buildingType = buildingType;
		this._area = area;
	}
	
	public static void setArrays(String address, String buildingName, double latitude, double longitude, String buildingType, String area){
		// Adding each value to the ArrayLists
		addressArray.add(address);
		buildingNameArray.add(buildingName);
		latitudeArray.add(latitude);
		longitudeArray.add(longitude);
		buildingTypeArray.add(buildingType);
		areaArray.add(area);
	}

	public String get_address() {
		return _address;
	}

	public String get_buildingName() {
		return _buildingName;
	}

	public String get_buildingType() {
		return _buildingType;
	}

	public String get_area() {
		return _area;
	}

	public String get_id() {
		return _id;
	}

	public double get_latitude() {
		return _latitude;
	}

	public double get_longitude() {
		return _longitude;
	}
	
	public ArrayList<String> getAddressArray() {
		return addressArray;
	}

	public ArrayList<String> getBuildingNameArray() {
		return buildingNameArray;
	}

	public ArrayList<Double> getLatitudeArray() {
		return latitudeArray;
	}

	public ArrayList<Double> getLongitudeArray() {
		return longitudeArray;
	}

	public ArrayList<String> getBuildingTypeArray() {
		return buildingTypeArray;
	}

	public ArrayList<String> getArea() {
		return areaArray;
	}

	public void set_address(String _address) {
		this._address = _address;
	}

	public void set_buildingName(String _buildingName) {
		this._buildingName = _buildingName;
	}

	public void set_buildingType(String _buildingType) {
		this._buildingType = _buildingType;
	}

	public void set_area(String _area) {
		this._area = _area;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void set_latitude(double _latitude) {
		this._latitude = _latitude;
	}

	public void set_longitude(double _longitude) {
		this._longitude = _longitude;
	}
	
}
