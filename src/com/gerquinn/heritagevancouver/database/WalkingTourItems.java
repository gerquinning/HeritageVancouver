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

	public String getAddress() {
		return address;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public int getYear() {
		return year;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	

}
