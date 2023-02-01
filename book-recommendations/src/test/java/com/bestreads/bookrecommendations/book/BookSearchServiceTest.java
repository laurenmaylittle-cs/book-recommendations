package com.bestreads.bookrecommendations.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
    assertThrows(IllegalArgumentException.class,
        () -> bookSearchService.searchByTitle(searchTerm, maxResults));
  }

  @Test
  void searchByTitle_withMaxResultsNegative_assertException() {
    var searchTerm = "Dan Ashworth";
    var maxResults = -1;
    assertThrows(IllegalArgumentException.class,
        () -> bookSearchService.searchByTitle(searchTerm, maxResults));
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

  @Test
  void searchByIsbn_withMaxResults_verifyServiceCall() {
    var searchTerm = "9781302931100";
    var maxResults = 12;
    bookSearchService.searchByIsbn(searchTerm, maxResults);

    var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
    verify(googleBooksService).searchVolumeByIsbn(encodedSearchTerm, 0, maxResults);
  }

  @Test
  void searchByIsbn_withStartIndex_verifyServiceCall() {
    var searchTerm = "9780547928210";
    var maxResults = 39;
    var startIndex = 10;
    bookSearchService.searchByIsbn(searchTerm, startIndex, maxResults);

    var encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
    verify(googleBooksService).searchVolumeByIsbn(encodedSearchTerm, startIndex, maxResults);
  }

  @Test
  void searchByIsbn_withMaxResultsOverload_assertException() {
    var searchTerm = "9781302945534";
    var maxResults = 41;
    assertThrows(IllegalArgumentException.class,
            () -> bookSearchService.searchByIsbn(searchTerm, maxResults));
  }

  @Test
  void searchByIsbn_withMaxResultsNegative_assertException() {
    var searchTerm = "9781779501127";
    var maxResults = -1;
    assertThrows(IllegalArgumentException.class,
            () -> bookSearchService.searchByIsbn(searchTerm, maxResults));
  }

  @Test
  void viewIndividualBook_withISBNReturnsABook() {
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    String ISBN = "9780297859406";
    when(googleBooksService.getVolumeByIsbn(ISBN, 1)).thenReturn(httpResponse);

    var expectedBook = new Book(
        "id",
        "Gone Girl",
        List.of("Gillian Flynn"),
        "Hachette UK",
        "2012-05-24",
        "Test Description",
        0,
        new ArrayList<>(),
        new ImageLinks("smallThumbnail", "largeThumbnail"),
        "en",
        0,
        0,
        "9780735211292"
    );
    when(httpResponseToBook.extractFromHttpResponse(httpResponse)).thenReturn(
        List.of(expectedBook));

    var receivedBook = bookSearchService.getBookByIsbn(ISBN);
    assertEquals(expectedBook, receivedBook);
  }

  @Test
  void viewIndividualBook_ThrowsExceptionWhenISBNIsInvalid() {
    var exception = assertThrows(IllegalArgumentException.class,
        () -> bookSearchService.getBookByIsbn("INVALID_ISBN"));
    assertEquals("INVALID_ISBN is an invalid ISBN.", exception.getMessage());
  }

}