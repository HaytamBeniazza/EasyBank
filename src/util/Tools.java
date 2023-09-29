package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static Date stringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null to indicate a parsing error
        }
    }

    public static boolean isDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Make parsing strict, so it doesn't accept invalid dates

        try {
            // Attempt to parse the string as a date
            Date date = dateFormat.parse(dateString);
            return true; // If parsing is successful, it's a valid date
        } catch (ParseException e) {
            return false; // Parsing failed, it's not a valid date
        }
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
