package by.itacademy.railway.mapper.station.route;

import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.station.StationReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = StationReadMapper.class)
public interface RouteStationReadMapper extends CommonMapper<RouteStation, RouteStationReadDto> {
}
