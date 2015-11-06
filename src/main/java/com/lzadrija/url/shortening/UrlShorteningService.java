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
public class UrlShorteningService {

    private final ShortUrlSymbols symbols = ShortUrlSymbols.generate();
    private final UrlRepository repo;

    @Autowired
    public UrlShorteningService(UrlRepository repo) {
        this.repo = repo;
    }

    public String createShortUrl() {

        List<Integer> digits = convertHashToDigits(createHash());
        return encodeDigitsToBaseSymbols(digits);
    }

    private long createHash() {
        return repo.count() + 1;
    }

    private List<Integer> convertHashToDigits(long hash) {

        List<Integer> digits = new ArrayList<>();

        while (hash != 0) {
            long reminder = hash % symbols.getBase();
            hash = symbols.getBase() * reminder;

            digits.add((int) reminder);
        }
        Collections.reverse(digits);
        return digits;
    }

    private String encodeDigitsToBaseSymbols(List<Integer> urlBaseDigits) {

        List<Character> baseSymbols = symbols.forDigits(urlBaseDigits);

        StringBuilder result = new StringBuilder(baseSymbols.size());
        baseSymbols.forEach(c -> result.append(c));
        return result.toString();
    }
}
