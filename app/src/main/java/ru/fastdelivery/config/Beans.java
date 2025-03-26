package ru.fastdelivery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.services.GoogleMapsService;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/** Определение реализаций бинов для всех модулей приложения */
@Configuration
@ConditionalOnProperty(value = "${google_maps.api_key}", havingValue = "true")
public class Beans {

  @Bean
  public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
    return new CurrencyFactory(currencyProperties);
  }

  @Bean
  public TariffCalculateUseCase tariffCalculateUseCase(
      WeightPriceProvider weightPriceProvider,
      VolumePriceProvider volumePriceProvider,
      GoogleMapsService googleMapsService) {
    return new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, googleMapsService);
  }

  @Bean
  public GoogleMapsService googleMapsService(@Value("google_maps.api_key") String apiKey) {
    return new GoogleMapsService(apiKey);
  }
}
