package by.itacademy.railway.mapper.wagon;

import by.itacademy.railway.dto.wagon.WagonReadDto;
import by.itacademy.railway.entity.Wagon;
import by.itacademy.railway.entity.WagonType;
import by.itacademy.railway.entity.embedded.WagonInfo;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.train.TrainReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {WagonInfo.class, WagonType.class},
        uses = {TrainReadMapper.class})
public interface WagonReadMapper extends CommonMapper<Wagon, WagonReadDto> {

    @Override
    @Mapping(target = "wagonInfo", expression = "java(WagonInfo.builder()" +
                                                ".no(dto.getNo())" +
                                                ".type(WagonType.valueOf(dto.getType()))" +
                                                ".build())")
    Wagon toModel(WagonReadDto dto);

    @Override
    @Mappings({
            @Mapping(target = "no", expression = "java(model.getWagonInfo().getNo())"),
            @Mapping(target = "type", expression = "java(model.getWagonInfo().getType().toString())")
    })
    WagonReadDto toDto(Wagon model);
}
