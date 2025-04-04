package ru.fastdelivery.domain.delivery.shipment;

import java.util.List;
import lombok.Builder;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
@Builder
public record Shipment(List<Pack> packages, Currency currency) {
  public Weight weightAllPackages() {
    return packages.stream().map(Pack::weight).reduce(Weight.zero(), Weight::add);
  }

  public Volume volumeAllPackages() {
    return packages.stream().map(Pack::volume).reduce(Volume.zero(), Volume::add);
  }
}
