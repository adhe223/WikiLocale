package com.example.wikilocale;

import com.example.wikilocale.LocService.LocalBinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.os.IBinder;

public class WikiLocale extends Activity implements OnClickListener {
	protected TextView output;
	private boolean mBounded;
	public LocService ls;
	private ServiceConnection mConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wiki_locale);
		output = (TextView) findViewById(R.id.output);
		
		mConnection = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
				Toast.makeText(WikiLocale.this, "Service is disconnected!", Toast.LENGTH_SHORT).show();
				mBounded = false;
				ls = null;
			}
			
			public void onServiceConnected(ComponentName name, IBinder service) {
				Toast.makeText(WikiLocale.this, "Service is connected!", Toast.LENGTH_SHORT).show();
				mBounded = true;
				LocalBinder mLocalBinder = (LocalBinder)service;
				ls = mLocalBinder.getServerInstance();
			}
		};
		
		//Start the LocationServices service. Usine startService() along with
		//the binding command should ensure it stays alive but can also
		//have its methods accessed from this activity.
		Intent intent = new Intent(this, LocService.class);
		
		//Not sure if I need this and bindService or not
		this.startService(intent);			
		getApplicationContext().bindService(intent, mConnection, BIND_AUTO_CREATE);	//What does this flag do specifically?
		
//Old way to do it. Have encapsulated this in a service so the lifecycle can be controlled
//		//Start the LocationMan custom location listener we will use for location
//		//based services
//		locMan = new LocationMan(this);
		
		//Click Listeners for buttons
		View open_current_button = findViewById(R.id.open_current_button);
		open_current_button.setOnClickListener(this);
		View test_button = findViewById(R.id.set_home);
		test_button.setOnClickListener(this);
		View settings_button = findViewById(R.id.settings_button);
		settings_button.setOnClickListener(this);
		View exit_button = findViewById(R.id.exit_button);
		exit_button.setOnClickListener(this);
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	    if(mBounded) {
	    	getApplicationContext().unbindService(mConnection);
	        mBounded = false;
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Prefs.class));
			return true;
		}
		return false;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit_button:
		    if(mBounded) {
		    	getApplicationContext().unbindService(mConnection);
		        mBounded = false;
		    }
			finish();
			break;
		case R.id.set_home:
			if (ls == null) {
				Toast.makeText(WikiLocale.this, "Can't set home, service not connected.", Toast.LENGTH_SHORT).show();
			}
			else {
				ls.lm.setHome();
			}
			break;
		case R.id.open_current_button:
			if (ls == null) {
				Toast.makeText(WikiLocale.this, "Can't get location, service not connected.", Toast.LENGTH_SHORT).show();
			}
			else {
				ls.lm.getLocation();
			}
		}
	}
}
