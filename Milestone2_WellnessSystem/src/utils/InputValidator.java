package utils;

/**
 * Utility class for input validation across the application.
 * Ensures user input is well-formed before being processed or saved to the database.
 */
public class InputValidator {

    /**
     * Checks if any provided string field is empty or null.
     * @param fields Varargs list of strings to validate
     * @return true if any field is empty or null, false otherwise
     */
    public static boolean isEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given string can be parsed as an integer.
     * @param value The input string
     * @return true if it's a valid integer, false otherwise
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the numeric rating is within the valid range (1–5).
     * @param rating The rating value
     * @return true if valid, false otherwise
     */
    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    /**
     * Checks if a string is a valid rating by parsing and applying range check.
     * @param ratingStr The rating input as string
     * @return true if the string is a valid number within range 1–5
     */
    public static boolean isValidRating(String ratingStr) {
        if (!isInteger(ratingStr)) return false;
        int rating = Integer.parseInt(ratingStr);
        return isValidRating(rating);
    }

    /**
     * Validates if a string matches the date format YYYY-MM-DD.
     * @param dateStr The date string
     * @return true if valid format, false otherwise
     */
    public static boolean isValidDate(String dateStr) {
        return dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    /**
     * Validates if a string matches the time format HH:MM (24-hour format).
     * @param timeStr The time string
     * @return true if valid format, false otherwise
     */
    public static boolean isValidTime(String timeStr) {
        return timeStr.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$");
    }
}
