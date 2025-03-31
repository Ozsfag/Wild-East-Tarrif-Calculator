package ru.fastdelivery.presentation.mappers.utils;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;

@Named("ShipmentMapperUtil")
@Component
@RequiredArgsConstructor
public class ShipmentMapperUtil {
  private final CurrencyFactory currencyFactory;

  @Named("getCurrencyFromCurrencyCode")
  public Currency getCurrencyFromCurrencyCode(String currencyCode) {
    return currencyFactory.create(currencyCode);
  }
}
