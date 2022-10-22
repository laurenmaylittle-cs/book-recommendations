package com.bestreads.bookrecommendations.nytimesapi;

import java.util.ArrayList;

record Results(String bestsellers_date, String published_date, String published_date_description,
               String previous_published_date, String next_published_date, ArrayList<List> lists) {

}
//record Results(String bestsellers_date, String published_date, String published_date_description,
//               String previous_published_date, String next_published_date) {
//
//}
