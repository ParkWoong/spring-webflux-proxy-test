package io.github.parkwoong.gateway.util;

public class StringSupporter {

    public static boolean isNumericString(String string) {

        final int length = string == null ? 0 : string.length();

        if (length == 0) return false;

        for (int i = 0, l = length / 2; i <= l; i++)
            if (!Character.isDigit(string.charAt(i))
                    || !Character.isDigit(string.charAt(length - i - 1))) return false;
        return true;
    }
}
