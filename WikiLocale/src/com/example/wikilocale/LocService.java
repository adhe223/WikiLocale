package com.example.wikilocale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

//Need this service to control the lifecycle of the custome LocationListener
//updates.

public class LocService extends Service {
	private Context myContext;
	private LocationMan lm;
	
	@Override
	public IBinder onBind(Intent intent) {
	    return null;
	}
	 
	@Override
	public void onCreate() {
		super.onCreate();
		 
		myContext = this;
		lm = new LocationMan(myContext);
	 }
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Toast.makeText(myContext, "WikiLocale Service Started", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Toast.makeText(myContext, "WikiLocale Service Destroyed", Toast.LENGTH_SHORT).show();
	}
}
