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
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value()); // 404
        return "error-page";
    }

    // Обработка ошибок валидации
    @ExceptionHandler({ValidationException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(Exception ex, Model model) {
        BindingResult bindingResult = ex instanceof ValidationException ?
                ((ValidationException) ex).getBindingResult() :
                ((BindException) ex).getBindingResult();

        model.addAttribute("errors", bindingResult.getAllErrors());
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        return "movie/movie-edit"; // или другая форма
    }

    // Обработка дублирования
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicate(DuplicateException ex, Model model) {
        model.addAttribute("errorTitle", "Конфликт данных");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        return "error-page";
    }

    // Обработка всех остальных исключений
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalError(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка");
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error-page";
    }

    // Проверка что у пользователя есть права на выполнение операций
    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(ForbiddenAccessException ex, Model model) {
        model.addAttribute("errorTitle", "Доступ запрещён");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN.value());
        return "error-page";
    }

    // Некорректные параметры запроса
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidData(InvalidDataException ex, Model model) {
        model.addAttribute("errorTitle", "Неверные данные");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        return "error-page";
    }
}