package com.example.skripsigre;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.skripsigre.api.ApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.skripsigre.api.RoutesApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RuteActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute);
        // Obtain the SupportMapFragment and get notified when the map is ready to be
        // used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available. This callback is triggered when the map
     * is ready to be used. This is where we can add markers or lines, add listeners
     * or move the camera. In this case, we just add a marker near Sydney,
     * Australia. If Google Play services is not installed on the device, the user
     * will be prompted to install it inside the SupportMapFragment. This method
     * will only be triggered once the user has installed Google Play services and
     * returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Jalur> listJalur = new ArrayList<>();
        Gson gson = new Gson();
        Retrofit retrofit = ApiClient.getClient();

        RoutesApi routesApi = retrofit.create(RoutesApi.class);
        Call<List<Routes>> call = routesApi.getRoutes();
        call.enqueue(new Callback<List<Routes>>() {
            @Override
            public void onResponse(Call<List<Routes>> call, Response<List<Routes>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error", String.valueOf(response.code()));
                } else {
                    // Add all routes to listJalur
                    List<Routes> routes = response.body();
                    for (Routes route : routes) {
                        Jalur jalur = gson.fromJson(route.getJalur(), Jalur.class);
                        listJalur.add(jalur);
                    }

                    // Display all routes
                    for (Jalur jalur : listJalur) {
                        List<List<Double>> coordinates = jalur.getCoordinates();
                        for (List<Double> coordinate : coordinates) {
                            System.out.println(coordinate);
                            LatLng location = new LatLng(coordinate.get(0), coordinate.get(1));
                            googleMap.addMarker(new MarkerOptions().position(location));
                        }
                    }
                    LatLng cimahi = new LatLng(-6.8799376, 107.5611123);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cimahi, 13));
                }
            }

            @Override
            public void onFailure(Call<List<Routes>> call, Throwable t) {
                Log.e("Error", t.getMessage());

                // Add a marker in Sydney and move the camera
                LatLng cimahi = new LatLng(-6.8799376, 107.5611123);
                googleMap.addMarker(new MarkerOptions().position(cimahi).title("rmah greg"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cimahi, 13));
            }
        });
    }
}