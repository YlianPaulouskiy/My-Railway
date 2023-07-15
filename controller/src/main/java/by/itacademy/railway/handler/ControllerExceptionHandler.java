package by.itacademy.railway.handler;

import by.itacademy.railway.service.exception.PasswordMismatchException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "by.itacademy.railway.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handlerNotFountException(Model model, EntityNotFoundException exception) {
        log.error("Failed to return response ", exception);
        model.addAttribute("message", exception.getMessage());
        return "error/error";
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public String handlerPasswordException(Model model, PasswordMismatchException exception) {
        log.error("Failed to return response ", exception);
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

}
