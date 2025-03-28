package ru.fastdelivery.domain.common.weight;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WeightFactoryTest {

  @ParameterizedTest(name = "Граммы = {arguments} -> объект создан")
  @ValueSource(longs = {0, 1, 100, 10_000})
  void whenGramsGreaterThanZero_thenObjectCreated(long amount) {
    var weight = new Weight(BigInteger.valueOf(amount));

    assertNotNull(weight);
    assertThat(weight.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(amount));
  }
}
