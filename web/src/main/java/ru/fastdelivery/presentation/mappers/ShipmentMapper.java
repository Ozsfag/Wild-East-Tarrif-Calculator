package ru.fastdelivery.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.mappers.utils.ShipmentMapperUtil;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;

@Mapper(
    componentModel = "spring",
    uses = {PackageMapper.class, ShipmentMapperUtil.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentMapper {

  @Mappings({
    @Mapping(
        target = "packages",
        qualifiedByName = {"PackageMapper", "cargoPackageRequestListToPackList"},
        source = "request.packages"),
    @Mapping(
        target = "currency",
        qualifiedByName = {"ShipmentMapperUtil", "getCurrencyFromCurrencyCode"},
        source = "request.currencyCode")
  })
  Shipment calculatePackageRequestToShipment(CalculatePackagesRequest request);
}
