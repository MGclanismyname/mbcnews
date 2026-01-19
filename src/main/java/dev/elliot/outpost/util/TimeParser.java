
package dev.elliot.outpost.util;

public class TimeParser {
    public static int parse(String s) {
        s = s.toLowerCase();
        int v = Integer.parseInt(s.replaceAll("[^0-9]", ""));
        if (s.endsWith("d")) return v * 86400;
        if (s.endsWith("h")) return v * 3600;
        if (s.endsWith("m")) return v * 60;
        throw new IllegalArgumentException("Invalid time");
    }
}
