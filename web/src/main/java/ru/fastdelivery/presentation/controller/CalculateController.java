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
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.presentation.services.CalculatorService;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
  private final CalculatorService calculatorService;

  @PostMapping
  @Operation(summary = "Расчет стоимости по упаковкам груза")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
      })
  public CalculatePackagesResponse calculate(@Valid @RequestBody CalculatePackagesRequest request) {
    return calculatorService.calculate(request);
  }
}
