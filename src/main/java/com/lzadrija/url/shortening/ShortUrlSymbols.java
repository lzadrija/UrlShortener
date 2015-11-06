package com.lzadrija.url.shortening;

import java.util.List;
import java.util.stream.Collectors;

class ShortUrlSymbols {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    private final char[] symbols;

    private ShortUrlSymbols(char[] symbols) {
        this.symbols = symbols;
    }

    static ShortUrlSymbols generate() {
        return new ShortUrlSymbols(generateSymbols());
    }

    private static char[] generateSymbols() {
        return new StringBuilder().append(ALPHABET).append(ALPHABET.toUpperCase()).append(DIGITS)
                .toString().toCharArray();
    }

    List<Character> forDigits(List<Integer> digits) {
        return digits.stream().map(d -> symbols[d]).collect(Collectors.toList());
    }

    int getBase() {
        return symbols.length;
    }
}
