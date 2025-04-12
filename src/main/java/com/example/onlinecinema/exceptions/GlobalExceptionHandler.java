package com.example.onlinecinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обработка 404 ошибки
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Фильм не найден");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-page";
    }

    // Обработка ошибок валидации
    @ExceptionHandler({ValidationException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(Exception ex, Model model) {
        BindingResult bindingResult = ex instanceof ValidationException ?
                ((ValidationException) ex).getBindingResult() :
                ((BindException) ex).getBindingResult();

        model.addAttribute("errors", bindingResult.getAllErrors());
        return "movie/movie-edit"; // или другая форма
    }

    // Обработка дублирования
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicate(DuplicateException ex, Model model) {
        model.addAttribute("errorTitle", "Конфликт данных");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-page";
    }

    // Обработка всех остальных исключений
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalError(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка");
        return "error/error-page";
    }
}