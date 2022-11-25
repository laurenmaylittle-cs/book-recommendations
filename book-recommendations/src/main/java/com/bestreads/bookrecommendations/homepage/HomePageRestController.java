package com.bestreads.bookrecommendations.homepage;

import com.bestreads.bookrecommendations.nytimesapi.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomePageRestController {

  private final BestSellersService bestSellersService;

  @Autowired
  public HomePageRestController(BestSellersService bestSellersService) {
    this.bestSellersService = bestSellersService;
  }

  @GetMapping("/best-sellers")
  public List<Category> getListOfBestSellers() {
    return bestSellersService.getBestSellers();
  }

}
