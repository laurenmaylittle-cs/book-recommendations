package com.bestreads.bookrecommendations.nytimesapi;

import com.fasterxml.jackson.annotation.JsonProperty;

record Root(String status,
            String copyright,
            @JsonProperty("num_results") int numResults,
            Results results) {

}

