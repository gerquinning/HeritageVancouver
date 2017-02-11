package com.gerquinn.heritagevancouver.helpers;

import android.graphics.Bitmap;

public class ImageItem {
	
	private Bitmap image;
	private String title;
	
	public ImageItem(Bitmap image, String title){
		super();
		this.image = image;
		this.title = title;
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setImage(Bitmap image){
		this.image = image;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
}
