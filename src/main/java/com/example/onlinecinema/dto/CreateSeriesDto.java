package com.example.onlinecinema.dto;

import jakarta.validation.constraints.*;

public record CreateSeriesDto(
        @NotBlank(message = "Название сериала обязательно")
        @Size(min = 1, max = 100, message = "Название должно быть от 1 до 100 символов")
        String title,

        @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
        String description,

        @NotBlank(message = "Жанр обязателен")
        @Size(max = 50, message = "Жанр не должен превышать 50 символов")
        String genre,

        @Min(value = 1888, message = "Год должен быть не меньше 1888")
        @Max(value = 2100, message = "Год должен быть не больше 2100")
        int year,

        @DecimalMin(value = "0.0", message = "Рейтинг не может быть отрицательным")
        @DecimalMax(value = "10.0", message = "Рейтинг не может превышать 10")
        double rating,

        @NotBlank(message = "URL постера обязателен")
        @Pattern(regexp = "^(http|https)://.*\\.(jpg|jpeg|png|gif)$",
                message = "URL постера должен быть валидной ссылкой на изображение")
        String posterUrl,

        @NotBlank(message = "URL видео обязателен")
        @Pattern(regexp = "^(http|https)://.*\\.(mp4|mov|avi)$",
                message = "URL видео должен быть валидной ссылкой на видеофайл")
        String movieUrl
) {

    public CreateSeriesDto {
        if (year > java.time.Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Год выпуска не может быть в будущем");
        }
    }
}