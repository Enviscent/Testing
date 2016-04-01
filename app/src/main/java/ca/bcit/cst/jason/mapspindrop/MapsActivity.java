package ca.bcit.cst.jason.mapspindrop;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final LocationListener mLocationListener = new LocationListener() {
        public void onProviderEnabled(String s){

        }
        public void onProviderDisabled(String s){

        }
        public void onStatusChanged(String s, int i, Bundle b){

        }
        @Override
        public void onLocationChanged(final Location location) {
            Location l = getLastBestLocation();
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

            // Add a marker in Sydney and move the camera
            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("test"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 10));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    try{
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, mLocationListener);
    } catch (SecurityException e){

    }

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
        mMap = googleMap;
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e){

        }
    }
    private Location getLastBestLocation() {
        LocationManager m = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location locationGPS = m.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = m.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            long GPSLocationTime = 0;
            if (null != locationGPS) {
                GPSLocationTime = locationGPS.getTime();
            }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }

            if (0 < GPSLocationTime - NetLocationTime) {
                return locationGPS;
            } else {
                return locationNet;
            }
        } catch (SecurityException e) {

        }
        return null;
    }
}
