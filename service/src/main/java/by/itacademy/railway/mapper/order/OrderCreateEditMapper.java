package by.itacademy.railway.mapper.order;

import by.itacademy.railway.dto.order.OrderCreateEditDto;
import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.OrderStatus;
import by.itacademy.railway.entity.embedded.OrderInfo;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {OrderInfo.class, OrderStatus.class})
public interface OrderCreateEditMapper extends CommonMapper<Order, OrderCreateEditDto> {

    @Override
    @Mapping(target = "orderInfo", expression = "java(OrderInfo.builder()" +
                                                ".no(dto.getNo())" +
                                                ".status(OrderStatus.valueOf(dto.getStatus()))" +
                                                ".registrationTime(dto.getRegistrationTime())" +
                                                ".payedTime(dto.getPayedTime()).build())")
    Order toModel(OrderCreateEditDto dto);

    @Override
    @Mappings({
            @Mapping(target = "no", expression = "java(model.getOrderInfo().getNo())"),
            @Mapping(target = "status", expression = "java(model.getOrderInfo().getStatus().toString())"),
            @Mapping(target = "registrationTime", expression = "java(model.getOrderInfo().getRegistrationTime())"),
            @Mapping(target = "payedTime", expression = "java(model.getOrderInfo().getPayedTime())")
    })
    OrderCreateEditDto toDto(Order model);

}
