package ru.fastdelivery.domain.delivery.pack;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

class PackTest {

  @Test
  void whenWeightLessThanMaxWeight_thenObjectCreated() {
    var actual =
        new Pack(new Weight(BigInteger.valueOf(1_000)), new Volume(BigDecimal.valueOf(150)));
    assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
  }
}
