package com.gerquinn.heritagevancouver.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gerquinn.heritagevancouver.FullScreenViewActivity;
import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.helpers.AppController;

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

public class RemoteGridViewAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<String> filePaths = new ArrayList<String>();
	private int imageWidth;
	ImageView imageView;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	ArrayList<String> imagePaths = new ArrayList<String>();
	
	public RemoteGridViewAdapter(Activity activity, ArrayList<String> filePaths, int imageWidth){
		this.activity = activity;
		this.filePaths = filePaths;
		this.imageWidth = imageWidth;
	}
	
	public RemoteGridViewAdapter() {
		// TODO Auto-generated constructor stub
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
		
		if(convertView == null){
			convertView = activity.getLayoutInflater()
					.inflate(R.layout.activity_picture_gallery, parent, false);
		}
		
		if(imageLoader == null){
			System.out.println("Image Loader Empty");
			imageLoader = AppController.getInstance().getImageLoader();	
		}
		
		//GalleryItem item = getItem(position);
		
		NetworkImageView gridImage = (NetworkImageView)convertView.findViewById(R.id.image1);
		gridImage.setDefaultImageResId(R.drawable.img_placeholder);
		gridImage.setImageUrl(imagePaths.get(position), imageLoader);
		gridImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
		gridImage.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth));
		
		System.out.println("Image Path: " + imagePaths.get(position));
		
		return convertView;
		
		/*ImageView imageView;
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
		
		return imageView;*/
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
	
	/**
	 * Getter and Setter methods for the 
	 * List of Images from the Server
	 */
	public ArrayList<String> getFilesArray() {
		return imagePaths;
	}

	public void setFilesArray(ArrayList<String> imagePaths) {
		this.imagePaths = imagePaths;
		
		for(int i = 0; i <= imagePaths.size() - 1; i++){
			System.out.println("FILES: " + imagePaths.get(i));
		}
	}
}
