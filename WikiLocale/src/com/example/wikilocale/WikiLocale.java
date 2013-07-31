package com.example.wikilocale;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class WikiLocale extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wiki_locale);
		
		//Click Listeners for buttons
		View open_current_button = findViewById(R.id.open_current_button);
		open_current_button.setOnClickListener(this);
		View test_button = findViewById(R.id.test_button);
		test_button.setOnClickListener(this);
		View settings_button = findViewById(R.id.settings_button);
		settings_button.setOnClickListener(this);
		View exit_button = findViewById(R.id.exit_button);
		exit_button.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wiki_locale, menu);
		return true;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit_button:
			finish();
			break;
		}
	}

}
