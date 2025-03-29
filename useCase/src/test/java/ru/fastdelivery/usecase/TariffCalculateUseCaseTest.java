package ru.fastdelivery.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

class TariffCalculateUseCaseTest {

  final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
  final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
  final DistancePriceProvider distancePriceProvider = mock(DistancePriceProvider.class);
  final Currency currency = new CurrencyFactory(code -> true).create("RUB");

  final TariffCalculateUseCase tariffCalculateUseCase =
      new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, distancePriceProvider);

  @Test
  @DisplayName("Расчет стоимости доставки -> успешно")
  void whenCalculatePrice_thenSuccess() {
    var minimalPrice = new Price(BigDecimal.TEN, currency);
    var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
    var pricePerCubicMeter = new Price(BigDecimal.valueOf(500), currency);
    var pricePerDistance = new Price(BigDecimal.valueOf(450), currency);

    when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
    when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
    when(volumePriceProvider.costPerCubicMeter()).thenReturn(pricePerCubicMeter);
    when(distancePriceProvider.costPerDistance()).thenReturn(pricePerDistance);

    var shipment =
        new Shipment(
            List.of(
                new Pack(
                    new Weight(BigInteger.valueOf(1200)), new Volume(BigDecimal.valueOf(1500)))),
            new CurrencyFactory(code -> true).create("RUB"));
    var departure = new Coordinate(55.755826, 37.617288);
    var destination = new Coordinate(56.755826, 38.617288);
    var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

    var actualPrice = tariffCalculateUseCase.calculateTariff(shipment, departure, destination);

    assertThat(actualPrice)
        .usingRecursiveComparison()
        .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
        .isEqualTo(expectedPrice);
  }

  @Test
  @DisplayName("Получение минимальной стоимости -> успешно")
  void whenMinimalPrice_thenSuccess() {
    BigDecimal minimalValue = BigDecimal.TEN;
    var minimalPrice = new Price(minimalValue, currency);
    when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

    var actual = tariffCalculateUseCase.minimalPrice();

    assertThat(actual).isEqualTo(minimalPrice);
  }
}
