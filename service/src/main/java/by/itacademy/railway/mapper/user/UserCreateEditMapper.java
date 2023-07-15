package by.itacademy.railway.mapper.user;

import by.itacademy.railway.dto.user.UserCreateEditDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Mapper(componentModel = "spring", imports = {Optional.class, StringUtils.class,
        Predicate.class, MultipartFile.class}, uses = {PasswordEncoder.class})
public interface UserCreateEditMapper extends CommonMapper<User, UserCreateEditDto> {

    @Override
    @Mappings({
            @Mapping(target = "name", expression = "java(dto.getName())"),
            @Mapping(target = "lastName", expression = "java(dto.getLastName())"),
            @Mapping(target = "email", expression = "java(dto.getEmail())"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))"),
            @Mapping(target = "avatar", expression = "java(dto.getImage() != null ? dto.getImage().getOriginalFilename() : null)")
    })
    User toModel(UserCreateEditDto dto);

}
