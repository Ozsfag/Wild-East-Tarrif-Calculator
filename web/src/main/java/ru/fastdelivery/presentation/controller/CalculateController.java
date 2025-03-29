package ru.fastdelivery.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.mappers.CoordinateMapper;
import ru.fastdelivery.mappers.PackageMapper;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
  private final TariffCalculateUseCase tariffCalculateUseCase;
  private final CurrencyFactory currencyFactory;
  private final PackageMapper packageMapper = PackageMapper.INSTANCE;
  private final CoordinateMapper coordinateMapper = CoordinateMapper.INSTANCE;

  @PostMapping
  @Operation(summary = "Расчет стоимости по упаковкам груза")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
      })
  public CalculatePackagesResponse calculate(@Valid @RequestBody CalculatePackagesRequest request) {
    List<Pack> packs = mapToPacks(request);
    var shipment = createShipment(request, packs);
    var calculatedPrice =
        tariffCalculateUseCase.calculateTariff(
            shipment,
            coordinateMapper.coordinateRequestToCoordinate(request.departure()),
            coordinateMapper.coordinateRequestToCoordinate(request.destination()));
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
  }

  private Shipment createShipment(CalculatePackagesRequest request, List<Pack> packs) {
    return new Shipment(packs, currencyFactory.create(request.currencyCode()));
  }
}
