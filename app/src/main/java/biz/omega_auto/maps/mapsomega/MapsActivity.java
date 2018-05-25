package biz.omega_auto.maps.mapsomega;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AllListeners allListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        allListeners    = new AllListeners();

        Bundle inputParams = getIntent().getExtras();
        if (inputParams != null) {
            if (inputParams.size() > 0) {

                Bundle inputParams1c = inputParams.getBundle("params_1c");

                if (inputParams1c != null) {
                    MapSchema mapSchema = MapSchema.getInstance();

                    try {
                        mapSchema.loadInputParams(inputParams1c);
                    } catch (Exception exeption) {
                        Toast.makeText(this, mapSchema.exceptionString(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // подпишемся на события
        mMap.setOnMapClickListener(allListeners);
        mMap.setOnMapLongClickListener(allListeners);
        mMap.setOnMarkerClickListener(allListeners);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        MapSchema mapSchema = MapSchema.getInstance();
        mapSchema.view(mMap);


    }



    class AllListeners implements
            GoogleMap.OnMapClickListener,
            GoogleMap.OnMapLongClickListener,
            GoogleMap.OnMarkerClickListener
    {
        @Override
        public void onMapClick(LatLng latLng) {

        }

        @Override
        public void onMapLongClick(LatLng latLng) {

        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }
    }
}
