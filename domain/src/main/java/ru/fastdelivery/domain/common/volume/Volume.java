package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a volume measurement in cubic millimeters.
 */
public record Volume(BigDecimal cubicMillimeters) implements Comparable<Volume> {

  public Volume {
    if (isNegative(cubicMillimeters)) {
      throw new IllegalArgumentException("Volume cannot be below zero!");
    }
  }

  private static boolean isNegative(BigDecimal volume) {
    return BigDecimal.ZERO.compareTo(volume) > 0;
  }

  public static Volume zero() {
    return new Volume(BigDecimal.ZERO);
  }

  /**
   * Converts the volume from cubic millimeters to cubic meters with a precision of four decimal places.
   *
   * @return the volume in cubic meters
   */
  public BigDecimal toCubicMeters() {
    return cubicMillimeters.divide(BigDecimal.valueOf(1_000_000_000), 4, RoundingMode.HALF_UP);
  }

  public Volume add(Volume additionalVolume) {
    return new Volume(this.cubicMillimeters.add(additionalVolume.cubicMillimeters));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Volume otherVolume = (Volume) obj;
    return cubicMillimeters.compareTo(otherVolume.cubicMillimeters) == 0;
  }

  @Override
  public int compareTo(Volume otherVolume) {
    return this.cubicMillimeters.compareTo(otherVolume.cubicMillimeters);
  }

  public boolean isGreaterThan(Volume otherVolume) {
    return this.cubicMillimeters.compareTo(otherVolume.cubicMillimeters) > 0;
  }
}