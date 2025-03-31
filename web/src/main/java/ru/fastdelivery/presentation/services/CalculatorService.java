package ru.fastdelivery.presentation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fastdelivery.presentation.mappers.CoordinateMapper;
import ru.fastdelivery.presentation.mappers.ShipmentMapper;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@Service
@RequiredArgsConstructor
public class CalculatorService {
  private final ShipmentMapper shipmentMapper;
  private final TariffCalculateUseCase tariffCalculateUseCase;
  private final CoordinateMapper coordinateMapper;

  public CalculatePackagesResponse calculate(CalculatePackagesRequest request) {
    var shipment = shipmentMapper.calculatePackageRequestToShipment(request);
    var calculatedPrice =
        tariffCalculateUseCase.calculateTariff(
            shipment,
            coordinateMapper.coordinateRequestToCoordinate(request.departure()),
            coordinateMapper.coordinateRequestToCoordinate(request.destination()));
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
  }
}
