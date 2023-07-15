package by.itacademy.railway.service;

import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.dto.user.UserChangePasswordDto;
import by.itacademy.railway.dto.user.UserCreateEditDto;
import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.dto.user.UserUpdateDto;
import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.User;
import by.itacademy.railway.mapper.user.UserCreateEditMapper;
import by.itacademy.railway.mapper.user.UserReadMapper;
import by.itacademy.railway.mapper.user.UserUpdateMapper;
import by.itacademy.railway.repository.RoleRepository;
import by.itacademy.railway.repository.UserRepository;
import by.itacademy.railway.service.exception.PasswordMismatchException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderService orderService;
    private final ImageService imageService;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserUpdateMapper userUpdateMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> findById(@NotNull(message = "User id can't be null.") Long id) {
        return userRepository.findById(id).map(userReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserReadDto> findByEmail(@NotBlank(message = "Email can't be empty.") String email) {
        return userRepository.findByEmail(email).map(userReadMapper::toDto);
    }

    @Transactional
    public Optional<UserReadDto> create(UserCreateEditDto userCreateEditDto) {
        var user = userCreateEditMapper.toModel(userCreateEditDto);
        user.setRole(roleRepository.findByRole("USER")
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Role USER not found!!!");
                }));
        try {
            if (user.getAvatar() != null && !user.getAvatar().isBlank()) {
                imageService.upload(user.getAvatar(), userCreateEditDto.getImage().getInputStream());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        userRepository.save(user);
        return Optional.ofNullable(userReadMapper.toDto(user));
    }

    @Transactional
    public void remove(@NotNull(message = "User id can't be null.") Long id) {
        deleteAllOrdersWhichLinkedAtUser(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public Optional<UserReadDto> update(@NotNull(message = "User id can't be null.") Long userId,
                                        @Valid UserUpdateDto updateDto) {
        var updatedUser = userUpdateMapper.toModel(updateDto);
        updatedUser.setId(userId);
        userRepository.findById(userId).ifPresent(user -> {
            try {
                if (updatedUser.getAvatar() != null && !updatedUser.getAvatar().isBlank()) {
                    if (user.getAvatar() != null && !user.getAvatar().isBlank())
                        imageService.removeImage(user.getAvatar()); // удаляем старый автар
                    imageService.upload(updateDto.getImage().getOriginalFilename(), updateDto.getImage().getInputStream()); //сохраняем новый
                } else { // если аватар не меняется
                    updatedUser.setAvatar(user.getAvatar()); // то устанавливаем старый автар
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatedUser.setPassword(user.getPassword()); // устанавливаем старый пароль
            updatedUser.setRole(user.getRole());//роль
        });
        userRepository.saveAndFlush(updatedUser);
        return Optional.ofNullable(userReadMapper.toDto(updatedUser));
    }

    @Transactional(readOnly = true)
    public Optional<byte[]> findAvatar(@NotNull(message = "User id can't be null.") Long userId) {
        return userRepository.findById(userId)
                .map(User::getAvatar)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public void updateRole(@NotNull(message = "User id can't be null.") Long id, RoleReadDto roleReadDto) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User with id = " + id + " not found!!!");
        });
        user.setRole(roleRepository.findById(roleReadDto.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Role with name = " + roleReadDto.getName() + " not found!!!");
        }));
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void changePassword(Long id, UserChangePasswordDto passwordDto) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User with id = " + id + " not found!!!");
        });
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.saveAndFlush(user);
    }

    /**
     * Удаляет заказы которые ссылаются на текущего пользователя
     *
     * @param id идентификационный номер заказа
     */
    private void deleteAllOrdersWhichLinkedAtUser(Long id) {
        userRepository.findById(id).ifPresent(user -> user.getOrders()
                .stream()
                .map(Order::getId)
                .forEach(orderService::remove));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }
}
