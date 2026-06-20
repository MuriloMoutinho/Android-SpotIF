package br.ifsul.utils;

public class TimeFormatUtils {

    public static String formatStringTime(Integer timeMs) {
        if (timeMs == null) return "";

        int totalSeconds = timeMs / 1000;

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }

        return String.format("%d:%02d", minutes, seconds);
    }
}
