package com.gerquinn.heritagevancouver.helpers;

import android.content.Context;
import android.util.Log;
import com.gerquinn.heritagevancouver.database.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

        Observable<String> observable = Observable.fromIterable(arrayList).create(new ObservableOnSubscribe<String>() {

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
                .observeOn(AndroidSchedulers.mainThread());

        return  observable;
    }

    private String getBuildings(String tableName) throws IOException, JSONException {

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

            return resStr;
        }
        return "";
    }
}
