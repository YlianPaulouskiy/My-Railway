package by.itacademy.railway.service;

import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.passenger.PassengerCreateEditMapper;
import by.itacademy.railway.mapper.passenger.PassengerReadMapper;
import by.itacademy.railway.repository.PassengerRepository;
import by.itacademy.railway.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;
    private final TicketService ticketService;
    private final PassengerReadMapper passengerReadMapper;
    private final PassengerCreateEditMapper passengerCreateEditMapper;

    @Transactional(readOnly = true)
    public Optional<PassengerReadDto> findById(@NotNull(message = "Passenger id can't be null") Long id) {
        return passengerRepository.findById(id).map(passengerReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<PassengerReadDto> findAllByUserId(@NotNull(message = "User id can't be null") Long userId) {
        return passengerRepository.findAllByUserId(userId).stream()
                .map(passengerReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PassengerReadDto> findAllByUserId(@NotNull(message = "Passenger id can't be null.") Long id, Pageable pageable) {
        return passengerRepository.findAllByUserId(id, pageable)
                .map(passengerReadMapper::toDto);
    }

    @Transactional
    public Optional<PassengerReadDto> create(@NotNull(message = "User id can't be null.") Long userId,
                                             @Valid PassengerCreateEditDto passengerCreateEditDto) {
        var passenger = passengerRepository.save(
                passengerCreateEditMapper.toModel(passengerCreateEditDto));
        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("User with id = " + userId + " not found!!!");
        }
        userOpt.get().addPassengers(passenger);
        userRepository.saveAndFlush(userOpt.get());
        return Optional.ofNullable(passengerReadMapper.toDto(passenger));
    }

    @Transactional
    public Optional<PassengerReadDto> update(@NotNull(message = "Passenger id can't be null.") Long id,
                                             @Valid PassengerCreateEditDto passengerCreateEditDto) {
        var passenger = passengerCreateEditMapper.toModel(passengerCreateEditDto);
        passenger.setId(id);
        passengerRepository.saveAndFlush(passenger);
        return Optional.of(passengerReadMapper.toDto(passenger));
    }

    @Transactional
    public void remove(@NotNull(message = "Passenger id can't be null.") Long id) {
        deleteTicketWhichLinkedAtPassenger(id);
        passengerRepository.deleteById(id);
    }

    /**
     * Удаляет ссылающие билеты на текущего пассажира
     *
     * @param id идентификационный номер пассажира
     */
    private void deleteTicketWhichLinkedAtPassenger(Long id) {
        passengerRepository.findById(id).ifPresent(passenger -> passenger.getTickets()
                .stream()
                .map(Ticket::getId)
                .forEach(ticketService::remove));
    }

}
