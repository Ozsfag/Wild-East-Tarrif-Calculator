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

    double cOriginLat = Math.cos(originLatRad);
    double cDestinationLat = Math.cos(destinationLatRad);
    double sOriginLat = Math.sin(originLatRad);
    double sDestinationLat = Math.sin(destinationLatRad);
    double deltaLng = destinationLngRad - originLngRad;
    double cDelta = Math.cos(deltaLng);
    double sDelta = Math.sin(deltaLng);

    double y =
        Math.sqrt(
            Math.pow(cDestinationLat * sDelta, 2)
                + Math.pow(
                    cOriginLat * sDestinationLat - sOriginLat * cDestinationLat * cDelta, 2));
    double x = sOriginLat * sDestinationLat + cOriginLat * cDestinationLat * cDelta;
    double ad = Math.atan2(y, x);

    return earthRadiusKilometers * ad;
  }
}
