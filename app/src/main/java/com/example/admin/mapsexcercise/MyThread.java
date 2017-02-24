package com.example.admin.mapsexcercise;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.admin.mapsexcercise.showroute.view.MapsView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by admin on 11/18/2016.
 */

public class MyThread extends Thread {

    private static final String TAG = "MyThreadTAG_";

    private List<LatLng> latLngs;
    private Handler handler;
    private Message message;
    private String speed;
    private volatile boolean exit = false;

    public MyThread(List<LatLng> latLngs, Handler handler, String speedVal) {
        this.handler = handler;
        this.latLngs = latLngs;
        this.speed = speedVal;
    }

    @Override
    public void run() {
        super.run();
        Log.d(TAG, "run: " + Thread.currentThread());
        float speed = Float.parseFloat(this.speed);
        for(LatLng latLng : latLngs){
            Log.d(TAG, "run: " + exit);
//            while(!exit) {
            if(exit) break;
            message = new Message();
            try {
                Thread.sleep((long) (10000 / speed));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bundle b = new Bundle();
            b.putParcelable("LATLNG", latLng);
            message.setData(b);
            handler.sendMessage(message);
//            }

        }
    }

    public void stopProcess(){
        exit = true;
    }
}
