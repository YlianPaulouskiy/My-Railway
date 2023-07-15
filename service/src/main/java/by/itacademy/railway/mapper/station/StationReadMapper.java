package by.itacademy.railway.mapper.station;

import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.entity.Station;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationReadMapper extends CommonMapper<Station, StationReadDto> {
}
