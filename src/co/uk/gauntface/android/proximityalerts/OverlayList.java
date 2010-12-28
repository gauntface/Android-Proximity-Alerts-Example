package co.uk.gauntface.android.proximityalerts;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class OverlayList extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlayItems;
	
	public OverlayList(Drawable drawable) {
		super(boundCenterBottom(drawable));
		mOverlayItems = new ArrayList<OverlayItem>();
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int position) {
		if(position < 0 || position >= mOverlayItems.size()) {
			return null;
		}
		return mOverlayItems.get(position);
	}

	@Override
	public int size() {
		return mOverlayItems.size();
	}

	public void add(OverlayItem overlayItem) {
		mOverlayItems.add(overlayItem);
		populate();
	}
}
