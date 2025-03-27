package ru.fastdelivery.presentation.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;

public record CoordinateRequest(
    @Schema(description = "Широта", example = "55.755826") @NotNull @NotBlank Double latitude,
    @Schema(description = "Долгота", example = "37.617288") @NotNull @NotBlank Double longitude) {
  public static Coordinate mapTo(CoordinateRequest request) {
    return new Coordinate(request.latitude(), request.longitude());
  }
}
