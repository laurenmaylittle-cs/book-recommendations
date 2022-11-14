package com.bestreads.bookrecommendations.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchTermUtils {

    private SearchTermUtils() {
        throw new UtilsClassInitalisationException(this.getClass());
    }

    public static String encodeURLTerm(String urlTerm) {
        return URLEncoder.encode(urlTerm, StandardCharsets.UTF_8);
    }
}
