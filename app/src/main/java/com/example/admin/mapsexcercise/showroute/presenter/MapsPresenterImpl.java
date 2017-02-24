package com.example.admin.mapsexcercise.showroute.presenter;

import android.os.Handler;
import android.util.Log;

import com.example.admin.mapsexcercise.helper.PositionThread;
import com.example.admin.mapsexcercise.model.directions.Leg;
import com.example.admin.mapsexcercise.model.directions.Result;
import com.example.admin.mapsexcercise.model.directions.Route;
import com.example.admin.mapsexcercise.model.directions.Step;
import com.example.admin.mapsexcercise.rest.RetrofitHelper;
import com.example.admin.mapsexcercise.showroute.view.MapsView;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2/23/2017.
 */

public class MapsPresenterImpl implements MapsPresenter {

    private static final String TAG = "MapsPresenterTAG_";
    private MapsView mapsView;
    private PositionThread positionThread;

    public MapsPresenterImpl(MapsView mapsView) {
        this.mapsView = mapsView;
    }

    @Override
    public void validate(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, String speed) {
        if(positionThread != null) positionThread.stopProcess();
        if(fromLatitude == null || fromLatitude.equals("")){
            mapsView.wrongValidation("From latitude could not be empty");
            return;
        }
        if(fromLongitude == null || fromLongitude.equals("")){
            mapsView.wrongValidation("From longitude could not be empty");
            return;
        }
        if(toLatitude == null || toLatitude.equals("")){
            mapsView.wrongValidation("To latitude could not be empty");
            return;
        }
        if(toLongitude == null || toLongitude.equals("")){
            mapsView.wrongValidation("To longitude could not be empty");
            return;
        }
        if(speed == null || speed.equals("")){
            mapsView.wrongValidation("To longitude could not be empty");
            return;
        }
        // TODO: 2/23/2017 Finish validating
        mapsView.correctValidation();
    }

    @Override
    public void enableForm(boolean enabled) {
        mapsView.enableForm(enabled);
    }

    @Override
    public void getDirections(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude) {
        StringBuffer origin = new StringBuffer();
        StringBuffer destination =  new StringBuffer();
        origin.append(fromLatitude).append(",").append(fromLongitude);
        destination.append(toLatitude).append(",").append(toLongitude);
        Call<Result> call = new RetrofitHelper.Factory().getDirections(origin.toString(), destination.toString());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                List<Route> routes = result.getRoutes();
                if(routes.size() > 0){
                    List<Leg> legs = routes.get(0).getLegs();
                    if(legs.size() > 0) {
                        List<Step> steps = legs.get(0).getSteps();
                        for (Step step : steps) {
                            List<LatLng> latLngs = PolyUtil.decode(step.getPolyline().getPoints());
                            for(LatLng latLng : latLngs)
                                mapsView.addLine(latLng);
                        }
                    }
                    mapsView.drawRoute();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                mapsView.wrongValidation("Error while calculating the route");
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void updateLocation(List<LatLng> latLngs, Handler handler, String speedVal) {
        positionThread = new PositionThread(latLngs, handler, speedVal);
        positionThread.start();
    }
}
