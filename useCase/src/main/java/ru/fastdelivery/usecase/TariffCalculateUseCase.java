package ru.fastdelivery.usecase;

import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.model.request.Coordinate;
import ru.fastdelivery.services.GoogleMapsService;

import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
  private final WeightPriceProvider weightPriceProvider;
  private final VolumePriceProvider volumePriceProvider;
  private final GoogleMapsService googleMapsService;

  public Price calc(Shipment shipment, Coordinate departure, Coordinate destination) {
    var maxPriceByWeight = getMaxPriceByWeight(shipment);
    var maxPriceByVolume = getMaxPriceByVolume(shipment);
    var basePrice = maxPriceByWeight.max(maxPriceByVolume);
    var distance = getDistanceByCoordinates(departure, destination);
    var distanceCoefficient = distance > 450 ? (distance / 450) : 1;
    var deliveryPrice = basePrice.multiply(BigDecimal.valueOf(distanceCoefficient));
    return deliveryPrice;
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
  public long getDistanceByCoordinates(Coordinate departure, Coordinate destination) {
    double originLat = departure.latitude();
    double originLng = departure.longitude();
    double destinationLat = destination.latitude();
    double destinationLng = destination.longitude();
    return googleMapsService.calculateDistanceByCoordinates(
        originLat, originLng, destinationLat, destinationLng);
  }
}
