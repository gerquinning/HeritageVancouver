package com.gerquinn.heritagevancouver.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class HttpRequestWithEntity extends  AsyncTask<String, Void, Void> {
	
	/*private String method;
	
	public HttpRequestWithEntity(String url, String method){
		if(method == null || (method != null && method.isEmpty())){
			//this.method = HttpMethod.GET();
		}else{
			this.method = method;
		}
		
		try{
			setURI(new URI(url));
		}catch(URISyntaxException e){
			e.printStackTrace();
		}
	}*/
	
	private String jsonUrl = "http://quinntechssential.com/heritage_vancouver/user_img_upload/fileinfo.php";
	
	 private Activity activity;
	 private Context context;
	 
	 private HttpResponse response;
	 
	 public HttpRequestWithEntity(Activity activity, Context context){
	    this.activity = activity;
		this.context = context;
	}
	
	public HttpResponse sendJSONObject(String uri, String json){
		
		int TIMEOUT_MILLISEC = 10000;
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,  TIMEOUT_MILLISEC);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		HttpPost request = new HttpPost(uri);
		try {
			StringEntity se = new StringEntity(json);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
			request.setEntity(se);
			//request.setEntity(new ByteArrayEntity(
					//json.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response = client.execute(request);
			
			if(response != null){
				InputStream in = response.getEntity().getContent();
				StringWriter writer = new StringWriter();
				IOUtils.copy(in, writer, "UTF-8");
				String resString = writer.toString();
				System.out.println("resString: " + resString);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		
		/*InputStream inputStream = null;
		HttpClient httpClient = null;
		HttpRequestWithEntity httpGet = null;
		int status = Def.REQUEST_INVALID;
		try{
			httpClient = new DefaultHttpClient();
			httpGet = new HttpRequestWithEntity(jsonUrl, method);
			JSONObject jsonObject = new JSONObject();
			for(String[] pair : params){
				jsonObject.put(pair[0], pair[1]);
			}
			
			StringEntity stringEntity = new StringEntity(json);
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			
			httpGet.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(httpGet);
			status = response.getStatusLine().getStatusCode();
			
			Log.d(TAG, "status: " + status);
			if(response != null && (status == Def.CREATED || status == Def.OK)){
				inputStream = response.getEntity().getContent();
				if(inputStream != null){
					String json = StreamUtils.convertStreamToString(inputStream);
					userId = JSONUtils.getUserId(json);
					iUserId.sendUserId(userId);
					Log.d("ger", "userId = " + userId);
				}
			}
		}catch(Exception e){
			Log.e(TAG, "Error during send");
			status = Def.NETWORK_ERROR;
		}
		return status;*/ 
	}
	
	@Override
	protected Void doInBackground(String... params) {
		if(params == null)
			return null;
		
		System.out.println("Sending... " + params[0]);
		String json = params[0]; 
		
		response = sendJSONObject(jsonUrl, json);
		System.out.println("Response: " + response.getStatusLine());
		
		return null;
	}
	
	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Void result){
		super.onPostExecute(result);
	}

}
