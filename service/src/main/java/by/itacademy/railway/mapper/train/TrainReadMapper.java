package by.itacademy.railway.mapper.train;

import by.itacademy.railway.dto.train.TrainReadDto;
import by.itacademy.railway.entity.Train;
import by.itacademy.railway.entity.TrainType;
import by.itacademy.railway.entity.embedded.TrainInfo;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {TrainInfo.class, TrainType.class})
public interface TrainReadMapper extends CommonMapper<Train, TrainReadDto> {

    @Override
    @Mapping(target = "trainInfo", expression = "java(TrainInfo.builder()" +
                                                ".code(dto.getCode())" +
                                                ".type(TrainType.valueOf(dto.getType()))" +
                                                ".build())")
    Train toModel(TrainReadDto dto);

    @Override
    @Mappings({
            @Mapping(target = "code", expression = "java(model.getTrainInfo().getCode())"),
            @Mapping(target = "type", expression = "java(model.getTrainInfo().getType().toString())")
    })
    TrainReadDto toDto(Train model);
}
