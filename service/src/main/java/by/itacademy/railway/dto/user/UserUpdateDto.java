package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
public class UserUpdateDto {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @Email(message = "Email is incorrect, follow the example: mymail@gmail.com")
    String email;
    MultipartFile image;

}
