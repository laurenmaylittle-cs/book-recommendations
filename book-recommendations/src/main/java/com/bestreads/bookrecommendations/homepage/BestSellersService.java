package com.bestreads.bookrecommendations.homepage;

import com.bestreads.bookrecommendations.nytimesapi.Category;
import com.bestreads.bookrecommendations.nytimesapi.NyTimesHttpResponseToBook;
import com.bestreads.bookrecommendations.nytimesapi.NyTimesService;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BestSellersService {

  private final NyTimesService nyTimesService; //test
  private final NyTimesHttpResponseToBook nyTimesHttpResponseToBook;

  @Autowired
  BestSellersService(NyTimesService nyTimesService,
      NyTimesHttpResponseToBook nyTimesHttpResponseToBook) {
    this.nyTimesService = nyTimesService;
    this.nyTimesHttpResponseToBook = nyTimesHttpResponseToBook;
  }

  List<Category> getBestSellers() {
    HttpResponse<String> httpResponse = nyTimesService.getCurrentBestSellers();
    return nyTimesHttpResponseToBook.extractFromHttpResponse(httpResponse);
  }

}
