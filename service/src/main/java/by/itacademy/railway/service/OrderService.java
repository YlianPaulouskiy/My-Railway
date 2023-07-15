package by.itacademy.railway.service;

import by.itacademy.railway.dto.order.OrderCreateEditDto;
import by.itacademy.railway.dto.order.OrderReadDto;
import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.OrderStatus;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.entity.embedded.OrderInfo;
import by.itacademy.railway.mapper.order.OrderCreateEditMapper;
import by.itacademy.railway.mapper.order.OrderReadMapper;
import by.itacademy.railway.repository.OrderRepository;
import by.itacademy.railway.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketService ticketService;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    @Transactional(readOnly = true)
    public List<OrderReadDto> findAllByUserId(@NotNull(message = "User id can't be null.") Long id) {
        return orderRepository.findAllByUserId(id).stream()
                .map(orderReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<OrderReadDto> findAllByUserId(@NotNull(message = "User id can't be null.") Long id, Pageable pageable) {
        return orderRepository.findAllByUserId(id, pageable)
                .map(orderReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OrderReadDto> findById(@NotNull(message = "Order id can't be null.") Long id) {
        return orderRepository.findById(id).map(orderReadMapper::toDto);
    }

    @Transactional
    public Optional<OrderCreateEditDto> create(@NotNull(message = "User id can't be null.") Long userId) {
        var order = Order.builder()
                .orderInfo(OrderInfo.builder()
                        .no(UUID.randomUUID().toString())
                        .status(OrderStatus.NEED_PAY)
                        .registrationTime(LocalDateTime.now())
                        .build())
                .user(userRepository.findById(userId)
                        .orElseThrow(() -> {
                            throw new EntityNotFoundException("User with id = " + userId + " not found!!!");
                        }))
                .build();
        orderRepository.save(order);
        return Optional.of(orderCreateEditMapper.toDto(order));
    }

    @Transactional
    public void remove(@NotNull(message = "Order id can't be null.") Long id) {
        deleteTicketsWhichLinkedAtOrder(id);
        orderRepository.deleteById(id);
    }

    /**
     * Удаляет ссылающие билеты на текущий заказ
     *
     * @param id идентификационный номер заказа
     */
    private void deleteTicketsWhichLinkedAtOrder(Long id) {
        orderRepository.findById(id).ifPresent(order -> order.getTickets()
                .stream()
                .map(Ticket::getId)
                .forEach(ticketService::remove));
    }

}
