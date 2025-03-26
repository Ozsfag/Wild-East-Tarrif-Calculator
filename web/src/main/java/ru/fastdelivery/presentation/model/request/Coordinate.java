package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record Coordinate(
    @Schema(description = "Широта", example = "55.755826") double latitude,
    @Schema(description = "Долгота", example = "37.617288") double longitude) {}
