package gs.maps.nestedscroll;

import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.model.LatitudeLongitude;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.sma.mobile.location.PopupAdapter;
import com.sma.mobile.location.beans.JMarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportNestedScrollMapFragment extends SupportMapFragment implements JOnMapReadyCallback {

    private GoogleMap googleMap;
    private JOnMapReadyCallback jOnMapReadyCallback;
    private JOnMarkerClickListener jOnMarkerClickListener;
    private Map<String, LatitudeLongitude> mapLatitudeLongitude;
    private PopupAdapter popupAdapter;


    public JOnMarkerClickListener getJOnMarkerClickListener() {
        return jOnMarkerClickListener;
    }

    public void setJOnMarkerClickListener(JOnMarkerClickListener jOnMarkerClickListener) {
        this.jOnMarkerClickListener = jOnMarkerClickListener;
    }

    public JOnMapReadyCallback getJOnMapReadyCallback() {
        return jOnMapReadyCallback;
    }

    public void setJOnMapReadyCallback(JOnMapReadyCallback jOnMapReadyCallback) {
        this.jOnMapReadyCallback = jOnMapReadyCallback;
    }

    /**
     * @return
     */
    public static SupportNestedScrollMapFragment newInstance() {
        return new SupportNestedScrollMapFragment();
    }

    /**
     * @param options
     * @return
     */
    public static SupportNestedScrollMapFragment newInstance(GoogleMapOptions options) {
        SupportNestedScrollMapFragment fragment = new SupportNestedScrollMapFragment();
        Bundle args = new Bundle();
        args.putParcelable("MapOptions", options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        NestedScrollMapView scrollingView = new NestedScrollMapView(getContext());
        scrollingView.setjOnMapReadyCallback(this);
        scrollingView.addView(view);
        getMapAsync(scrollingView);
        mapLatitudeLongitude = new HashMap<String, LatitudeLongitude>();
        return scrollingView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setIndoorEnabled(true);
        if (null != getJOnMarkerClickListener()) {
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    getJOnMarkerClickListener().onMarkerClick(marker);
                    return false;
                }
            });
        }
        if (null != getJOnMapReadyCallback()) {
            getJOnMapReadyCallback().onMapReady(googleMap);
        }
    }

    /**
     * @param listJMarkerOptions
     */
    public void addMarker(List<JMarkerOptions> listJMarkerOptions) {
        if (null == listJMarkerOptions || listJMarkerOptions.size() <= 0) {
            return;
        }
        googleMap.clear();
        for (JMarkerOptions jMarkerOptions : listJMarkerOptions) {
            if (null != jMarkerOptions && null != googleMap) {
                Marker marker = googleMap.addMarker(jMarkerOptions.getMarkerOptions());
                mapLatitudeLongitude.put(marker.getId(), jMarkerOptions.getLatitudeLongitude());
                popupAdapter = new PopupAdapter(getActivity(), getLayoutInflater(), mapLatitudeLongitude);
            }
        }
        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(popupAdapter);
    }

    /**
     * @param markerOptions
     */
    public void addMarker(MarkerOptions markerOptions) {
        if (null == markerOptions) {
            return;
        }
        googleMap.addMarker(markerOptions);
    }

    /**
     * LatLng(double latitude, double longitude)
     * https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLng
     *
     * @param latitude
     * @param longitude
     */
    public final void moveCamera(double latitude, double longitude, int zoom) {
        if (null != googleMap) {
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(latitude, longitude))
                    .zoom(zoom)
                    .bearing(0)
                    .tilt(0)
                    .build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom), 2000, null);
        }
    }
}
