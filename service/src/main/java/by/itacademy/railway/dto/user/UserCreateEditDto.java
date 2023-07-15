package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
public class UserCreateEditDto {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @Email(message = "Email is incorrect, follow the example: mymail@gmail.com")
    String email;
    @Size(min = 8, message = "The password must consist of at least 8 characters")
    String password;

    MultipartFile image;

}
