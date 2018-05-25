package biz.omega_auto.maps.mapsomega;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 21.03.2018.
 */


// Список всех объектов, которые
public class MapSchema {

    private static MapSchema instance;
    private String exceptionString;

    // Список отображаемых точек
    // List<MapPoint>  mapPointList;
    List<MarkerOptions> markerOptionsList;

    //Список отображаемых маршрутов
    // TODO

    private MapSchema(){
        markerOptionsList   = new ArrayList<MarkerOptions>();
    }

    public static MapSchema getInstance(){
        if (instance == null) {
            instance = new MapSchema();
        }

        return instance;
    }

    public void addMarker(double latitude, double longitude){
        markerOptionsList.add(new MarkerOptions().position(new LatLng(latitude, longitude)));
    }

    public void view(GoogleMap googleMap){
        // отобразить все объекты на карте.

        GoogleMap mMap = googleMap;
        LatLng cameraLat = null;

        for (MarkerOptions markerOptions: markerOptionsList) {
            mMap.addMarker(markerOptions);
            cameraLat    = markerOptions.getPosition();
        }

        if (cameraLat != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraLat, 12));
        }
    }

    public void loadInputParams(Bundle inputParams) throws Exception {

        // заполним массив маркеров из входящих параметров
        Bundle markers = inputParams.getBundle("markers");

        if (markers != null) {

            for (int i = 0; i < markers.size(); i++) {

                Bundle marker = markers.getBundle("marker" + Integer.toString(i));
                if (marker != null) {
                    if (!marker.isEmpty()) {
                        MapSchema mapSchema = MapSchema.getInstance();
                        try {
                            mapSchema.addMarker(Double.valueOf(marker.get("x").toString()), Double.valueOf(marker.get("y").toString()));
                        } catch (NumberFormatException exception) {
                            exceptionString = exception.toString();
                            throw new Exception(exceptionString);
                        } finally {

                        }

                    }
                }
            }
        } else {

        }
    }

    public String exceptionString(){
        return exceptionString;
    }
}
