package by.itacademy.railway.mapper.user;

import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.CommonMapper;
import by.itacademy.railway.mapper.role.RoleReadMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleReadMapper.class})
public interface UserReadMapper extends CommonMapper<User, UserReadDto> {

}
