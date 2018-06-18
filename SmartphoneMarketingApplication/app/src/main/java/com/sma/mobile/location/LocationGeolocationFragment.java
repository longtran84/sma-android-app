package com.sma.mobile.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.LatitudeLongitude;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.location.adapters.LocationGeolocationAdapter;
import com.sma.mobile.location.beans.JMarkerOptions;
import com.sma.mobile.location.beans.LatLngBean;
import com.sma.mobile.notification.FirebaseMessagingResponse;
import com.sma.mobile.utils.LogHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import gs.maps.nestedscroll.BottomSheetFragment;
import gs.maps.nestedscroll.JBottomSheetCallback;
import gs.maps.nestedscroll.JOnMapReadyCallback;
import gs.maps.nestedscroll.JOnMarkerClickListener;
import gs.maps.nestedscroll.SupportNestedScrollMapFragment;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 03/04/2018.
 */

public class LocationGeolocationFragment extends AbstractFragment implements View.OnClickListener {

    private static final String TAG = LocationGeolocationFragment.class.getName();

    @BindView(R.id.nearby_stops)
    RecyclerView recyclerView;

    @BindView(R.id.floating_action_location_menu)
    FloatingActionMenu floatingActionMenuLocationFilter;

    @BindView(R.id.bank_location_type_bank_agency)
    FloatingActionButton floatingActionButtonBankAgency;

    @BindView(R.id.bank_location_type_atm)
    FloatingActionButton floatingActionButtonATM;

    @BindView(R.id.bank_location_type_sales_promotion)
    FloatingActionButton floatingActionButtonSalePromotion;


    private LocationGooglePlayServicesWithFallbackProvider provider;
    private SmartLocation smartLocation;

    private final boolean GRID_LAYOUT = false;
    private BottomSheetFragment bottomSheetFragment;
    private LocationGeolocationAdapter locationGeolocationAdapter;
    private SupportNestedScrollMapFragment supportNestedScrollMapFragment;
    private List<LatLngBean> itemList;
    private List<JMarkerOptions> listJMarkerOptions;
    private int currentPosition = 0;
    private FirebaseMessagingResponse firebaseMessaging;
    private float slideOffsetCurrent;
    private BitmapDescriptor bitmapDescriptor;

    /**
     * @param firebaseMessaging
     * @return
     */
    public static LocationGeolocationFragment newInstance(FirebaseMessagingResponse firebaseMessaging) {
        LocationGeolocationFragment locationGeolocationFragment = new LocationGeolocationFragment();
        Bundle args = new Bundle();
        args.putParcelable(FirebaseMessagingResponse.class.getName(), firebaseMessaging);
        locationGeolocationFragment.setArguments(args);
        return locationGeolocationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_geolocation, container, false);
    }

    /**
     * @param name
     * @return
     */
    private Bitmap getMarkerBitmapFromView(String name) {
        //HERE YOU CAN ADD YOUR CUSTOM VIEW
        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker, null);

        //IN THIS EXAMPLE WE ARE TAKING TEXTVIEW BUT YOU CAN ALSO TAKE ANY KIND OF VIEW LIKE IMAGEVIEW, BUTTON ETC.
        TextView textView = (TextView) customMarkerView.findViewById(R.id.txt_name);
        textView.setText(name);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    /**
     *
     */
    private void bindingData(String typeFilter) {
        //showProcessing();
        currentPosition = 0;
        Location lastLocation = SmartLocation.with(getActivity()).location().getLastLocation();
        if (null == lastLocation) {
            lastLocation = new Location("");
            lastLocation.setLatitude(21.03187);
            lastLocation.setLongitude(105.78797);
        }
        FintechvietSdk.getInstance().nearbySearch(typeFilter, lastLocation.getLongitude(), lastLocation.getLatitude(), "", new JCallback<List<LatitudeLongitude>>() {
            @Override
            public void onResponse(Call<List<LatitudeLongitude>> call, Response<List<LatitudeLongitude>> response) {
                if (response.code() == 200) {
                    itemList.clear();
                    listJMarkerOptions.clear();
                    List<LatitudeLongitude> listLatitudeLongitude = response.body();
                    int position = 0;
                    for (LatitudeLongitude latitudeLongitude : listLatitudeLongitude) {
                        LatLngBean latLngBean = new LatLngBean();
                        latLngBean.setTitle(latitudeLongitude.getName());
                        latLngBean.setSnippet(latitudeLongitude.getVicinity());
                        latLngBean.setReference(latitudeLongitude.getReference());
                        latLngBean.setPosition(position);
                        latLngBean.setDistance(latitudeLongitude.getDistance());
                        switch (typeFilter) {
                            case "BANK_AGENCY":
                                latLngBean.setType(1);
                                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_bank);
                                break;
                            case "ATM":
                                latLngBean.setType(2);
                                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_atm);
                                break;
                            case "AD_LOCATION":
                                latLngBean.setType(3);
                                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gif);
                                break;
                            default:
                                latLngBean.setType(2);
                                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_atm);
                                break;
                        }
                        try {
                            latLngBean.setLongitude(Double.parseDouble(latitudeLongitude.getLongitude()));
                            latLngBean.setLatitude(Double.parseDouble(latitudeLongitude.getLatitude()));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        itemList.add(latLngBean);
                        //BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("Custom Marker"))
                        MarkerOptions markerOptions = new MarkerOptions()
                                .icon(bitmapDescriptor)
                                .position(new LatLng(latLngBean.getLatitude(), latLngBean.getLongitude()))
                                .title(latitudeLongitude.getName());
                        JMarkerOptions jMarkerOptions = new JMarkerOptions();
                        jMarkerOptions.setLatitudeLongitude(latitudeLongitude);
                        jMarkerOptions.setMarkerOptions(markerOptions);
                        listJMarkerOptions.add(jMarkerOptions);
                        locationGeolocationAdapter.notifyDataSetChanged();
                        position++;
                    }
                    supportNestedScrollMapFragment.addMarker(listJMarkerOptions);
                }
                if (Float.compare(slideOffsetCurrent, 0.0f) == 0) {
                    LinearLayout.LayoutParams recyclerViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 256);
                    recyclerView.setLayoutParams(recyclerViewLayoutParams);
                } else {
                    LinearLayout.LayoutParams recyclerViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    recyclerView.setLayoutParams(recyclerViewLayoutParams);
                }
                recyclerView.scrollToPosition(currentPosition);
                locationGeolocationAdapter.setCheckedPosition(currentPosition);
                hideProcessing();
            }

            @Override
            public void onFailure(Call<List<LatitudeLongitude>> call, Throwable t) {
                hideProcessing();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.firebaseMessaging = args.getParcelable(FirebaseMessagingResponse.class.getName());
        }
        ButterKnife.bind(this, view);
        MapsInitializer.initialize(getContext());
        floatingActionMenuLocationFilter.setClosedOnTouchOutside(true);
        floatingActionButtonBankAgency.setOnClickListener(this);
        floatingActionButtonATM.setOnClickListener(this);
        floatingActionButtonSalePromotion.setOnClickListener(this);
        provider = new LocationGooglePlayServicesWithFallbackProvider(getActivity());
        smartLocation = new SmartLocation.Builder(getActivity()).logging(true).build();
        LinearLayout.LayoutParams recyclerViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 256);
        recyclerView.setLayoutParams(recyclerViewLayoutParams);
        listJMarkerOptions = new ArrayList<>();
        // To get map from MapFragment from layout
        supportNestedScrollMapFragment = SupportNestedScrollMapFragment.newInstance(new GoogleMapOptions());
        getFragmentManager().beginTransaction()
                .replace(R.id.contentFragment, supportNestedScrollMapFragment, null)
                .commit();
        supportNestedScrollMapFragment.setJOnMapReadyCallback(new JOnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap var1) {
                if (null != firebaseMessaging) {
                    bindingData("AD_LOCATION");
                    supportNestedScrollMapFragment.moveCamera(firebaseMessaging.getLatitude(), firebaseMessaging.getLongitude(), 19);
                } else {
                    bindingData("BANK_AGENCY");
                    Location lastLocation = SmartLocation.with(getActivity()).location().getLastLocation();
//                MarkerOptions markerOptions = new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current_location))
//                        .position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
//                supportNestedScrollMapFragment.addMarker(markerOptions);
                    if (null != lastLocation) {
                        supportNestedScrollMapFragment.moveCamera(lastLocation.getLatitude(), lastLocation.getLongitude(), 13);
                    }
                }
                smartLocation.location(provider).start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
//                        MarkerOptions markerOptions = new MarkerOptions()
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current_location))
//                                .position(new LatLng(location.getLatitude(), location.getLongitude()));
//                        supportNestedScrollMapFragment.addMarker(markerOptions);
                        if (null != firebaseMessaging) {
                            //todo
                        } else {
                            supportNestedScrollMapFragment.moveCamera(location.getLatitude(), location.getLongitude(), 13);
                        }
                    }
                });
            }
        });
        supportNestedScrollMapFragment.setJOnMarkerClickListener(new JOnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker var1) {
                for (LatLngBean latLngBean : itemList) {
                    Location locationMarkerSelected = new Location("");
                    locationMarkerSelected.setLatitude(var1.getPosition().latitude);
                    locationMarkerSelected.setLongitude(var1.getPosition().longitude);
                    Location locationMarker = new Location("");
                    locationMarker.setLatitude(latLngBean.getLatitude());
                    locationMarker.setLongitude(latLngBean.getLongitude());
                    float distanceInMeters = locationMarkerSelected.distanceTo(locationMarker);
                    if (Float.compare(distanceInMeters, 0.0f) == 0) {
                        locationGeolocationAdapter.setCheckedPosition(latLngBean.getPosition());
                        recyclerView.scrollToPosition(latLngBean.getPosition());
                        currentPosition = latLngBean.getPosition();
                        break;
                    }
                }
                return false;
            }
        });
        bottomSheetFragment = (BottomSheetFragment) getChildFragmentManager().findFragmentById(R.id.bottomSheet);
        bottomSheetFragment.setJBottomSheetCallback(new JBottomSheetCallback() {
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float offset = Math.max(slideOffset, 0);
                slideOffsetCurrent = offset;
                if (Float.compare(offset, 0.0f) == 0) {
                    LinearLayout.LayoutParams recyclerViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 256);
                    recyclerView.setLayoutParams(recyclerViewLayoutParams);
                    recyclerView.scrollToPosition(currentPosition);
                } else {
                    LinearLayout.LayoutParams recyclerViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    recyclerView.setLayoutParams(recyclerViewLayoutParams);
                }
            }
        });
        itemList = new ArrayList<>();
        locationGeolocationAdapter = new LocationGeolocationAdapter(getContext(), itemList, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
//                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                //locationGeolocationAdapter.changedData(position);
                recyclerView.scrollToPosition(position);
                bottomSheetFragment.setPeekHeight(0);
                LatLngBean latLngBean = itemList.get(position);
                supportNestedScrollMapFragment.moveCamera(latLngBean.getLatitude(), latLngBean.getLongitude(), 19);
                currentPosition = position;
            }
        });
        if (GRID_LAYOUT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        recyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0x40000000);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        recyclerView.setAdapter(locationGeolocationAdapter);
    }

    @Override
    public void onClick(View view) {
        floatingActionMenuLocationFilter.close(true);
        switch (view.getId()) {
            case R.id.bank_location_type_bank_agency:
                bindingData("BANK_AGENCY");
                break;
            case R.id.bank_location_type_atm:
                bindingData("ATM");
                break;
            case R.id.bank_location_type_sales_promotion:
                bindingData("AD_LOCATION");
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (itemList == null || itemList.size() <= 0) {
                bindingData("BANK_AGENCY");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SmartLocation.with(getActivity()).location().stop();
        LogHelper.d(TAG, "Location stopped!");
        SmartLocation.with(getActivity()).activity().stop();
        LogHelper.d(TAG, "Activity Recognition stopped!");
        SmartLocation.with(getActivity()).geofencing().stop();
        LogHelper.d(TAG, "Geofencing stopped!");
    }
}
