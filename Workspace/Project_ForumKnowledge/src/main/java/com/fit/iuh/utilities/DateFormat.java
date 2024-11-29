package com.fit.iuh.utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormat {
    public static String formatMMMMddyyyy(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return dateFormat.format(date);
    }

    // Hours:Minutes:Seconds - MMMM dd, yyyy
    public static String formatHHmmssMMMMddyyyy(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm - MMMM dd, yyyy");
        return dateFormat.format(date);
    }
}
