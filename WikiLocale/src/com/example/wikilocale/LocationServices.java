package com.example.wikilocale;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.TextView;

public class LocationServices extends Service {
	private Context cont;
	TextView output;
	LocationMan loc;
	protected TextView output2;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		cont = this;
		//output = 
		
		//Use our custom location listener in order to access location data and send notification
		loc = new LocationMan(cont, output);
		
	}
}
	