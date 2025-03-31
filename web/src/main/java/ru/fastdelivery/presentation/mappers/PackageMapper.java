package ru.fastdelivery.presentation.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.presentation.mappers.utils.PackageMapperUtil;
import ru.fastdelivery.presentation.model.request.CargoPackageRequest;

@Mapper(
    componentModel = "spring",
    uses = {PackageMapperUtil.class})
@Named("PackageMapper")
public interface PackageMapper {

  @Mappings({
    @Mapping(target = "weight", qualifiedByName = "bigIntegerToWeight", source = "request.weight"),
    @Mapping(target = "volume", qualifiedByName = "calculateTotalVolume", source = "request")
  })
  @Named("cargoPackageRequestToPack")
  Pack cargoPackageRequestToPack(CargoPackageRequest request);

  @Named("cargoPackageRequestListToPackList")
  default List<Pack> cargoPackageRequestListToPackList(List<CargoPackageRequest> requests) {
    return requests.stream().map(this::cargoPackageRequestToPack).toList();
  }
}
