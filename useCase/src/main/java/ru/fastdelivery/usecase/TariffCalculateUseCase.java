package ru.fastdelivery.usecase;

import javax.inject.Named;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
  private final WeightPriceProvider weightPriceProvider;
  private final VolumePriceProvider volumePriceProvider;

  public Price calc(Shipment shipment) {
    var maxPriceByWeight = getMaxPriceByWeight(shipment);
    var maxPriceByVolume = getMaxPriceByVolume(shipment);
    return maxPriceByWeight.max(maxPriceByVolume);
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
    var volumeAllPackagesMm = shipment.volumeAllPackages().cubicMeters();
    return volumePriceProvider.costPerCubicMeter().multiply(volumeAllPackagesMm);
  }
}
