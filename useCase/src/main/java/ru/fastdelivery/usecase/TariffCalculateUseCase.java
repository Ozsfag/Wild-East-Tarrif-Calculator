package ru.fastdelivery.usecase;

import java.math.BigDecimal;
import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Named
@RequiredArgsConstructor
@Slf4j
public class TariffCalculateUseCase {
  private final WeightPriceProvider weightPriceProvider;
  private final VolumePriceProvider volumePriceProvider;
  private final DistancePriceProvider distancePriceProvider;

  public Price calculateTariff(Shipment shipment, Coordinate departure, Coordinate destination) {
    var maxPriceByWeight = getMaxPriceByWeight(shipment);
    var maxPriceByVolume = getMaxPriceByVolume(shipment);
    var basePrice = maxPriceByWeight.max(maxPriceByVolume);
    var distance = getDistanceByCoordinates(departure, destination);
    var distanceCoefficient = getDistanceCoefficient(distance);
    return basePrice.multiply(BigDecimal.valueOf(distanceCoefficient));
  }

  public Price getMaxPriceByWeight(Shipment shipment) {
    var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
    var minimalPrice = weightPriceProvider.minimalPrice();
    return weightPriceProvider.costPerKg().multiply(weightAllPackagesKg).max(minimalPrice);
  }

  public Price minimalPrice() {
    return weightPriceProvider.minimalPrice();
  }

  public Price getMaxPriceByVolume(Shipment shipment) {
    var volumeAllPackagesMm = shipment.volumeAllPackages().toCubicMeters();
    return volumePriceProvider.costPerCubicMeter().multiply(volumeAllPackagesMm);
  }

  @SneakyThrows
  public double getDistanceByCoordinates(Coordinate departure, Coordinate destination) {
    double originLat = departure.latitude();
    double originLng = departure.longitude();
    double destinationLat = destination.latitude();
    double destinationLng = destination.longitude();
    return OrthodromeCalculator.calculateDistanceByCoordinates(
        originLat, originLng, destinationLat, destinationLng);
  }

  public double getDistanceCoefficient(double distance) {
    var minDistance = distancePriceProvider.costPerDistance().amount().doubleValue();

    return distance > minDistance ? (distance / minDistance) : 1;
  }
}
