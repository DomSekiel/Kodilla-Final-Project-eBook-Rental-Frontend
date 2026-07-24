package utils;

import java.util.UUID;

public class TestDataGenerator {

    public static String generateTitle() {
        return "TestTitle_" + UUID.randomUUID();
    }

    public static String generateAuthor() {
        return "TestAuthor_" + UUID.randomUUID();
    }

    public static String generateLogin() {
        return "user" + UUID.randomUUID();
    }

    public static String getDefaultYear() {
        return "2026";
    }
}