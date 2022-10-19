package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
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
        HttpResponse<String> httpResponse = googleBooksService.searchVolumeByTitle(
                encodeSearchTerm(searchTerm),
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
                encodeSearchTerm(searchTerm),
                startIndex,
                maxResults
        );

        return httpResponseToBook.extractFromHttpResponse(httpResponse);
    }

    private String encodeSearchTerm(String searchTerm) {
        return URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
    }
}
