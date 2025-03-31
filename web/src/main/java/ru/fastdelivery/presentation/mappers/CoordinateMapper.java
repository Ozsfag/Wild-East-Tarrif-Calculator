package ru.fastdelivery.presentation.mappers;

import org.mapstruct.Mapper;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;
import ru.fastdelivery.presentation.model.request.CoordinateRequest;

@Mapper(componentModel = "SPRING")
public interface CoordinateMapper {

  Coordinate coordinateRequestToCoordinate(CoordinateRequest request);
}
