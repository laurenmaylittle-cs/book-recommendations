package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        var maxResults = 12;
        bookSearchService.searchByTitle(searchTerm, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByTitle(encodedSearchTerm, 0, maxResults);
    }

    @Test
    void searchByTitle_withStartIndex_verifyServiceCall() {
        var searchTerm = "Tales of a Mexican Genius";
        var maxResults = 39;
        var startIndex = 10;
        bookSearchService.searchByTitle(searchTerm, startIndex, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByTitle(encodedSearchTerm, startIndex, maxResults);
    }

    @Test
    void searchByTitle_withMaxResultsOverload_assertException() {
        var searchTerm = "Dan Ashworth";
        var maxResults = 41;
        assertThrows(IllegalArgumentException.class, () -> bookSearchService.searchByTitle(searchTerm, maxResults));
    }

    @Test
    void searchByTitle_withMaxResultsNegative_assertException() {
        var searchTerm = "Dan Ashworth";
        var maxResults = -1;
        assertThrows(IllegalArgumentException.class, () -> bookSearchService.searchByTitle(searchTerm, maxResults));
    }

    @Test
    void searchByAuthor_withMaxResults_verifyServiceCall() {
        var searchTerm = "Dan Ashworth";
        var maxResults = 40;
        bookSearchService.searchByAuthor(searchTerm, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByAuthor(encodedSearchTerm, 0, maxResults);
    }

    @Test
    void searchByAuthor_withStartIndex_verifyServiceCall() {
        var searchTerm = "Mr Brightside & Cheese";
        var maxResults = 12;
        var startIndex = 1;
        bookSearchService.searchByAuthor(searchTerm, startIndex, maxResults);

        var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        verify(googleBooksService).searchVolumeByAuthor(encodedSearchTerm, startIndex, maxResults);
    }
}