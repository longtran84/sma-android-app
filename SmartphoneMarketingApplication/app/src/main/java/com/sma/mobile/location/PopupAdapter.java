package com.sma.mobile.location;

/**
 * Created by longtran on 12/04/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import com.fintechviet.android.sdk.model.LatitudeLongitude;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.sma.mobile.R;
import com.squareup.picasso.Callback;

/**
 *
 */
public class PopupAdapter implements InfoWindowAdapter {

    private View popup = null;
    private LayoutInflater inflater = null;
    private Map<String, LatitudeLongitude> hashMapLatitudeLongitude = null;
    private Context context = null;
    private Marker lastMarker = null;

    public PopupAdapter(Context context, LayoutInflater inflater, Map<String, LatitudeLongitude> hashMapLatitudeLongitude) {
        this.context = context;
        this.inflater = inflater;
        this.hashMapLatitudeLongitude = hashMapLatitudeLongitude;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return (null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            popup = inflater.inflate(R.layout.custom_infowindow, null);
        }
        if (lastMarker == null
                || !lastMarker.getId().equals(marker.getId())) {
            lastMarker = marker;
            LatitudeLongitude latitudeLongitude = hashMapLatitudeLongitude.get(marker.getId());
            ImageView imageView = (ImageView) popup.findViewById(R.id.client_pic);
            TextView textView = (TextView) popup.findViewById(R.id.text_view_title);
            textView.setText(latitudeLongitude.getName());
            TextView textViewDescription = (TextView) popup.findViewById(R.id.text_view_description);
            textViewDescription.setText(latitudeLongitude.getVicinity());
        }
        return (popup);
    }

    /**
     *
     */
    public static class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError() {
            Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.showInfoWindow();
            }
        }
    }
}
