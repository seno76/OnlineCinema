package com.example.onlinecinema.service;

public class FormatDurations {

    // Форматирование длительности сериала
    static public String getFormattedDuration(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        if (hours > 0) {
            return hours + " ч " + minutes + " мин";
        }
        return minutes + " мин";
    }

}
