package com.gerquinn.heritagevancouver.helpers;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	
	static Bitmap downloadBitmap(String url){
		final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);
		try{
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK){
				Log.w("ImageDownLoader", "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}
			
			final HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream inputStream = null;
				try{
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				}finally{
					if(inputStream != null){
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			getRequest.abort();Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
		}finally{
			if(client != null){
				client.close();
			}
		}
		return null;
	}
	
	private final WeakReference<ImageView> imageViewReference;
	
	public ImageDownloaderTask(ImageView imageView){
		imageViewReference = new WeakReference<ImageView>(imageView);
	}
	
	//Actual Download method, run in the Task Thread
	@Override
	protected Bitmap doInBackground(String... params){
		//params comes from the execute() call: params[0] is the url.
		return downloadBitmap(params[0]);
	}
	
	//Once the Image is downloaded, associates it to the ImageView
	@Override
	protected void onPostExecute(Bitmap bitmap){
		if(isCancelled()){
			bitmap = null;
		}
		
		if(imageViewReference != null){
			ImageView imageView = imageViewReference.get();
			if(imageView != null){
				if(bitmap != null){
					imageView.setImageBitmap(bitmap);
				}else{
					//imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.list_placeholder));
				}
			}
		}
	}

}
