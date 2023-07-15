package by.itacademy.railway.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class UserChangePasswordDto {

    String oldPassword;
    @Size(min = 8, message = "The password must consist of at least 8 characters")
    String newPassword;

}
