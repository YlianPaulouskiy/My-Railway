package by.itacademy.railway.mapper.seat;

import by.itacademy.railway.dto.seat.SeatCreateEditDto;
import by.itacademy.railway.entity.Seat;
import by.itacademy.railway.entity.SeatType;
import by.itacademy.railway.entity.embedded.SeatInfo;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {SeatInfo.class, SeatType.class})
public interface SeatCreateEditMapper extends CommonMapper<Seat, SeatCreateEditDto> {

    @Override
    @Mapping(target = "seatInfo", expression = "java(SeatInfo.builder()" +
                                               ".no(dto.getNo())" +
                                               ".type(SeatType.valueOf(dto.getType()))" +
                                               ".build())")
    Seat toModel(SeatCreateEditDto dto);

    @Override
    @Mappings({
            @Mapping(target = "no", expression = "java(model.getSeatInfo().getNo())"),
            @Mapping(target = "type", expression = "java(model.getSeatInfo().getType().toString())")
    })
    SeatCreateEditDto toDto(Seat model);
}
