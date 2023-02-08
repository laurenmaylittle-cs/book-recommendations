package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.GoogleBooksService;
import com.bestreads.bookrecommendations.utils.SearchTermUtils;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService {

  private final GoogleBooksService googleBooksService;
  private final HttpResponseToBook httpResponseToBook;

  @Autowired
  public BookSearchService(GoogleBooksService googleBooksService,
      HttpResponseToBook httpResponseToBook) {
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

  public List<Book> searchByIsbn(String searchTerm, int maxResults) {
    return searchByIsbn(searchTerm, 0, maxResults);
  }

  public List<Book> searchByIsbn(String searchTerm, int startIndex, int maxResults) {
    if (maxResults < 0 || maxResults > 40) {
      throw new IllegalArgumentException("maxResults should be between 0 and 40");
    }

    HttpResponse<String> httpResponse = googleBooksService.searchVolumeByIsbn(
        SearchTermUtils.encodeURLTerm(searchTerm),
        startIndex,
        maxResults
    );

    return httpResponseToBook.extractFromHttpResponse(httpResponse);
  }

  public Book getBookData(String isbn, String title, String authors) {
    HttpResponse<String> httpResponse = googleBooksService.searchVolumeByIsbn(
        SearchTermUtils.encodeURLTerm(isbn),
        0,
        1
    );

    List<Book> books = httpResponseToBook.extractFromHttpResponse(httpResponse);

    if (!books.isEmpty() && books.size() == 1) {
      return books.get(0);
    } else {
      return searchByTitleAndAuthor(title, authors);
    }

  }

  /**
   * Returns the first book found with the given title and author
   *
   * @throws IllegalArgumentException if no book is found for given title and authors
   */
  private Book searchByTitleAndAuthor(String title, String authors) {
    HttpResponse<String> httpResponse = googleBooksService.getVolumeByTitleAndAuthors(
        SearchTermUtils.encodeURLTerm(title),
        SearchTermUtils.encodeURLTerm(authors)
    );

    List<Book> books = httpResponseToBook.extractFromHttpResponse(httpResponse);

    if (books.size() > 0) {
      return books.get(0);
    } else {
      throw new IllegalArgumentException(
          "No book found with title: %s and author: %s".formatted(title, authors));
    }
  }
}
