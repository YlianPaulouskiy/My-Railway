package by.itacademy.railway.mapper.passenger;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.entity.Passenger;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerReadMapper extends CommonMapper<Passenger, PassengerReadDto> {
}
