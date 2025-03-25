package ru.fastdelivery.domain.delivery.shipment;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

class ShipmentTest {

  @Test
  void whenSummarizingWeightOfAllPackages_thenReturnSum() {
    var weight1 = new Weight(BigInteger.TEN);
    var weight2 = new Weight(BigInteger.ONE);
    var volume = new Volume(BigDecimal.valueOf(1000));

    var packages = List.of(new Pack(weight1, volume), new Pack(weight2, volume));
    var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

    var massOfShipment = shipment.weightAllPackages();

    assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
  }
}
