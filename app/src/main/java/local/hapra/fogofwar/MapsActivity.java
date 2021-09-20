package local.hapra.fogofwar;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import local.hapra.fogofwar.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //GitHub Token: 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

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private GoogleMap map;
    private ActivityMapsBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

        // Prüfen ob Erlaubniss zur Ortung erteilt wurde
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }




    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>(){

            public void onSuccess(final Location location){

                if(location != null){
                    // Sync Map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            // Initialize lat Lng
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            // Create Marker
                            MarkerOptions options = new MarkerOptions().position(latLng).title("hier i AM");
                            // Zoom Map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            // Add Marker
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode==44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // When Permission garanted
                // Call method
                getCurrentLocation();
            }
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
        map = googleMap;

        LatLng southWest = new LatLng(45.4630647,-112.178804986);
        LatLng northEast = new LatLng(45.631394227,-111.879379778);
        LatLngBounds latLngBounds = new LatLngBounds(southWest,northEast);

        // Add a marker in Sydney and move the camera
        LatLng oberesSchlossSiegen = new LatLng(50.8756, 8.0301);
        map.addMarker(new MarkerOptions().position(oberesSchlossSiegen));
        map.addMarker(new MarkerOptions().position(oberesSchlossSiegen).title("Oberes Schloss Siegen"));

        //Zoom to your Location
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(oberesSchlossSiegen,16));
        //Disable Zoom
        map.getUiSettings().setZoomGesturesEnabled(false);
        //Disable dragging
        map.getUiSettings().setScrollGesturesEnabled(false);
        //Disable Rotating
        map.getUiSettings().setRotateGesturesEnabled(false);

        /*
        String cameraPosition;
        cameraPosition= String.valueOf(map.getCameraPosition());
        System.out.printf(cameraPosition);
        */




        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.raw.katzenbild);

        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        groundOverlayOptions.position(oberesSchlossSiegen,500f,500f);
        groundOverlayOptions.image(bitmapDescriptor);
        groundOverlayOptions.transparency(0f);
        map.addGroundOverlay(groundOverlayOptions);
        /*
        Alternativ funktioniert aber nicht, App stürzt ab
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.raw.katzenbild))
                .positionFromBounds(latLngBounds)
                .transparency(0.5f);
        mMap.addGroundOverlay(newarkMap);
        */
    }
    private float x;
    private float y;
    public void addPOI (){

    }

}