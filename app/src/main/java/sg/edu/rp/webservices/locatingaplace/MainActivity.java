package sg.edu.rp.webservices.locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    private GoogleMap map;
    String[] locationItems = { "North", "Central", "East"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.location_spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,locationItems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_Admiralty= new LatLng(1.44464, 103.786263);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Admiralty, 15));

                Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Admiralty)
                        .title("HQ North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm Tel:65433456")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                LatLng poi_Orchard = new LatLng(1.33224, 103.715593);
                Marker central = map.addMarker(new MarkerOptions()
                        .position(poi_Orchard)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 Operating hours: 11am-8pm Tel:67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_Tampines = new LatLng(1.44224, 103.886644);
                Marker east = map.addMarker(new MarkerOptions()
                        .position(poi_Tampines)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788 Operating hours: 9am-5pm Tel:66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
                else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),locationItems[position] , Toast.LENGTH_LONG).show();

        if (position == 0) {
            LatLng poi_Admiralty= new LatLng(1.44464, 103.786263);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Admiralty, 20));
        }

        else if (position == 1) {
            LatLng poi_Orchard = new LatLng(1.33224, 103.715593);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Orchard, 15));
        }

        else {
            LatLng poi_Tampines = new LatLng(1.44224, 103.886644);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Tampines, 15));
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}