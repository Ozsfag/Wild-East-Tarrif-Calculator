package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
    @Schema(
            description = "Список упаковок отправления",
            example =
                "[\n"
                    + "    {\n"
                    + "      \"weight\": 4564,\n"
                    + "      \"length\": 345,\n"
                    + "      \"width\": 589,\n"
                    + "      \"height\": 234\n"
                    + "    }\n"
                    + "]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,
    @Schema(description = "Трехбуквенный код валюты", example = "RUB") @NotNull
        String currencyCode) {}
