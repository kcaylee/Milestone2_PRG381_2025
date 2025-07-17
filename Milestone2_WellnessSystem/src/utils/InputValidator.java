package utils;

public class InputValidator {

    // Check if any string field is empty
    public static boolean isEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Check if a string is a valid integer
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if rating is between 1 and 5
    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    // Check if string is a valid rating number
    public static boolean isValidRating(String ratingStr) {
        if (!isInteger(ratingStr)) return false;
        int rating = Integer.parseInt(ratingStr);
        return isValidRating(rating);
    }

    // Check if string is a valid date in format YYYY-MM-DD
    public static boolean isValidDate(String dateStr) {
        return dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    // Check if string is a valid time in format HH:MM
    public static boolean isValidTime(String timeStr) {
        return timeStr.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$");
    }
}
