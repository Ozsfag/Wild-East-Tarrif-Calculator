package ru.fastdelivery.usecase;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrthodromeCalculator {

  public double calculateDistanceByCoordinates(
      double originLat, double originLng, double destinationLat, double destinationLng) {

    final double pi = Math.PI;
    final double earthRadiusKilometers = 6371.795;

    var originLatRad = originLat * (pi / 180);
    var originLngRad = originLng * (pi / 180);
    var destinationLatRad = destinationLat * (pi / 180);
    var destinationLngRad = destinationLng * (pi / 180);

    double cl1 = Math.cos(originLatRad);
    double cl2 = Math.cos(destinationLatRad);
    double sl1 = Math.sin(originLatRad);
    double sl2 = Math.sin(destinationLatRad);
    double deltaLng = destinationLngRad - originLngRad;
    double cdelta = Math.cos(deltaLng);
    double sdelta = Math.sin(deltaLng);

    double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
    double x = sl1 * sl2 + cl1 * cl2 * cdelta;
    double ad = Math.atan2(y, x);

    return earthRadiusKilometers * ad;
  }
}
