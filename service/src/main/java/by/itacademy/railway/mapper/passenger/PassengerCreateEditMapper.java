package by.itacademy.railway.mapper.passenger;

import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.entity.Passenger;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerCreateEditMapper extends CommonMapper<Passenger, PassengerCreateEditDto> {

}
