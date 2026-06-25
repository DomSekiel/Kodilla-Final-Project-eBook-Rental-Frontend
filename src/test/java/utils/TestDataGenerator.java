package utils;

public class TestDataGenerator {

    public static String generateTitle() {
        return "TestTitle_" + System.currentTimeMillis();
    }

    public static String generateAuthor() {
        return "TestAuthor_" + System.currentTimeMillis();
    }

    public static String generateLogin() {
        return "user" + System.currentTimeMillis();
    }

    public static String generateYear() {
        return "2026";
    }
}