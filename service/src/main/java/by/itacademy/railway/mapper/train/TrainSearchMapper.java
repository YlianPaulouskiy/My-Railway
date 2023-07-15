package by.itacademy.railway.mapper.train;

import by.itacademy.railway.dto.station.route.RouteStationReadDto;
import by.itacademy.railway.dto.train.TrainSearchDto;
import by.itacademy.railway.entity.Train;
import by.itacademy.railway.entity.TrainType;
import by.itacademy.railway.entity.embedded.TrainInfo;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.station.route.RouteStationReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {TrainInfo.class, TrainType.class},
        uses = RouteStationReadMapper.class)
public interface TrainSearchMapper extends CommonMapper<Train, TrainSearchDto> {

    @Override
    @Mapping(target = "trainInfo", expression = "java(TrainInfo.builder()" +
                                                ".code(dto.getCode())" +
                                                ".type(TrainType.valueOf(dto.getType()))" +
                                                ".build())")
    Train toModel(TrainSearchDto dto);

    @Mappings({
            @Mapping(target = "id", expression = "java(model.getId())"),
            @Mapping(target = "code", expression = "java(model.getTrainInfo().getCode())"),
//            @Mapping(target = "from", source = "from"),
//            @Mapping(target = "to", source = "to"),
            @Mapping(target = "type", expression = "java(model.getTrainInfo().getType().toString())"),
            @Mapping(target = "seatsCount", expression = "java(Long.valueOf(model.getWagons().stream()" +
                                                         ".flatMap(wagon -> wagon.getSeats().stream())" +
                                                         ".filter(seat -> seat.getTicket() == null)" +
                                                         ".count()).intValue())")
    })
    TrainSearchDto toDto(Train model, RouteStationReadDto from, RouteStationReadDto to);

}
