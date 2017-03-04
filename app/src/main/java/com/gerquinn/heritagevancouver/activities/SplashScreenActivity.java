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

    private  String resStr;

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

            Observer<String> observer = new Observer<String>() {
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

            /*Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    Log.d("SUBSCRIBE", e.toString());
                    getBuildings();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            observable.subscribe(observer);*/


            /*Observable<String> observable = Observable.fromIterable(arrayList).create(new ObservableOnSubscribe<String>() {

                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {

                    for(int i = 0; i <= arrayList.size() - 1; i++) {
                        String str = getBuildings(arrayList.get(i));
                    }
                    e.onComplete();
                    Log.d("SUBSCRIBE", e.toString());
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());*/

            ReactiveHelper reactiveHelper = new ReactiveHelper();
            Observable<String> observable = reactiveHelper.createObservableFromIterable(arrayList, context);

            observable.subscribe(observer);

        /*Flowable flowable = Flowable.fromArray(arrayList).create(new FlowableOnSubscribe() {
            @Override
            public void subscribe(FlowableEmitter e) throws Exception {
                for(int i = 0; i <= arrayList.size() - 1; i++) {
                    Log.d("SUBSCRIBE", arrayList.get(i));
                    //e.onNext(arrayList.get(i));
                    String str = getBuildings(arrayList.get(i));
                }
                //e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = flowable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d("RXResult", s);
            }
        });*/
        /*.subscribeWith(new DisposableSubscriber<String>() {

                    @Override
                    public void onNext(String s) {
                        try {
                            String str = getBuildings(s);
                            Log.d("RESULT", str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("RXResult", s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

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

    private String getBuildings(final String tableName) throws IOException, JSONException {


                OkHttpClient client = new OkHttpClient();
                Log.d("TABLE NAME", tableName);
                Response response = null;
                try {
                    response = client.newCall(new Request.Builder().url(url_all_buildings+ "?tableName=" + tableName).build()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(response.isSuccessful()) {

                    String resStr = null;
                    try {
                        resStr = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JSONObject JObject = new JSONObject( resStr );
                    JSONArray JArray = JObject.getJSONArray("buildings");
                    Log.d("JSONARRAY", JArray.toString());

                    DatabaseHandler dbHandler = new DatabaseHandler(context);
                    dbHandler.createTable(tableName, JArray);
                }
                return "";
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
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
