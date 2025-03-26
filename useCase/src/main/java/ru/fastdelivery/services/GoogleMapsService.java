package ru.fastdelivery.services;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

public class GoogleMapsService {

  private final GeoApiContext context;

  public GoogleMapsService(String apiKey) {
    this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
  }

  public long calculateDistanceByCoordinates(
      double originLat, double originLng, double destinationLat, double destinationLng)
      throws Exception {
    LatLng origin = new LatLng(originLat, originLng);
    LatLng destination = new LatLng(destinationLat, destinationLng);

    DistanceMatrix result =
        DistanceMatrixApi.newRequest(context)
            .origins(origin)
            .destinations(destination)
            .mode(TravelMode.DRIVING)
            .await();

    return result.rows[0].elements[0].distance.inMeters;
  }
}
