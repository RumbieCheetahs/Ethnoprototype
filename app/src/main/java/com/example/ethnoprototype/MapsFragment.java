package com.example.ethnoprototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.CategoryAssignedResource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
//        MapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        List<LatLng> locations = new ArrayList<>();
        List<CategoryAssignedResource> resourceList = AppDatabase.getAppDatabase(MapsFragment.this).assignedResourceDAO().getAll();
        if(resourceList.size() != 0) {
            for (CategoryAssignedResource resource : resourceList) {
                LatLng latLng = new LatLng(resource.videoId.latitude, resource.videoId.longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(""+resource.plantName)).showInfoWindow();
                locations.add(latLng);
            }
        }
//        LatLng sydney = locations.get(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locations.get(0)));
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_maps, container, false);
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(callback);
//        }
//    }
}