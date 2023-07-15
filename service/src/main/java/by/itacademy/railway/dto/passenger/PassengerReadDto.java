package by.itacademy.railway.dto.passenger;

import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import jakarta.validation.Valid;
import lombok.*;

@Value
@Builder
public class PassengerReadDto {

    Long id;
    String name;
    String lastName;
    String middleName;
    Gender gender;
    DocumentType document;
    String documentNo;

}
