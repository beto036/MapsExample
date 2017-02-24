package com.example.admin.mapsexcercise.showroute.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.mapsexcercise.R;
import com.example.admin.mapsexcercise.showroute.presenter.MapsPresenter;
import com.example.admin.mapsexcercise.showroute.presenter.MapsPresenterImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsView {

    private static final String TAG = "MATAG_";
    private GoogleMap mMap;
    private EditText fromLatitude;
    private EditText fromLongitude;
    private EditText toLatitude;
    private EditText toLongitude;
    private EditText speed;
    private Button button;
    private MapsPresenter mapsPresenter;

    private String fromLatitudeVal;
    private String fromLongitudeVal;
    private String toLatitudeVal;
    private String toLongitudeVal;
    private String speedVal;

    private PolylineOptions polylineOptions;
    private CircleOptions circleOptions;

    private Handler myHandler;
    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        circleOptions = new CircleOptions();
        mapsPresenter =  new MapsPresenterImpl(this);
        fromLatitude = (EditText) findViewById(R.id.fromLatitude);
        fromLongitude = (EditText) findViewById(R.id.fromLongitude);
        toLatitude = (EditText) findViewById(R.id.toLatitude);
        toLongitude = (EditText) findViewById(R.id.toLongitude);
        speed = (EditText) findViewById(R.id.speedTxt);
        button = (Button) findViewById(R.id.btnMap);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LatLng position = msg.getData().getParcelable("LATLNG");
                updateLocation(position);
            }


        };
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapsPresenter.enableForm(true);
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void getValues(View view) {
        polylineOptions = new PolylineOptions();
        fromLatitudeVal = this.fromLatitude.getText().toString();
        fromLongitudeVal = this.fromLongitude.getText().toString();
        toLatitudeVal = this.toLatitude.getText().toString();
        toLongitudeVal = this.toLongitude.getText().toString();
        speedVal = this.speed.getText().toString();

        mapsPresenter.validate(fromLatitudeVal, fromLongitudeVal, toLatitudeVal, toLongitudeVal, speedVal);
    }

    @Override
    public void correctValidation() {
        // TODO: 2/23/2017 Draw in the map
        mMap.clear();
        LatLng from = new LatLng(Float.parseFloat(fromLatitudeVal), Float.parseFloat(fromLongitudeVal));
        LatLng to = new LatLng(Float.parseFloat(toLatitudeVal), Float.parseFloat(toLongitudeVal));
        LatLngBounds route = new LatLngBounds(from, to);
        mMap.addMarker(new MarkerOptions().position(from).title("From Marker"));
        mMap.addMarker(new MarkerOptions().position(to).title("To Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(from, 15f));
        mapsPresenter.getDirections(fromLatitudeVal, fromLongitudeVal, toLatitudeVal, toLongitudeVal);

    }

    @Override
    public void wrongValidation(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enableForm(boolean enabled) {
        fromLatitude.setEnabled(enabled);
        fromLongitude.setEnabled(enabled);
        toLatitude.setEnabled(enabled);
        toLongitude.setEnabled(enabled);
        speed.setEnabled(enabled);
        button.setEnabled(enabled);
    }

    @Override
    public void addLine(LatLng latLng) {
        polylineOptions.add(latLng);
    }

    @Override
    public void drawRoute() {
        polylineOptions.color(Color.GREEN);
        polylineOptions.width(10);
        mMap.addPolyline(polylineOptions);
        Log.d(TAG, "drawRoute: ");
        mapsPresenter.updateLocation(polylineOptions.getPoints(), myHandler, speedVal);
    }

    public void updateLocation(LatLng latLng) {
        if(circle != null){
            circle.remove();
        }
        circleOptions.center(latLng);
        circleOptions.fillColor(Color.BLUE);
        circleOptions.radius(10);
        circleOptions.strokeColor(Color.BLUE);
        circle = mMap.addCircle(circleOptions);
    }




}
