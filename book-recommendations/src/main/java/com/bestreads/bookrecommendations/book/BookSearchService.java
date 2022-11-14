package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import com.bestreads.bookrecommendations.utils.SearchTermUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookSearchService {

    private final GoogleBooksService googleBooksService;
    private final HttpResponseToBook httpResponseToBook;

    @Autowired
    public BookSearchService(GoogleBooksService googleBooksService, HttpResponseToBook httpResponseToBook) {
        this.googleBooksService = googleBooksService;
        this.httpResponseToBook = httpResponseToBook;
    }

    public List<Book> searchByTitle(String searchTerm, int maxResults) {
        return searchByTitle(searchTerm, 0, maxResults);
    }

    public List<Book> searchByTitle(String searchTerm, int startIndex, int maxResults) {
        if (maxResults < 0 || maxResults > 40) {
            throw new IllegalArgumentException("maxResults should be between 0 and 40");
        }

        HttpResponse<String> httpResponse = googleBooksService.searchVolumeByTitle(
                SearchTermUtils.encodeURLTerm(searchTerm),
                startIndex,
                maxResults
        );

        return httpResponseToBook.extractFromHttpResponse(httpResponse);
    }

    public List<Book> searchByAuthor(String searchTerm, int maxResults) {
        return searchByAuthor(searchTerm, 0, maxResults);
    }

    public List<Book> searchByAuthor(String searchTerm, int startIndex, int maxResults) {
        HttpResponse<String> httpResponse = googleBooksService.searchVolumeByAuthor(
                SearchTermUtils.encodeURLTerm(searchTerm),
                startIndex,
                maxResults
        );

        return httpResponseToBook.extractFromHttpResponse(httpResponse);
    }
}
