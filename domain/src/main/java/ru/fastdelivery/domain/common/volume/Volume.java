package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Volume(BigDecimal volumeMillimeters) implements Comparable<Volume> {
  public Volume {
    if (isLessThanZero(volumeMillimeters)) {
      throw new IllegalArgumentException("Volume cannot be below Zero!");
    }
  }

  private static boolean isLessThanZero(BigDecimal price) {
    return BigDecimal.ZERO.compareTo(price) > 0;
  }

  public static Volume zero() {
    return new Volume(BigDecimal.ZERO);
  }

  public BigDecimal cubicMeters() {
    return volumeMillimeters.divide(BigDecimal.valueOf(1_000_000_000), 100, RoundingMode.HALF_UP);
  }

  public Volume add(Volume additionalVolume) {
    return new Volume(this.volumeMillimeters.add(additionalVolume.volumeMillimeters));
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Volume volume = (Volume) o;
    return volumeMillimeters.compareTo(volume.volumeMillimeters) == 0;
  }

  @Override
  public int compareTo(Volume v) {
    return v.volumeMillimeters().compareTo(volumeMillimeters());
  }

  public boolean greaterThan(Volume v) {
    return volumeMillimeters().compareTo(v.volumeMillimeters) > 0;
  }
}
