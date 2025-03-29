package ru.fastdelivery.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.fastdelivery.domain.delivery.coordinate.Coordinate;
import ru.fastdelivery.presentation.model.request.CoordinateRequest;

@Mapper
public interface CoordinateMapper {
  CoordinateMapper INSTANCE = Mappers.getMapper(CoordinateMapper.class);

  Coordinate coordinateRequestToCoordinate(CoordinateRequest request);
}
