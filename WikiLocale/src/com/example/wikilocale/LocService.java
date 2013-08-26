package com.example.wikilocale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Binder;
import android.widget.TextView;
import android.widget.Toast;

//Need this service to control the lifecycle of the custome LocationListener
//updates.

public class LocService extends Service {
	private Context myContext;
	public LocationMan lm;
	IBinder mBinder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
	    return mBinder;
	}
	
	public class LocalBinder extends Binder {
		public LocService getServerInstance() {
			return LocService.this;
		}
	}
	 
	@Override
	public void onCreate() {
		super.onCreate();
		 
		myContext = this;
		lm = new LocationMan(myContext);
		Toast.makeText(myContext, "WikiLocale Service Started", Toast.LENGTH_SHORT).show();
	 }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Toast.makeText(myContext, "WikiLocale Service Destroyed", Toast.LENGTH_SHORT).show();
	}
}
