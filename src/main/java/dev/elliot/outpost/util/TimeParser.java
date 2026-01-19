
package dev.elliot.outpost.util;

public class TimeParser {

    public static int parse(String input) {
        input = input.toLowerCase();
        int value = Integer.parseInt(input.replaceAll("[^0-9]", ""));

        if (input.endsWith("d")) return value * 86400;
        if (input.endsWith("h")) return value * 3600;
        if (input.endsWith("m")) return value * 60;

        throw new IllegalArgumentException("Invalid time format");
    }
}
