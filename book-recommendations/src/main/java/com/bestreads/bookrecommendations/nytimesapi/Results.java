package com.bestreads.bookrecommendations.nytimesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

record Results(@JsonProperty("bestsellers_date") String bestSellersDate,
               @JsonProperty("published_date") String publishedDate,
               @JsonProperty("published_date_description") String publishedDateDescription,
               @JsonProperty("previous_published_date") String previousPublishedDate,
               @JsonProperty("next_published_date") String nextPublishedDate,
               @JsonProperty("lists") ArrayList<Category> categories) {

}
