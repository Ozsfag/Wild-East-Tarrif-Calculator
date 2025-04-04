package ru.fastdelivery.calc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.request.CargoPackageRequest;
import ru.fastdelivery.presentation.model.request.CoordinateRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

class CalculateControllerTest extends ControllerTest {

  final String baseCalculateApi = "/api/v1/calculate";
  @MockBean TariffCalculateUseCase useCase;
  @MockBean CurrencyFactory currencyFactory;

  @Test
  @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
  void whenValidInputData_thenReturn200() {
    var request =
        new CalculatePackagesRequest(
            List.of(
                new CargoPackageRequest(
                    BigInteger.TEN, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN)),
            "RUB",
            new CoordinateRequest(55.0, 55.0),
            new CoordinateRequest(56.0, 56.0));
    var rub = new CurrencyFactory(code -> true).create("RUB");
    when(useCase.calculateTariff(any(), any(), any()))
        .thenReturn(new Price(BigDecimal.valueOf(10), rub));
    when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));

    ResponseEntity<CalculatePackagesResponse> response =
        restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  @DisplayName("Список упаковок == null -> Ответ 400")
  void whenEmptyListPackages_thenReturn400() {
    var request =
        new CalculatePackagesRequest(
            null, "RUB", new CoordinateRequest(55.0, 55.0), new CoordinateRequest(56.0, 56.0));

    ResponseEntity<String> response =
        restTemplate.postForEntity(baseCalculateApi, request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
