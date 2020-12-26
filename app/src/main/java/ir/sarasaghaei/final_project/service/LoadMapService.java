package ir.sarasaghaei.final_project.service;

//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------- for lode map to service----------



import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.entity.Map_entity;

public class LoadMapService extends IntentService {
    public GoogleMap mMap;


    public LoadMapService() {
        super("LoadMapService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(Const.TAG, "onHandleIntent startService-----------");
        Log.e(Const.TAG, "Latitude----" + intent.getStringExtra("Latitude"));
        Log.e(Const.TAG, intent.getStringExtra("Longitude"));
        Log.e(Const.TAG, intent.getStringExtra("zooming"));
        double lat = Double.parseDouble(intent.getStringExtra("Latitude"));
        double lng = Double.parseDouble(intent.getStringExtra("Longitude"));
        float zooming = Float.parseFloat((intent.getStringExtra("zooming")));
        Bundle data = intent.getExtras();
        Map_entity map = (Map_entity) data.getParcelable("googlemap");
        mMap = map.getGoogleMap();

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zooming);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(zoom);
        Log.e(Const.TAG, "EndService-----------");

    }

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
