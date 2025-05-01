package com.example.androidgooglemap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

  private GoogleMap myMap;

  Button zoomIn, zoomOut;

  private final LatLng bmuLatLng = new LatLng(28.24749740961875, 76.81450398233812);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    zoomIn = findViewById(R.id.btn_zoom_in);
    zoomOut = findViewById(R.id.btn_zoom_out);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
      R.id.map);
    mapFragment.getMapAsync(this);


    zoomIn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bmuLatLng, 15.0f), 3000, null);
//        myMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f), 3000, null);
      }
    });

    zoomOut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bmuLatLng, 1.0f), 3000, null);
//        myMap.animateCamera(CameraUpdateFactory.zoomTo(1.0f), 3000, null);
      }
    });

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
      (v, insets) -> {
        Insets systemBars = insets.getInsets(
          WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right,
          systemBars.bottom);
        return insets;
      });
  }

  @Override
  public void onMapReady(@NonNull GoogleMap googleMap) {
    myMap = googleMap;

    myMap.addMarker(new MarkerOptions().position(bmuLatLng).title("BMU"));
    myMap.moveCamera(CameraUpdateFactory.newLatLng(bmuLatLng));
  }
}