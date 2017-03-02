package com.gerquinn.heritagevancouver.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.database.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    //private static int SPLASH_TIME_OUT = 2000;

    // url to get all buildings list
    private static String url_all_buildings = "http://quinntechssential.com/heritage_vancouver/databases/android_connect/get_all_buildings.php";
    //public String DB_NAME = "heritage_14";
    public String TABLE_NAME = "all_buildings";

    private Subscription subscription;

    private  String resStr;

    public ArrayList<String> buildingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Boolean isInternet = isInternetAvailable();
        final Intent intent = new Intent(this, MainActivity.class);

        if(isInternet) {

            Observer<String> observer = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Log.d("RXResult", s);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    startActivity(intent);
                }
            };

            Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {

                    getBuildings();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            observable.subscribe(observer);
            /*observable.subscribe(new Consumer<String>() {
                @Override
                public void accept(@NonNull String s) throws Exception {
                    Log.d("RXResult", "The result from this inner class action is: " + s);
                }
            });*/
        }else {

            Context context = getApplicationContext();
            CharSequence text = "Please check your internet connection";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        /*if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }*/
    }

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}*/

    private String getBuildings() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(new Request.Builder().url(url_all_buildings+ "?tableName=" + TABLE_NAME).build()).execute();

        if(response.isSuccessful()) {

            String resStr = response.body().string();

            JSONObject JObject = new JSONObject( resStr );
            JSONArray JArray = JObject.getJSONArray("buildings");

            DatabaseHandler dbHandler = new DatabaseHandler(this);
            dbHandler.createTable(TABLE_NAME, JArray);

            return resStr;
        }
        return "";
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAdd = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAdd.equals("");

        } catch (Exception e) {
            return false;
        }

    }


}
