package org.basic.feimi.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphaNum = upper + lower + digits;

    public static final String alphaNumWithoutZeroAndO = alphaNum
            .replaceAll("0", "")
            .replaceAll("o", "")
            .replaceAll("O", "");

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random, boolean withoutZeroAndO) {
        this(length, random, withoutZeroAndO ? alphaNumWithoutZeroAndO : alphaNum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length, boolean withoutZeroAndO) {
        this(length, new SecureRandom(), withoutZeroAndO);
    }
}
