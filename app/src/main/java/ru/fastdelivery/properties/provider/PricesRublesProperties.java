package ru.fastdelivery.properties.provider;

import java.math.BigDecimal;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.DistancePriceProvider;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/** Настройки базовых цен стоимости перевозки из конфига */
@ConfigurationProperties("cost.rub")
@Setter
public class PricesRublesProperties
    implements WeightPriceProvider, VolumePriceProvider, DistancePriceProvider {

  private BigDecimal perKg;
  private BigDecimal minimal;
  private BigDecimal perCubicMeter;
  private BigDecimal perDistance;

  @Autowired private CurrencyFactory currencyFactory;

  @Override
  public Price costPerKg() {
    return new Price(perKg, currencyFactory.create("RUB"));
  }

  @Override
  public Price minimalPrice() {
    return new Price(minimal, currencyFactory.create("RUB"));
  }

  @Override
  public Price costPerCubicMeter() {
    return new Price(perCubicMeter, currencyFactory.create("RUB"));
  }

  @Override
  public Price costPerDistance() {
    return new Price(perDistance, currencyFactory.create("RUB"));
  }
}
