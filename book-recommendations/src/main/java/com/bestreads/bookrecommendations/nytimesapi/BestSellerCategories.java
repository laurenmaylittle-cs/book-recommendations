package com.bestreads.bookrecommendations.nytimesapi;

public enum BestSellerCategories {
  HARDCOVER_FICTION("Hardcover Fiction"),
  HARDCOVER_NONFICTION("Hardcover Nonfiction"),
  TRADE_FICTION_PAPERBACK("trade-fiction-paperback");

  private final String category;

  BestSellerCategories(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }
}
