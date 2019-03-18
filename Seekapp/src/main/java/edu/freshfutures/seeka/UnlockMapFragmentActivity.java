package edu.freshfutures.seeka;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UnlockMapFragmentActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng ROYALROADS = new LatLng(48.4321302, -123.4783257);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(48.4321302, -123.4783257))
                .title("Royal Roads"));


        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(new LatLng(48.4321302, -123.4783257), 2));
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}
