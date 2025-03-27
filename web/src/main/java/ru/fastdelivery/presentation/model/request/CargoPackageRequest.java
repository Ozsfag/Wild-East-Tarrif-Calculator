package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.BigInteger;

public record CargoPackageRequest(
    @Schema(description = "Вес упаковки, граммы", example = "5667.45") @Min(0) @Max(150_000)
        BigInteger weight,
    @Schema(description = "Длинна упаковки, миллиметры", example = "345") @Min(0) @Max(1500)
        BigDecimal length,
    @Schema(description = "Ширина упаковки, миллиметры", example = "234") @Min(0) @Max(1500)
        BigDecimal width,
    @Schema(description = "Высота упаковки, миллиметры", example = "123") @Min(0) @Max(1500)
        BigDecimal height) {}
