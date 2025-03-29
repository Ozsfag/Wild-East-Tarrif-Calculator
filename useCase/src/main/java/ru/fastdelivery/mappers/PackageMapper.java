package ru.fastdelivery.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.request.CargoPackageRequest;

@Mapper
public interface PackageMapper {
  PackageMapper INSTANCE = Mappers.getMapper(PackageMapper.class);

  @Mappings({
    @Mapping(target = "weight", source = "request.weight"),
    @Mapping(target = "volume", qualifiedByName = "getVolumeFromDimensions", source = "request")
  })
  Pack cargoPackageRequestToPack(CalculatePackagesRequest request);

  @Named("getVolumeFromDimensions")
  default Volume getVolumeFromDimensions(CargoPackageRequest cargoPackageRequest) {
    var length = cargoPackageRequest.length();
    var width = cargoPackageRequest.width();
    var height = cargoPackageRequest.height();
    return new Volume(length.multiply(width).multiply(height));
  }
}
