package com.bestreads.bookrecommendations.nytimesapi;

record Root(String status,
            String copyright,
            int num_results,
            Results results) {

}

