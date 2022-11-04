package com.bestreads.bookrecommendations.nytimesapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.ImageLinks;
import java.net.http.HttpResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NyTimesHttpResponseToBookTest {

  HttpResponse httpResponse = mock(HttpResponse.class);

  private NyTimesHttpResponseToBook nyTimesHttpResponseToBook;

  @BeforeEach
  void setUp() {
    nyTimesHttpResponseToBook = new NyTimesHttpResponseToBook();

  }

  @Test
  void extractFromHttpResponse_returnsEmptyCollection() {
    when(httpResponse.statusCode()).thenReturn(400);

    assert (nyTimesHttpResponseToBook.extractFromHttpResponse(httpResponse)).isEmpty();
  }

  @Test
  void extractFromHttpResponse_returnsListOfCategories() {
    when(httpResponse.statusCode()).thenReturn(200);
    when(httpResponse.body()).thenReturn("""
        {
          "results": {
             "lists": [
                {
                  "list_id": 1,
                  "list_name": "Hardcover Fiction",
                  "books": [
                    {
                      "title": "The Midnight Library",
                      "author": "Matt Haig",
                      "publisher": "ABC publishing",
                      "description": "Some say it's a good book to read",
                      "book_image": "https://google.com/image.jpg"                   
                    }
                  ]
                }
             ]
           }
        }          
        """
    );

    List<Category> results = (List<Category>) nyTimesHttpResponseToBook.extractFromHttpResponse(httpResponse);

    assertThat(results)
        .extracting(Category::list_id, Category::list_name, Category::books)
        .containsExactly(
            tuple(1, "Hardcover Fiction",
                List.of(new Book("The Midnight Library", List.of("Matt Haig"), "ABC publishing",
                    "Some say it's a good book to read",
                    new ImageLinks(null, "https://google.com/image.jpg")))));

  }
}
