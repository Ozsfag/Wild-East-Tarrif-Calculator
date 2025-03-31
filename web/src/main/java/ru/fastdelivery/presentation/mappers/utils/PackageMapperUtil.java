package ru.fastdelivery.presentation.mappers.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.presentation.model.request.CargoPackageRequest;

/** Utility class for mapping package-related data. */
@Named("PackageMapperUtil")
@Component
public class PackageMapperUtil {

  /**
   * Calculates the total volume of a cargo package.
   *
   * @param cargoPackageRequest the request containing package dimensions
   * @return the calculated volume
   */
  @Named("calculateTotalVolume")
  public Volume calculateTotalVolume(CargoPackageRequest cargoPackageRequest) {
    BigDecimal volumeValue =
        cargoPackageRequest
            .length()
            .multiply(cargoPackageRequest.width())
            .multiply(cargoPackageRequest.height())
            .setScale(4, RoundingMode.HALF_UP);
    return new Volume(volumeValue);
  }

  /**
   * Converts BigInteger to Weight.
   *
   * @param weightValue the weight in BigInteger
   * @return the Weight object
   */
  @Named("bigIntegerToWeight")
  public Weight bigIntegerToWeight(BigInteger weightValue) {
    return new Weight(weightValue);
  }
}
