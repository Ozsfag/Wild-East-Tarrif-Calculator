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
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.request.CoordinateRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
  private final TariffCalculateUseCase tariffCalculateUseCase;
  private final CurrencyFactory currencyFactory;

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
            CoordinateRequest.mapTo(request.departure()),
            CoordinateRequest.mapTo(request.destination()));
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
  }

  private List<Pack> mapToPacks(CalculatePackagesRequest request) {
    return request.packages().stream()
        .map(
            cargoPackage ->
                new Pack(
                    new Weight(cargoPackage.weight()),
                    new Volume(
                        cargoPackage
                            .length()
                            .multiply(cargoPackage.width())
                            .multiply(cargoPackage.height()))))
        .toList();
  }

  private Shipment createShipment(CalculatePackagesRequest request, List<Pack> packs) {
    return new Shipment(packs, currencyFactory.create(request.currencyCode()));
  }
}
