package com.bestreads.bookrecommendations.nytimesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

record Results(String bestsellers_date, String published_date, String published_date_description,
               String previous_published_date, String next_published_date,
               @JsonProperty("lists") ArrayList<Category> categories) {

}
