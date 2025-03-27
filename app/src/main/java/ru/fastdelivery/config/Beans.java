package ru.fastdelivery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.services.NavigatorService;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/** Определение реализаций бинов для всех модулей приложения */
@Configuration
public class Beans {

  @Bean
  public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
    return new CurrencyFactory(currencyProperties);
  }

  @Bean
  public TariffCalculateUseCase tariffCalculateUseCase(
      WeightPriceProvider weightPriceProvider,
      VolumePriceProvider volumePriceProvider,
      NavigatorService navigatorService) {
    return new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, navigatorService);
  }

  @Bean
  public NavigatorService navigatorService(@Value("${navigator.api_key}") String apiKey) {
    return new NavigatorService(apiKey);
  }
}
