package by.itacademy.railway.dto.user;

import by.itacademy.railway.dto.role.RoleReadDto;
import lombok.*;

@Value
@Builder
public class UserReadDto {

    Long id;
    String name;
    String lastName;
    String email;
    String avatar;
    RoleReadDto role;

}
