package com.example.admin.mapsexcercise.showroute.view;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 2/23/2017.
 */

public interface MapsView {
    void correctValidation();
    void wrongValidation(String error);
    void enableForm(boolean enabled);
    void addLine(LatLng latLng);
    void drawRoute();
}
