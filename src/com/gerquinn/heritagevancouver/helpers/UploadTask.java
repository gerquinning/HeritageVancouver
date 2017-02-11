/**************************************
 ********* Heritage Vancouver *********
 ********* Author: Ger Quinn **********
 *************** 2014 *****************
 **** http://quinntechssential.com ****
 **************************************/

package com.gerquinn.heritagevancouver.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.UploadPictureActivity;

public class UploadTask extends AsyncTask<String, Void, Void>{
	
	UploadPictureActivity uploadPictureActivity = new UploadPictureActivity();
	NotificationManager notifyManager;
	Builder builder;
	
	int serverResponseCode = 0;
        
    String uploadServerUri = "http://quinntechssential.com/heritage_vancouver/savetofile.php";
     
    /**********  File Path *************/
    final String uploadFilePath = "/storage/emulated/0/Pictures/Heritage Vancouver/";
    final String uploadFileName = "";
    
    private Activity activity;
    private Context context;
    
    public UploadTask(Activity activity, Context context){
    	this.activity = activity;
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(String... fileUris){
		if(fileUris == null)
			return null;
		
		System.out.println("Sending...");
		String fileUri = fileUris[0]; 
		
		uploadFile(fileUri);
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result){
		super.onPostExecute(result);
	}
	
	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	public int uploadFile(String sourceFileUri){
		
		String fileName = sourceFileUri;
		
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		
		final int id = 1;
		
		//Notification Manager Declaration
		notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context);
		builder.setContentTitle("Picture Upload").setContentText("Upload in progress").setSmallIcon(R.drawable.ic_action_upload);
		
		//If File does not exist
		if(!sourceFile.isFile()){
			Log.e("uploadFile", "Source File does not exist: " + sourceFileUri);
			
			activity.runOnUiThread(new Runnable(){
				@Override
				public void run(){
					Toast.makeText(context, "File does not exist", Toast.LENGTH_SHORT).show();
				}
			});
			
			return 0;
		}else{
			try{
				//Open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(uploadServerUri);
				
				//Open a Http connection to the URL
				conn = (HttpURLConnection)url.openConnection();
				conn.setDoInput(true);  //Allow Inputs
				conn.setDoOutput(true);  //Allow Outputs
				conn.setUseCaches(false);  //Don't use a cached copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection","Keep-Alive");
				conn.setRequestProperty("ENCTYPE","multipart/form-data");
				conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file",fileName);
				
				dos = new DataOutputStream(conn.getOutputStream());
				
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd); 
				
				System.out.println("FILENAME: " + fileName);
				
				dos.writeBytes(lineEnd);
				
				//Create a Buffer of Maximum Size
				bytesAvailable = fileInputStream.available();
				
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				
				//Read File and Write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				
				while(bytesRead > 0){
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					
					//Set the Progress Bar on the Notification
					builder.setProgress(100, bytesRead, false);
					notifyManager.notify(id, builder.build());
				}
				
				//Send Multipart form data necessary after File Data
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				//Response from the server 
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();
				
				Log.i("uploadFile", "HTTP Response is: " + serverResponseMessage + " : " + serverResponseCode);
				
				if(serverResponseCode == 200){
					
					//Read Response
					StringBuilder responseSB = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
					String line;
					while((line = br.readLine()) != null){
						responseSB.append(line);
					}
					
					//Close streams
					br.close();
					
					Log.i("Output: ", responseSB.toString());
					
					activity.runOnUiThread(new Runnable(){
						@Override
						public void run(){
							
							builder.setContentText("Upload Complete").setProgress(0, 0, false);
							notifyManager.notify(id, builder.build());
							
							//Log.i("Output: ", responseSB.toString());
							String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http:quinntechssential.com/heritage_vancouver/user_img_upload/"
                                    +uploadFileName;
							Toast.makeText(context, "File Upload Complete.", Toast.LENGTH_SHORT).show();
						}
					});
				}
				
				//Close the Streams
				fileInputStream.close();
				dos.flush();
				dos.close();
			}catch(MalformedURLException e){
				e.printStackTrace();
				activity.runOnUiThread(new Runnable(){
					@Override
					public void run(){
                        Toast.makeText(context, "MalformedURLException",
                                                            Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception", "Exception : "
                        + e.getMessage(), e);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return serverResponseCode;
		}
	}

}
