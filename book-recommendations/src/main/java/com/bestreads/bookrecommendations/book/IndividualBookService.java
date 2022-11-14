package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import com.bestreads.bookrecommendations.googlebooks.Item;
import com.bestreads.bookrecommendations.utils.SearchTermUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
class IndividualBookService {

    private final GoogleBooksService googleBooksService;
    private final HttpResponseToBook httpResponseToBook;

    @Autowired
    IndividualBookService(GoogleBooksService googleBooksService, HttpResponseToBook httpResponseToBook) {
        this.googleBooksService = googleBooksService;
        this.httpResponseToBook = httpResponseToBook;
    }

    Item viewBook(String id) {
        HttpResponse<String> httpResponse = googleBooksService.getVolumeById(
                SearchTermUtils.encodeURLTerm(id),
                1
        );

        return httpResponseToBook.extractBookFromHttpResponse(httpResponse);
    }


}
