package co.uk.gauntface.android.proximityalerts;

import java.util.ArrayList;
import java.util.List;

import co.uk.gauntface.android.proximityalerts.models.LatLonPair;
import co.uk.gauntface.android.proximityalerts.receivers.ProximityAlert;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;

public class ProximityDisplay extends MapActivity {
	private static final String PROXIMITY_INTENT_ACTION = new String("co.uk.gauntface.android.proximityalerts.action.PROXIMITY_ALERT");
	
	private IntentFilter mIntentFilter;
	private ArrayList<LatLonPair> mPositions;
	private MapView mMapView;
	private MyLocationOverlay mLocOverlay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_display);
        
        mIntentFilter = new IntentFilter(PROXIMITY_INTENT_ACTION);
        
        createPositions();
        registerIntents();
        initMapView();
    }

    protected void onResume() {
    	super.onResume();
    	
    	mLocOverlay.enableMyLocation();
    	registerReceiver(new ProximityAlert(), mIntentFilter);
    }
    
    protected void onPause() {
    	super.onPause();
    	
    	mLocOverlay.disableMyLocation();
    }
    
    protected void onStop() {
    	super.onStop();
    	
    	mLocOverlay.disableMyLocation();
    }
    
    private void createPositions() {
    	mPositions = new ArrayList<LatLonPair>();
    	mPositions.add(new LatLonPair(51.4948, -0.1467));
    	mPositions.add(new LatLonPair(51.455814, -2.603125));
    	mPositions.add(new LatLonPair(53.475638,-2.242057));
    }
    
    private void registerIntents() {
    	for(int i = 0; i < mPositions.size(); i++) {
    		LatLonPair latLon = mPositions.get(i);
    		setProximityAlert(latLon.getLatitude(), 
    				latLon.getLongitude(), 
    				i+1, 
    				i);
    	}
    }
    
    private void setProximityAlert(double lat, double lon, final long eventID, int requestCode)
    {
    	// 100 meter radius
    	float radius = 100f;
    	
    	// Expiration is 10 Minutes (10mins * 60secs * 1000milliSecs)
    	long expiration = 600000;
    	
    	LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	
    	Intent intent = new Intent(PROXIMITY_INTENT_ACTION);
    	intent.putExtra(ProximityAlert.EVENT_ID_INTENT_EXTRA, eventID);
    	PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	
    	locManager.addProximityAlert(lat, lon, radius, expiration, pendingIntent);
    }
    
    private void initMapView() {
    	mMapView = (MapView) findViewById(R.id.MapDisplayMapView);
    	
    	mLocOverlay = new MyLocationOverlay(getApplicationContext(), mMapView);
    	List<Overlay> overlays = mMapView.getOverlays();
    	overlays.add(mLocOverlay);
    	
    	mMapView.setBuiltInZoomControls(true);
    	
    	addPositionOverlays();
    }
    
    private void addPositionOverlays() {
    	Drawable defaultMarker = getResources().getDrawable(R.drawable.map_marker);
    	defaultMarker.setBounds(0, 0, defaultMarker.getIntrinsicWidth(),
    			defaultMarker.getIntrinsicHeight());
    	OverlayList overlayList = new OverlayList(defaultMarker);
    	for(int i = 0; i < mPositions.size(); i++) {
    		LatLonPair latLon = mPositions.get(i);
    		OverlayItem overlay = new OverlayItem(latLon.getGeoPoint(), "Title "+i, "Snippet "+1);
    		overlayList.add(overlay);
    	}
    	List<Overlay> overlays = mMapView.getOverlays();
    	overlays.add(overlayList);
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}