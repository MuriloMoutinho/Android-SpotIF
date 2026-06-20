package br.ifsul.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {
    private static String BRAZIL_PATTERN = "dd/MM/yyyy";

    public static String formatBrazilDateString(String inputDate){
        if(inputDate == null) return "";
        LocalDate date = LocalDate.parse(inputDate);
        DateTimeFormatter brFormatter = DateTimeFormatter.ofPattern(BRAZIL_PATTERN);

        return date.format(brFormatter);
    }
}
