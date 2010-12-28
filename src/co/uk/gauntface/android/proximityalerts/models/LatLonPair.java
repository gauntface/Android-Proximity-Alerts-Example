package co.uk.gauntface.android.proximityalerts.models;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class LatLonPair {
	private double mLatitude;
	private double mLongitude;
	
	public LatLonPair() {
		mLatitude = -2.60312596534349;
		mLongitude = 51.4558140934651;
	}
	
	public LatLonPair(double latitude, double longitude) {
		mLatitude = latitude;
		mLongitude = longitude;
	}
	
	public static boolean isValid(double lat, double lon) {
		
		if((lon >= -180 && lon <= 180) && (lat >= -90 && lat <= 90)) {
			return true;
		}
		
		return false;
	}
	
	public void setLatitude(double latitude) {
		mLatitude = latitude;
	}
	
	public double getLatitude() {
		return mLatitude;
	}
	
	public void setLongitude(double longitude) {
		mLongitude = longitude;
	}
	
	public double getLongitude() {
		return mLongitude;
	}
	
	public GeoPoint getGeoPoint() {
		Log.v("gauntface", "LatLonPair: Latitude " + (int) (mLatitude * 1E6) + " longitude " + (int) (mLongitude * 1E6));
		return new GeoPoint((int) (mLatitude * 1E6), (int) (mLongitude * 1E6));
	}
}
