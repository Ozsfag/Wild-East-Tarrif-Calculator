package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;

public interface DistancePriceProvider {
  Price costPerDistance();
}
