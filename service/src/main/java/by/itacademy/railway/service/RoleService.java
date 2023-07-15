package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.role.RoleReadMapper;
import by.itacademy.railway.repository.RoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RoleReadMapper roleReadMapper;

    @Transactional(readOnly = true)
    public List<RoleReadDto> findAll() {
        return roleRepository.findAll().stream()
                .map(roleReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<RoleReadDto> findById(@NotNull(message = "Role id can't be null.") Integer id) {
        return roleRepository.findById(id).map(roleReadMapper::toDto);
    }

    @Transactional
    public void remove(@NotNull(message = "Role id can't be null.") Integer id) {
        deleteAllUsersWhichLinkedAtRole(id);
        roleRepository.deleteById(id);
    }

    /**
     * Удаляет пользователей у которых эта роль
     *
     * @param id идентификационный номер роли
     */
    private void deleteAllUsersWhichLinkedAtRole(Integer id) {
        roleRepository.findById(id).ifPresent(role -> role.getUsers()
                .stream()
                .map(User::getId)
                .forEach(userService::remove));
    }

}
