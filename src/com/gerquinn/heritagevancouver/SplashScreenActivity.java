package com.gerquinn.heritagevancouver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.gerquinn.heritagevancouver.database.LoadAllBuildings;

public class SplashScreenActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000;
	
	LoadAllBuildings loadAllBuildings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		loadAllBuildings = new LoadAllBuildings(this);
		
		new Handler().postDelayed(new Runnable(){
			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */
			
			@Override
			public void run(){
				// This method will be executed once the timer is over
				//Load All your Buildings
				loadAllBuildings.execute();
			}
		}, SPLASH_TIME_OUT);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}*/

}
