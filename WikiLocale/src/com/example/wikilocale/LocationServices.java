package com.example.wikilocale;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class LocationServices implements LocationListener {
	Context myContext;
	private LocationManager mgr;
	private String best;
	private Location oldLocation;
	private Location currentLocation;
	private Location home;
	private TextView output;
	public static final long tolerance = 50000; //In meters
	
	//Takes in Context and a TextView object used for output
	public LocationServices(Context myContext, TextView textView) {
		this.myContext = myContext;	
		output = textView;
		mgr = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		best = mgr.getBestProvider(criteria, true);
		
		//Initialize the old and new location
		oldLocation = null;
		if (mgr.getLastKnownLocation(best) == null) {
			log("\nCurrent location is null!");
			currentLocation = null;
			home = null;
		} else {
			currentLocation = mgr.getLastKnownLocation(best);
			setHome();
		}
		
		
		//Request for app to update constantly on the given delay between calls
		mgr.requestLocationUpdates(best, 15000, 100, this);
	}

	//Get the location. Will update location before returning the data
	public void getLocation() {
		oldLocation = currentLocation;
		currentLocation = mgr.getLastKnownLocation(best);
		log("\nCurrent location is: " + currentLocation.toString());
	}
	
	//Function to set the home location of the user
	public void setHome() {
		if (currentLocation != null) {
			home = mgr.getLastKnownLocation(best);
			log("\nHome set to: (" + home.getLatitude() + ", " + home.getLongitude() + ").");
		} else {
		log("Home not set, location is null!");}
	}
	
	//Only take action on the new location if the user is not home and not in the same place as he last was
	@Override
	public void onLocationChanged(Location location) {
		if (location == null) {
			log("\nLocation is null!");
		} else {
			oldLocation = currentLocation;
			currentLocation = location;
			if (!locSame(oldLocation, currentLocation) && !isHome(currentLocation)) {
				log("\nCurrent location is now: (" + currentLocation.getLatitude() + ", " + currentLocation.getLongitude() + ")");
				log("\nNotification sent!");
				notify(currentLocation);
			}
		}
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		//log("\nProvider disabled: " + provider);
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		//log("\nProvider enabled: " + provider);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//log("\nProvider status changed: " + provider + " extras=" + extras);
	}
	
	//Helper function used to decide if two locations are similar enough to each other.
	private boolean locSame(Location oldLocation, Location currentLocation) {
		//Check if old location is null (already guarded against current being null)
		if (oldLocation == null) {
			//If it is, then the new location is certainly not the same.
			return false;
		} else {
			if (oldLocation.distanceTo(currentLocation) < tolerance) {
				log("\nYou are in the same location as before!");
				return true;
			} else {
				return false;}
		}
	}
	
	//Helper function to check if the user is close to Home
	private boolean isHome(Location location) {
		if (home == null) {
			log("\nHome is null!");
			return false;
		} else {
			if (home.distanceTo(location) < tolerance) {
				log("\nYou are at home");
				return true;
			} else {
				return false;}
		}
	}
	
	//Helper function to send notification to user
	private void notify(Location location) {
		//!!!
		//Send notification that links to wiki
		//!!!
	}
	
	//Helper Function that writes the output text. Will clear it before each new write.
	private void log(String string) {
		output.append(string + "\n");
	}
		
//	//Helper function to defend against null exceptions
//	private boolean isNull(Location location) {
//		if (location == null) {
//			return true;
//		} else {
//			return false;
//		}
//	}	
}

