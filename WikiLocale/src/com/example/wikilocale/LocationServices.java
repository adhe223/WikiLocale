package com.example.wikilocale;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class LocationServices implements LocationListener {
	Context myContext;
	private LocationManager mgr;
	private String best;
	private Location oldLocation;
	private Location newLocation;
	public static final long tolerance = 50000; //In meters
	
	public LocationServices(Context myContext) {
		this.myContext = myContext;		
		mgr = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		best = mgr.getBestProvider(criteria, true);
		
		//Initialize the old and new location
		oldLocation = null;
		newLocation = mgr.getLastKnownLocation(best);
		
		//Request for app to update constantly on the given delay between calls
		mgr.requestLocationUpdates(best, 15000, 100, this);
	}
	
	//Get the location. Will update location before returning the data
	public Location getLocation() {
		return mgr.getLastKnownLocation(best);
	}
	
	@Override
	onLocationChanged(Location location) {
		oldLocation = newLocation;
		newLocation = location;
		if (!locSame()) {
			//Send notification to user with wiki page
		}
		
	}
	
	//Helper function used to decide if two locations are similar enough to each other.
	private boolean locSame() {
		if (oldLocation.distanceTo(newLocation) < tolerance) {
			return true;
		} else {
			return false;
		}
	}
	
}

