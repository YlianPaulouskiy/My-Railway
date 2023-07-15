package by.itacademy.railway.rest;

import by.itacademy.railway.dto.user.UserReadDto;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.notFound;

@RestController("RestUserController")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> findAvatar(@SessionAttribute UserReadDto user) {
        return userService.findAvatar(user.getId())
                .map(content -> ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

}
