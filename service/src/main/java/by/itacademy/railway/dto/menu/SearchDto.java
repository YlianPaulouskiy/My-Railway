package by.itacademy.railway.dto.menu;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
public class SearchDto {

    @NotBlank(message = "Station From is required.")
    String from;
    @NotBlank(message = "Station To is required.")
    String to;
    LocalDate when;

}
