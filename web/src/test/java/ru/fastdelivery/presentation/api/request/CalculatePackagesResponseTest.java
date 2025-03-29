package ru.fastdelivery.presentation.api.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.controller.CalculateController;
import ru.fastdelivery.presentation.controller.GlobalExceptionHandler;
import ru.fastdelivery.presentation.model.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.model.request.CargoPackageRequest;
import ru.fastdelivery.presentation.model.request.CoordinateRequest;
import ru.fastdelivery.presentation.model.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

class CalculatePackagesResponseTest {

  @Test
  @DisplayName("Если валюты разные -> ошибка создания объекта")
  void whenCurrenciesAreNotEqual_thenException() {
    var calculatedPrice =
        new Price(new BigDecimal(100), new CurrencyFactory(code -> true).create("USD"));
    var minimalPrice =
        new Price(new BigDecimal(5), new CurrencyFactory(code -> true).create("RUB"));

    assertThrows(
        IllegalArgumentException.class,
        () -> new CalculatePackagesResponse(calculatedPrice, minimalPrice));
  }

  @Test
  @DisplayName("Если валюты одинаковые -> объект создан")
  void whenCurrenciesAreEqual_thenObjectCreated() {
    var usd = new CurrencyFactory(code -> true).create("USD");
    var calculatedPrice = new Price(new BigDecimal(100), usd);
    var minimalPrice = new Price(new BigDecimal(5), usd);

    var expected =
        new CalculatePackagesResponse(new BigDecimal(100), new BigDecimal(5), usd.getCode());

    var actual = new CalculatePackagesResponse(calculatedPrice, minimalPrice);

    assertThat(actual)
        .usingRecursiveComparison()
        .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
        .isEqualTo(expected);
  }

  @Test
  @DisplayName("Если в параметры передется пустой список упаковок -> выкидывается исключение.")
  public void whenEmptyPackagesList_thenThrowException() {
    TariffCalculateUseCase tariffCalculateUseCase = Mockito.mock(TariffCalculateUseCase.class);
    CurrencyFactory currencyFactory = Mockito.mock(CurrencyFactory.class);
    CalculateController calculateController =
        new CalculateController(tariffCalculateUseCase, currencyFactory);

    CoordinateRequest departure = new CoordinateRequest(55.755826, 37.617288);
    CoordinateRequest destination = new CoordinateRequest(59.939095, 30.315868);

    CalculatePackagesRequest request =
        new CalculatePackagesRequest(Collections.emptyList(), "RUB", departure, destination);

    MockMvc mockMvc =
        MockMvcBuilders.standaloneSetup(calculateController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    try {
      mockMvc
          .perform(
              MockMvcRequestBuilders.post("/api/v1/calculate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(request)))
          .andExpect(MockMvcResultMatchers.status().isBadRequest());

      Mockito.verify(tariffCalculateUseCase, Mockito.never())
          .calculateTariff(Mockito.any(), Mockito.any(), Mockito.any());
      Mockito.verify(tariffCalculateUseCase, Mockito.never()).minimalPrice();
      Mockito.verify(currencyFactory, Mockito.never()).create(Mockito.anyString());
    } catch (Exception e) {
      fail("Test failed with exception: " + e.getLocalizedMessage());
    }
  }

  @Test
  @DisplayName("Если в параметры передется null вместо списка упаковок -> выкидывается исключение.")
  public void whenNullPackagesList_thenThrowException() {
    TariffCalculateUseCase tariffCalculateUseCase = Mockito.mock(TariffCalculateUseCase.class);
    CurrencyFactory currencyFactory = Mockito.mock(CurrencyFactory.class);
    CalculateController calculateController =
        new CalculateController(tariffCalculateUseCase, currencyFactory);

    CoordinateRequest departure = new CoordinateRequest(55.755826, 37.617288);
    CoordinateRequest destination = new CoordinateRequest(59.939095, 30.315868);

    CalculatePackagesRequest request =
        new CalculatePackagesRequest(null, "RUB", departure, destination);

    MockMvc mockMvc =
        MockMvcBuilders.standaloneSetup(calculateController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

    try {
      mockMvc
          .perform(
              MockMvcRequestBuilders.post("/api/v1/calculate")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(request)))
          .andExpect(MockMvcResultMatchers.status().isBadRequest());

      Mockito.verify(tariffCalculateUseCase, Mockito.never())
          .calculateTariff(Mockito.any(), Mockito.any(), Mockito.any());
      Mockito.verify(tariffCalculateUseCase, Mockito.never()).minimalPrice();
      Mockito.verify(currencyFactory, Mockito.never()).create(Mockito.anyString());
    } catch (Exception e) {
      fail("Test failed with exception: " + e.getLocalizedMessage());
    }
  }

  @Test
  @DisplayName("Если в параметры передется null вместо координат -> выбрасывается исключение.")
  public void whenNullCoordinates_thenThrowException() {
    TariffCalculateUseCase tariffCalculateUseCase = Mockito.mock(TariffCalculateUseCase.class);
    CurrencyFactory currencyFactory = Mockito.mock(CurrencyFactory.class);
    CalculateController calculatorController =
        new CalculateController(tariffCalculateUseCase, currencyFactory);

    CargoPackageRequest packageRequest =
        new CargoPackageRequest(BigInteger.TEN, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN);

    CalculatePackagesRequest requestWithNullDeparture =
        new CalculatePackagesRequest(
            List.of(packageRequest), "RUB", null, new CoordinateRequest(59.939095, 30.315868));

    CalculatePackagesRequest requsetWithNullDestination =
        new CalculatePackagesRequest(
            List.of(packageRequest), "RUB", new CoordinateRequest(55.755826, 37.617288), null);

    Currency currency = new Currency("RUB");
    Mockito.when(currencyFactory.create("RUB")).thenReturn(currency);

    assertThrows(
        NullPointerException.class, () -> calculatorController.calculate(requestWithNullDeparture));
    assertThrows(
        NullPointerException.class,
        () -> calculatorController.calculate(requsetWithNullDestination));

    Mockito.verify(currencyFactory, Mockito.never()).create(Mockito.anyString());
    Mockito.verify(tariffCalculateUseCase, Mockito.never())
        .calculateTariff(
            Mockito.any(Shipment.class),
            Mockito.any(Coordinate.class),
            Mockito.any(Coordinate.class));
  }
}
