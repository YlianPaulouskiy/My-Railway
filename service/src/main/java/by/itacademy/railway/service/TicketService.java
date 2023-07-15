package by.itacademy.railway.service;

import by.itacademy.railway.dto.ticket.TicketCreateEditDto;
import by.itacademy.railway.dto.ticket.TicketReadDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.ticket.TicketCreateEditMapper;
import by.itacademy.railway.mapper.ticket.TicketReadMapper;
import by.itacademy.railway.repository.OrderRepository;
import by.itacademy.railway.repository.SeatRepository;
import by.itacademy.railway.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;
    private final TicketReadMapper ticketReadMapper;
    private final TicketCreateEditMapper ticketCreateEditMapper;


    @Transactional
    public Optional<TicketReadDto> create(TicketCreateEditDto ticketCreateEditDto) {
        var ticket = ticketCreateEditMapper.toModel(ticketCreateEditDto);
        ticket.setOrder(orderRepository.findById(ticketCreateEditDto.getOrder().getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Order with id = " + ticketCreateEditDto.getOrder().getId() + " not found!!!");
        }));
        ticket = ticketRepository.saveAndFlush(ticket);
        var seat = seatRepository.findById(ticketCreateEditDto.getSeat().getId());
        if (seat.isEmpty()) {
            throw new EntityNotFoundException("Seat with id = " + ticketCreateEditDto.getSeat().getId() + " not found!!!");
        }
        seat.get().setTicket(ticket);
        seatRepository.save(seat.get());
        return Optional.ofNullable(ticketReadMapper.toDto(ticket));
    }

    @Transactional
    public void remove(@NotNull(message = "Ticket id can't be null") Long id) {
        setNullToLinkedSeat(id);
        var ticketOpt = ticketRepository.findById(id);
        ticketRepository.deleteById(id);
        deleteOrderWhichLinkedAtTicket(ticketOpt);
    }

    /**
     * Устанавливает значение поля ticket у Seat в null для удаляемого билета
     *
     * @param id идентификационный номер билета
     */
    private void setNullToLinkedSeat(Long id) {
        ticketRepository.findById(id).ifPresent(ticket -> ticket.getSeat().setTicket(null));
    }

    /**
     * Удаляет заказ, если у него нету больше билетов которые на него ссылаются
     *
     * @param ticketOpt обертка билета, заказ которого нужно проверить
     */
    private void deleteOrderWhichLinkedAtTicket(Optional<Ticket> ticketOpt) {
        if (ticketOpt.isPresent()) {
            orderRepository.flush(); // чтобы в кэше ничего не осталось
            var orderOpt = orderRepository.findById(ticketOpt.get().getOrder().getId());
            if (orderOpt.isPresent() && orderOpt.get().getTickets().isEmpty()) {
                orderRepository.deleteById(orderOpt.get().getId());
            }
        }
    }

}
