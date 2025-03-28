package ru.fastdelivery.domain.common.weight;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Общий класс веса
 *
 * @param weightGrams вес в граммах
 */
public record Weight(BigInteger weightGrams) implements Comparable<Weight> {

  public static Weight zero() {
    return new Weight(BigInteger.ZERO);
  }

  public BigDecimal kilograms() {
    return new BigDecimal(weightGrams).divide(BigDecimal.valueOf(1000), 4, RoundingMode.HALF_UP);
  }

  public Weight add(Weight additionalWeight) {
    return new Weight(this.weightGrams.add(additionalWeight.weightGrams));
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Weight weight = (Weight) o;
    return weightGrams.compareTo(weight.weightGrams) == 0;
  }

  @Override
  public int compareTo(Weight w) {
    return w.weightGrams().compareTo(weightGrams());
  }
}
