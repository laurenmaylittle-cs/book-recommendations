package com.bestreads.bookrecommendations.homepage;

import com.bestreads.bookrecommendations.book.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
class HomePageRestController {

  private final BestSellersService bestSellersService;

  @Autowired
  HomePageRestController(BestSellersService bestSellersService) {
    this.bestSellersService = bestSellersService;
  }

  @GetMapping("/best-sellers")
  public List<Book> getLIstOfBestSellers() {
    return bestSellersService.getBestSellers();
  }

}
