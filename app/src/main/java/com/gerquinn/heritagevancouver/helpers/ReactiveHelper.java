package com.gerquinn.heritagevancouver.helpers;

import android.content.Context;
import android.util.Log;
import com.gerquinn.heritagevancouver.database.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ger on 2017-03-03.
 */

public class ReactiveHelper {

    private static final String URL_DATABASE = "http://quinntechssential.com/heritage_vancouver/databases/android_connect/get_all_buildings.php";
    private Context context;

    public void ReactiveHelper() {

    }

    public Observable<String> createObservableFromIterable(final ArrayList<String> arrayList, Context context) {

        this.context = context;

        return Observable.fromIterable(arrayList).create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                for(int i = 0; i <= arrayList.size() - 1; i++) {
                    getRemoteDatabase(arrayList.get(i));
                }
                e.onComplete();
                Log.d("SUBSCRIBE", e.toString());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Subscriber createFlowableFromArray(final ArrayList<String> arrayList) {

        return Flowable.fromArray(arrayList).create(new FlowableOnSubscribe() {
            @Override
            public void subscribe(FlowableEmitter e) throws Exception {
                for(int i = 0; i <= arrayList.size() - 1; i++) {
                    Log.d("SUBSCRIBE", arrayList.get(i));
                    getRemoteDatabase(arrayList.get(i));
                }
                //e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getRemoteDatabase(String tableName) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(new Request.Builder().url(URL_DATABASE+ "?tableName=" + tableName).build()).execute();

        if(response.isSuccessful()) {

            String resStr = response.body().string();

            JSONObject JObject = new JSONObject( resStr );
            JSONArray JArray = JObject.getJSONArray("buildings");

            DatabaseHandler dbHandler = new DatabaseHandler(context);
            Boolean tableExists = dbHandler.isTableExists(tableName, true);
            if(!tableExists) {
                dbHandler.createTable(tableName, JArray);
            }else {
                int tableCount = dbHandler.getProfilesCount(tableName);

                if(tableCount < JArray.length()) {
                    dbHandler.createTable(tableName, JArray);
                }
            }

        }
    }
}
