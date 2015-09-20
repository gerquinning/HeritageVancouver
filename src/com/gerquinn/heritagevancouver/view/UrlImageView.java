package com.gerquinn.heritagevancouver.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class UrlImageView extends ImageView{

	private static class UrlLoadingTask extends AsyncTask<URL, Void, Bitmap>{
		private final ImageView updateView;
		private boolean isCancelled = false;
		private InputStream urlInputStream;
		
		private UrlLoadingTask(ImageView updateView){
			this.updateView= updateView;
		}
		
		@Override
		protected Bitmap doInBackground(URL... params){
			try{
				URLConnection con = params[0].openConnection();
				//can use some more params, i.e. caching directory etc
				con.setUseCaches(true);
				this.urlInputStream = con.getInputStream();
				return BitmapFactory.decodeStream(urlInputStream);
			}catch(IOException e){
				Log.w(UrlImageView.class.getName(), "Faile to load image from " + params[0], e);
				return null;
			}finally{
				if(this.urlInputStream != null){
					try{
						this.urlInputStream.close();
					}catch(IOException e){
						; //swallow
					}finally{
						this.urlInputStream = null;
					}
				}
			}
		}
		
		/*
	     * Just remember that we were cancelled, no synchronization necessary
	     */
		@Override
		protected void onCancelled(){
			this.isCancelled = true;
			try{
				if(this.urlInputStream != null){
					try{
						this.urlInputStream.close();
					}catch(IOException e){
						; //swallow
					}finally{
						this.urlInputStream = null;
					}
				}
			}finally{
				super.onCancelled();
			}
		}
		
		 @Override
		protected void onPostExecute(Bitmap result){
			if(!this.isCancelled){
				//hope that the call is thread safe
				this.updateView.setImageBitmap(result);
			}
		}
	}
	
	/*
	 * track loading task to cancel it
	 */
	private AsyncTask<URL, Void, Bitmap>currentLoadingTask;
	//Just for Sync
	private Object loadingMonitor = new Object();
	
	public UrlImageView(Context context){
		super(context);
	}
	
	public UrlImageView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public UrlImageView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	/**
	   * cancels pending image loading
	   */
	public void cancelLoading(){
		synchronized(loadingMonitor){
			if(this.currentLoadingTask != null){
				this.currentLoadingTask.cancel(true);
				this.currentLoadingTask = null;
			}
		}
	}
	
	@Override
	public void setImageBitmap(Bitmap bm){
		cancelLoading();
		super.setImageBitmap(bm);
	}
	
	@Override
	public void setImageDrawable(Drawable drawable){
		cancelLoading();
		super.setImageDrawable(drawable);
	}
	
	@Override
	public void setImageResource(int resId){
		cancelLoading();
		super.setImageResource(resId);
	}
	
	 @Override
	public void setImageURI(Uri uri) {
	    cancelLoading();
	    super.setImageURI(uri);
	  }
	
	 /**
	   * Loads image from given URL
	   *  @param url
	 * @return 
	   */
	public UrlImageView setImageUrl(URL url){
		Context context = null;
		UrlImageView urlImg = new UrlImageView(context);
		synchronized(loadingMonitor){
			cancelLoading();
			this.currentLoadingTask = new UrlLoadingTask(this).execute(url);
			return urlImg;
		}
	}
}
