package by.itacademy.railway.mapper.role;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.entity.Role;
import by.itacademy.railway.mapper.CommonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleReadMapper extends CommonMapper<Role, RoleReadDto> {

    @Override
    @Mapping(target = "role", source = "name")
    Role toModel(RoleReadDto dto);

    @Override
    @Mapping(target = "name", source = "role")
    RoleReadDto toDto(Role model);

}
