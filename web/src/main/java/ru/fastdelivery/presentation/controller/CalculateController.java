package ru.fastdelivery.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    var packsWeights =
        request.packages().stream()
            .map(
                cargoPackage -> {
                  var weight = new Weight(cargoPackage.weight());
                  var length = cargoPackage.length();
                  var width = cargoPackage.width();
                  var height = cargoPackage.height();
                  var cubicMillimeters = length.multiply(width).multiply(height);
                  var volume = new Volume(cubicMillimeters);
                  return new Pack(weight, volume);
                })
            .toList();
    var departureRequest = request.departure();
    var destinationRequest = request.destination();
    var departureDto = CoordinateRequest.mapTo(departureRequest);
    var destinationDto = CoordinateRequest.mapTo(destinationRequest);
    var shipment = new Shipment(packsWeights, currencyFactory.create(request.currencyCode()));
    var calculatedPrice = tariffCalculateUseCase.calc(shipment, departureDto, destinationDto);
    var minimalPrice = tariffCalculateUseCase.minimalPrice();
    return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
  }
}
