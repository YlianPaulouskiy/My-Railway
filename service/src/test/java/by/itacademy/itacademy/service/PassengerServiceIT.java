package by.itacademy.itacademy.service;

import by.itacademy.itacademy.annotation.IT;
import by.itacademy.railway.dto.passenger.PassengerCreateEditDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.repository.PassengerRepository;
import by.itacademy.railway.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class PassengerServiceIT {

    private final PassengerService passengerService;
    private final PassengerRepository passengerRepository;
    private final static Integer DEFAULT_SIZE = 3;
    private final static Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private final static Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private PassengerCreateEditDto createPassengerDto;
    private PassengerCreateEditDto updatePassengerDto;
    private final static Long REMOVE_ID = 1L;
    private final static Integer PAGE_EXPECT_SIZE = 2;
    private final static Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        createPassengerDto = PassengerCreateEditDto.builder()
                .name("Nastya")
                .lastName("Kadirkylova")
                .middleName("Rystamovna")
                .gender(Gender.FEMALE)
                .document(DocumentType.PASSPORT)
                .documentNo("BB12323412")
                .build();
        updatePassengerDto = PassengerCreateEditDto.builder()
                .name("Martin")
                .lastName("Flex")
                .middleName("Michailovi4")
                .gender(Gender.MALE)
                .document(DocumentType.DRIVER_LICENSE)
                .documentNo("124673819272")
                .build();
    }

    @Test
    void whenFindAllByUserIdExpect3Passengers() {
        assertThat(passengerService.findAllByUserId(USER_ID)).hasSize(DEFAULT_SIZE);
    }

    @Test
    void whenFindAllByUserIdWithPageableExpectPageSizeAmountPassengers() {
        assertThat(passengerService.findAllByUserId(USER_ID, Pageable.ofSize(PAGE_EXPECT_SIZE)))
                .hasSize(PAGE_EXPECT_SIZE);
    }

    @Test
    void whenCreateExpectIncreaseFullSizeBy1() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        var dtoOptional = passengerService.create(1L, createPassengerDto);
        assertThat(dtoOptional).isPresent();
        assertThat(passengerRepository.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    void whenUpdateExpectNewFieldsValues() {
        var returnOpt = passengerService.update(REMOVE_ID, updatePassengerDto);
        var dbOpt = passengerRepository.findById(REMOVE_ID);
        assertTrue(returnOpt.isPresent());
        assertTrue(dbOpt.isPresent());
        assertEquals(returnOpt.get().getId(), dbOpt.get().getId());
        assertEquals(returnOpt.get().getName(), dbOpt.get().getName());
        assertEquals(returnOpt.get().getLastName(), dbOpt.get().getLastName());
        assertEquals(returnOpt.get().getMiddleName(), dbOpt.get().getMiddleName());
        assertEquals(returnOpt.get().getDocument(), dbOpt.get().getDocument());
        assertEquals(returnOpt.get().getDocumentNo(), dbOpt.get().getDocumentNo());
        assertEquals(returnOpt.get().getGender(), dbOpt.get().getGender());
    }

    @Test
    void whenRemoveExpectReduceFullSizeBy1() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        passengerService.remove(REMOVE_ID);
        assertThat(passengerRepository.existsById(REMOVE_ID)).isFalse();
        assertThat(passengerRepository.findAll()).hasSize(REMOVED_SIZE);
    }


}
