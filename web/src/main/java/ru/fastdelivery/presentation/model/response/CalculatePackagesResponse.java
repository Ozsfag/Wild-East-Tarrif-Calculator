package ru.fastdelivery.presentation.model.response;

import java.math.BigDecimal;
import ru.fastdelivery.domain.common.price.Price;

public record CalculatePackagesResponse(
    BigDecimal totalPrice, BigDecimal minimalPrice, String currencyCode) {
  public CalculatePackagesResponse(Price totalPrice, Price minimalPrice) {
    this(totalPrice.amount(), minimalPrice.amount(), totalPrice.currency().getCode());

    if (currencyIsNotEqual(totalPrice, minimalPrice)) {
      throw new IllegalArgumentException("Currency codes must be the same");
    }
  }

  private static boolean currencyIsNotEqual(Price priceLeft, Price priceRight) {
    return !priceLeft.currency().equals(priceRight.currency());
  }
}
