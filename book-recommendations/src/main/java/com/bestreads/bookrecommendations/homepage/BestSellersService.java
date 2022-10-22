package com.bestreads.bookrecommendations.homepage;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.HttpResponseToBook;
import com.bestreads.bookrecommendations.nytimesapi.NyTimesService;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BestSellersService {

  private final NyTimesService nyTimesService;
  private final HttpResponseToBook httpResponseToBook;

  @Autowired
  public BestSellersService(NyTimesService nyTimesService,
      @Qualifier("nyTimesHttpResponseToBook") HttpResponseToBook httpResponseToBook) {
    this.nyTimesService = nyTimesService;
    this.httpResponseToBook = httpResponseToBook;
  }

  public List<Book> getBestSellers() {

    HttpResponse<String> httpResponse = nyTimesService.getCurrentBestSellers();

    var books = httpResponseToBook.extractFromHttpResponse(httpResponse);
    return null;
  }
}
