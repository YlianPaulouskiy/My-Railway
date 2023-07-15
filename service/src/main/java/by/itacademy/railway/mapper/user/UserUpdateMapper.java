package by.itacademy.railway.mapper.user;

import by.itacademy.railway.dto.user.UserUpdateDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Mapper(componentModel = "spring", imports = {Optional.class,
        Predicate.class, MultipartFile.class})
public interface UserUpdateMapper extends CommonMapper<User, UserUpdateDto> {

    @Override
    @Mapping(target = "avatar", expression = "java(dto.getImage() != null ? dto.getImage().getOriginalFilename() : null)")
    User toModel(UserUpdateDto dto);
}
