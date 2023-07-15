package by.itacademy.railway.mapper.order;

import by.itacademy.railway.dto.order.OrderReadDto;
import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.OrderStatus;
import by.itacademy.railway.entity.embedded.OrderInfo;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.ticket.TicketReadMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring", imports = {OrderInfo.class, OrderStatus.class,
        BigDecimal.class, RoundingMode.class}, uses = {TicketReadMapper.class})
public interface OrderReadMapper extends CommonMapper<Order, OrderReadDto> {

    @Override
    @Mapping(target = "orderInfo", expression = "java(OrderInfo.builder()" +
                                                ".no(dto.getNo())" +
                                                ".status(OrderStatus.valueOf(dto.getStatus()))" +
                                                ".registrationTime(dto.getRegistrationTime())" +
                                                ".payedTime(dto.getPayedTime()).build())")
    Order toModel(OrderReadDto dto);

    @Override
    @Mappings({
            @Mapping(target = "no", expression = "java(model.getOrderInfo().getNo())"),
            @Mapping(target = "status", expression = "java(model.getOrderInfo().getStatus().toString())"),
            @Mapping(target = "registrationTime", expression = "java(model.getOrderInfo().getRegistrationTime())"),
            @Mapping(target = "payedTime", expression = "java(model.getOrderInfo().getPayedTime())"),
            @Mapping(target = "cost", expression = "java(model.getTickets().stream()" +
                                                   ".filter(ticket -> ticket.getSeat() != null)" +
                                                   ".map(ticket -> BigDecimal.valueOf(ticket.getSeat().getCost()))" +
                                                   ".reduce(BigDecimal.ZERO, BigDecimal::add)" +
                                                   ".setScale(2, RoundingMode.HALF_DOWN)" +
                                                   ".doubleValue())")
    })
    OrderReadDto toDto(Order model);
}
