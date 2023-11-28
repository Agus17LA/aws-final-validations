package com.awsfinal.awsfinalvalidations.utils;

import com.awsfinal.awsfinalvalidations.exceptions.InvalidDateFormatException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class DateUtils {
    private static final List<String> DATE_FORMATS = Arrays.asList(
            "dd/MM/yyyy",
            "yyyy-MM-dd",
            "MM/dd/yyyy",
            "yyyyMMdd"
    );

    public static String parseAndFormatBirthdate(String birthdateStr, String timeZoneId) {
            TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdfInput = new SimpleDateFormat(format);
                sdfInput.setTimeZone(timeZone);
                Date birthdate = sdfInput.parse(birthdateStr);

                SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"); // quizas usar= yyyy-MM-dd HH:mm:ss
                sdfOutput.setTimeZone(timeZone);
                return sdfOutput.format(birthdate);
            } catch (ParseException e) {
                //
            }
        }
        throw new InvalidDateFormatException("Fecha de nacimiento no válida: " + birthdateStr);
    }
}
