package com.gerquinn.heritagevancouver.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.database.DatabaseHandler;
import com.gerquinn.heritagevancouver.helpers.ReactiveHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
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
    private Observer<String> observer;
    private Observable<String> observable;
    public ArrayList<String> buildingList;
    public  Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Boolean isInternet = haveNetworkConnection();
        final Intent intent = new Intent(this, MainActivity.class);
        context = this;

        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("all_buildings");
        arrayList.add("carrall_street_tour");
        arrayList.add("chinatown_tour");
        arrayList.add("japantown");
        arrayList.add("west_hastings_tour");

        if(isInternet) {

            observer = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Log.d("RESULT", s);
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

            ReactiveHelper reactiveHelper = new ReactiveHelper();
            observable = reactiveHelper.createObservableFromIterable(arrayList, context);

            observable.subscribe(observer);

        }else {

            Context context = getApplicationContext();
            CharSequence text = "Please check your internet connection";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            findViewById(R.id.progressBar).setVisibility(View.GONE);
            startActivity(intent);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(subscription != null) {
            subscription.cancel();
        }
    }

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}*/

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) { // connected to the internet
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                haveConnectedWifi = true;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                haveConnectedMobile = true;
            }
        } else {
            return  false;
        }
        return haveConnectedWifi || haveConnectedMobile;
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
