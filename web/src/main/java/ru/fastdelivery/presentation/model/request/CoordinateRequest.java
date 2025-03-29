package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CoordinateRequest(
    @Schema(description = "Широта", example = "55.755826") @NotBlank Double latitude,
    @Schema(description = "Долгота", example = "37.617288") @NotBlank Double longitude) {}
