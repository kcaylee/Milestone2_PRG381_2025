package utils;

public class InputValidator {

    // Check if any string field is empty
    //Already such a function?
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

    // Check if string is a valid rating number (used with text fields)
    public static boolean isValidRating(String ratingStr) {
        if (!isInteger(ratingStr)) return false;
        int rating = Integer.parseInt(ratingStr);
        return isValidRating(rating);
    }

    
    
    
    //CLAUDE
    
    
    // Check if string is a valid date in YYYY-MM-DD format
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return false;
        
        try {
            java.sql.Date.valueOf(dateStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Check if string is a valid time in HH:MM format
    public static boolean isValidTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return false;
        
        try {
            java.sql.Time.valueOf(timeStr + ":00");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
