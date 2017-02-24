package com.example.admin.mapsexcercise.showroute.presenter;

import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by admin on 2/23/2017.
 */

public interface MapsPresenter {
    void validate(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, String speed);
    void enableForm(boolean enabled);
    void getDirections(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude);
    void updateLocation(List<LatLng> latLngs, Handler handler, String speedVal);
}
