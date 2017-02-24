package com.example.admin.mapsexcercise.showroute.presenter;

/**
 * Created by admin on 2/23/2017.
 */

public interface MapsPresenter {
    void validate(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, String speed);
    void enableForm(boolean enabled);
    void getDirections(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude);
}
