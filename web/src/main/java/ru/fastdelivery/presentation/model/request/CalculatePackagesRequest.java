package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/** Represents the request data for calculating delivery costs. */
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
        @NotNull(message = "Packages list must not be null")
        @NotEmpty(message = "Packages list must not be empty")
        List<CargoPackage> packages,
    @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull(message = "Currency code must not be null")
        String currencyCode,
    @Schema(
            description = "Координаты отправиеля",
            example = "{\"latitude\": 55.755826, \"longitude\": 37.617288}")
        @NotNull
        @NotEmpty
        Coordinate departure,
    @Schema(
            description = "Координаты получателя",
            example = "{\"latitude\": 55.755826, \"longitude\": 37.617288}")
        @NotNull
        @NotEmpty
        Coordinate destination) {}
