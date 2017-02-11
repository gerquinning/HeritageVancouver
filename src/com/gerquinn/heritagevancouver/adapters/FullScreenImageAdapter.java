package com.gerquinn.heritagevancouver.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.UploadPictureActivity;
import com.gerquinn.heritagevancouver.helpers.TouchImageView;

public class FullScreenImageAdapter extends PagerAdapter{
	private Activity activity;
	private ArrayList<String> imagePaths;
	private LayoutInflater inflater;
	private Bitmap bitmap;
	String fileUri = "";
	int finalPosition;
	
	public FullScreenImageAdapter(Activity activity, ArrayList<String> imagePaths){
		this.activity = activity;
		this.imagePaths = imagePaths;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		((ViewPager) container).removeView((RelativeLayout) object);
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	@Override
	public int getCount() {
		return this.imagePaths.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position){
		TouchImageView imgDisplay;
		Button btnBack, btnUpload;
		
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
				false);
		
		imgDisplay = (TouchImageView)viewLayout.findViewById(R.id.imgDisplay);
		btnBack = (Button)viewLayout.findViewById(R.id.btnBack);
		btnUpload = (Button)viewLayout.findViewById(R.id.btnUpload);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap = BitmapFactory.decodeFile(imagePaths.get(position), options);
		imgDisplay.setImageBitmap(bitmap);
		fileUri = imagePaths.get(position);
		
		//Close Button Click Event
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
				
			}
		});
		
		btnUpload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fileUri = imagePaths.get(position);
				if(fileUri != null){
					//Upload picture
					Intent i = new Intent(activity, UploadPictureActivity.class);
					i.putExtra("fileUri", fileUri);
					activity.startActivity(i);
				}else{
					Toast.makeText(activity.getApplicationContext(), "No file selected. Please select one again", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		((ViewPager) container).addView(viewLayout);
		return viewLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

}
