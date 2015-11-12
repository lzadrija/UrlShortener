package com.lzadrija.url.shortening;

import com.lzadrija.url.UrlRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlShortener {

    private final ShortUrlSymbols symbols = ShortUrlSymbols.generate();
    private final UrlRepository repo;

    @Autowired
    public UrlShortener(UrlRepository repo) {
        this.repo = repo;
    }

    public String createShortUrl() {

        List<Integer> digits = convertHashToDigitsInSymbolsBase(createHash());
        return encodeDigitsToBaseSymbols(digits);
    }

    private long createHash() {
        return repo.count();
    }

    private List<Integer> convertHashToDigitsInSymbolsBase(long hash) {

        List<Integer> digits = new ArrayList<>();

        if (hash == 0) {
            digits.add(0);
        }
        digits.addAll(convertHashToDigits(hash));

        Collections.reverse(digits);
        return digits;
    }

    private List<Integer> convertHashToDigits(long hash) {

        List<Integer> digits = new ArrayList<>();
        while (hash != 0) {
            long reminder = hash % symbols.getBase();
            hash = hash / symbols.getBase();

            digits.add((int) reminder);
        }
        return digits;
    }

    private String encodeDigitsToBaseSymbols(List<Integer> urlBaseDigits) {

        List<Character> baseSymbols = symbols.forDigits(urlBaseDigits);

        StringBuilder result = new StringBuilder(baseSymbols.size());
        baseSymbols.forEach(c -> result.append(c));
        return result.toString();
    }
}
