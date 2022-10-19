package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    private GoogleBooksService googleBooksService;

    @Mock
    private HttpResponseToBook httpResponseToBook;

    @InjectMocks
    private BookSearchService bookSearchService;

    @Test
    void searchByTitle_withMaxResults_verifyServiceCall() {
        var searchTerm = "Harry Potter";
        var maxResults = 42;
        bookSearchService.searchByTitle(searchTerm, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByTitle(encodedSearchTerm, 0, maxResults);
    }

    @Test
    void searchByTitle_withStartIndex_verifyServiceCall() {
        var searchTerm = "Tales of a Mexican Genius";
        var maxResults = 42;
        var startIndex = 10;
        bookSearchService.searchByTitle(searchTerm, startIndex, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByTitle(encodedSearchTerm, startIndex, maxResults);
    }

    @Test
    void searchByAuthor_withMaxResults_verifyServiceCall() {
        var searchTerm = "Dan Ashworth";
        var maxResults = 42;
        bookSearchService.searchByAuthor(searchTerm, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByAuthor(encodedSearchTerm, 0, maxResults);
    }

    @Test
    void searchByAuthor_withStartIndex_verifyServiceCall() {
        var searchTerm = "Mr Brightside & Cheese";
        var maxResults = 42;
        var startIndex = 1;
        bookSearchService.searchByAuthor(searchTerm, startIndex, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByAuthor(encodedSearchTerm, startIndex, maxResults);
    }
}