package com.gerquinn.heritagevancouver.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.gerquinn.heritagevancouver.FullScreenViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<String> filePaths = new ArrayList<String>();
	private int imageWidth;
	
	public GridViewAdapter(Activity activity, ArrayList<String> filePaths, int imageWidth){
		this.activity = activity;
		this.filePaths = filePaths;
		this.imageWidth = imageWidth;
	}
	
	@Override
	public int getCount(){
		return this.filePaths.size();
	}
	
	@Override
	public Object getItem(int position){
		return this.filePaths.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ImageView imageView;
		if(convertView == null){
			imageView = new ImageView(activity);
		}else{
			imageView = (ImageView)convertView;
		}
		
		//Get Screen Dimensions
		Bitmap image = decodeFile(filePaths.get(position), imageWidth, imageWidth);
		
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth));
		imageView.setImageBitmap(image);
		
		//ImageView Click Listener
		imageView.setOnClickListener(new OnImageClickListener(position));
		
		return imageView;
	}
	
	class OnImageClickListener implements OnClickListener{
		int position;
		
		//Constructor
		public OnImageClickListener(int position){
			this.position = position;
		}
		
		@Override
		public void onClick(View v){
			//On Selecting GridView Image Upload Image
			Intent i = new Intent(activity, FullScreenViewActivity.class);
			i.putExtra("position", position);
			activity.startActivity(i);
		}
	}
	
	//Resizing image size
	public static Bitmap decodeFile(String filePath, int WIDTH, int HEIGHT){
		
		try{
			File f = new File (filePath);
			
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			
			final int REQUIRED_WIDTH = WIDTH;
			final int REQUIRED_HEIGHT = HEIGHT;
			
			int scale = 1;
			while(o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HEIGHT)
				scale *= 2;
			
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
}
