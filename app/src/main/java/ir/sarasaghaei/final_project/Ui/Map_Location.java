package ir.sarasaghaei.final_project.Ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Service;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;

public class Map_Location extends FragmentActivity implements OnMapReadyCallback, LocationListener {


    GoogleMap mMap;
    LocationManager locationManager;
    Location curent_location;
    Double lng = Double.valueOf(0);
    Double lat = Double.valueOf(0);
    FloatingActionButton floatingbtnadd, flotingzoomin, flotingzoomout;
    String type = "2D";
    Float zooming = Float.valueOf(13);
    TextView sharing,btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__location);


        //---- for exit app
        exit.activityList.add(this);

        Log.e(Const.TAG, "onCreate-----------");
        //-----Check internet statuse
        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        init();
        setsetting();
    }

    private void init() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        floatingbtnadd = findViewById(R.id.floting3d);
        flotingzoomin = findViewById(R.id.flotingzoomin);
        flotingzoomout = findViewById(R.id.flotingzoomout);
        sharing = findViewById(R.id.sharing);
        btnback = findViewById(R.id.btnback);
        sharing.setVisibility(View.INVISIBLE);

        floatingbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatingbtnadd.getTag().equals("3D")) {
                    type = "3D";
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    floatingbtnadd.setTag("2D");
                    floatingbtnadd.setImageResource(R.drawable.ic_2d);
                } else {
                    type = "2D";
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    floatingbtnadd.setTag("3D");
                    floatingbtnadd.setImageResource(R.drawable.ic_baseline_3d_rotation_24);
                }

            }
        });
        flotingzoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zooming += 1;
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(zooming);
                mMap.animateCamera(zoom);
                Log.e(Const.TAG, "zooming" + zooming);
            }
        });
        flotingzoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Const.TAG, "zooming" + zooming);
                zooming -= 1;
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(zooming);
                mMap.animateCamera(zoom);
            }
        });

        //----------------- back and exit in activity-------------
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(Const.TAG, "onStart-----------");
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Boolean BeenPermission = askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            if (BeenPermission) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 2f, (LocationListener) this);
                curent_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lat = curent_location.getLatitude();
                lng = curent_location.getLongitude();
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 2f, (LocationListener) this);
            curent_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = curent_location.getLatitude();
            lng = curent_location.getLongitude();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Const.TAG, "onDestroy-----------");
        locationManager.removeUpdates((LocationListener) this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zooming);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(zoom);
        Log.e(Const.TAG, "EndService-----------");


        //------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------- for lode map to service----------

       /* Log.e(Const.TAG, "onMapReady-----------") ;
        Intent intent = new Intent(this, map.class);
        Log.e(Const.TAG, "lat" + lat) ;
        Log.e(Const.TAG, "lng" + lng) ;
        Log.e(Const.TAG, "zooming" + zooming);
        intent.putExtra("Latitude", lat.toString());
        intent.putExtra("Longitude", lng.toString());
        intent.putExtra("zooming", zooming.toString());
        Map_entity map_entety = new Map_entity();
        map_entety.setGoogleMap(mMap);
        intent.putExtra("googlemap", map_entety);

        Log.e(Const.TAG, "startService-----------") ;
        startService(intent);
        LatLng sydney = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.e(Const.TAG, "onLocationChanged-----------");
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double alte = location.getAltitude();
        String your_location = lng + "\n" + lat + "\n" + alte;

        //for update map
        LatLng sydney = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zooming);
        mMap.animateCamera(zoom);
        Log.e(Const.TAG, "update map-----------");

        Log.e(Const.TAG, "onLocationChanged :     " + your_location);
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        Boolean BeenPermission = false;
        Log.e(Const.TAG, "checkSelfPermission ...............");
        if (ActivityCompat.checkSelfPermission(this, permission) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            Log.e(Const.TAG, "requestPermissions ...............");
        } else {
            BeenPermission = true;
        }
        return BeenPermission;
    }

    private void setsetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains("Backgroundcolor")) {
            String Backgroundcolor = sharedPreferences.getString("Backgroundcolor", "");
            if (Backgroundcolor != "") {
                View whole_layout = findViewById(R.id.base_layout);
                whole_layout.setBackgroundColor(Color.parseColor(Backgroundcolor));
            }
        }
        if (sharedPreferences.contains("Toolbarcolor")) {
            String Toolbarcolor = sharedPreferences.getString("Toolbarcolor", "");
            if (Toolbarcolor != "") {
                View drawer_layout = findViewById(R.id.layou_toolbar);
                drawer_layout.setBackgroundColor(Color.parseColor(Toolbarcolor));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.setStatusBarColor(Color.parseColor(Toolbarcolor));
                    window.setNavigationBarColor(Color.parseColor(Toolbarcolor));
                }
            }
        }

    }
}