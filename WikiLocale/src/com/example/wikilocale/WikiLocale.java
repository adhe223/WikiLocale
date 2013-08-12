package com.example.wikilocale;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class WikiLocale extends Activity implements OnClickListener {
	protected TextView output;
	private LocationMan locMan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wiki_locale);
		output = (TextView) findViewById(R.id.output);
		
		////Old Reference
		////Start the LocationServices service
		////Intent intent = new Intent(this, LocationServices.class);
		////this.startService(intent);
		
		//Start the LocationMan custom location listener we will use for location
		//based services
		locMan = new LocationMan(this, output);
		
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
			finish();
			break;
		case R.id.set_home:
			locMan.setHome();
			break;
		case R.id.open_current_button:
			locMan.getLocation();
		}
	}
}
