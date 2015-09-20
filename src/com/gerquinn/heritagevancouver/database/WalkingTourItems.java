package com.gerquinn.heritagevancouver.database;

public class WalkingTourItems {
	
	private String buildingName, buildingType, address, description, imageUrl, thumbUrl;
	private int year;
	private double latitude, longitude;
	
	public WalkingTourItems() {
		
	}
	
	public WalkingTourItems(String buildingName, int year, String buildingType, String address, double latitude, double longitude, String description, String imageUrl, String thumbUrl){
		this.buildingName = buildingName;
		this.year = year;
		this.buildingType = buildingType;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.imageUrl = imageUrl;
		this.thumbUrl = thumbUrl;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
