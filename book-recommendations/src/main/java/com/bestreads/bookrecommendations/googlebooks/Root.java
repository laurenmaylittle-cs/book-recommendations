package com.bestreads.bookrecommendations.googlebooks;

import java.util.ArrayList;

/**
 * Represent the Root of the Json response obtained from the Google Books API
 */
public record Root(String kind,
                   int totalItems,
                   ArrayList<Item> items){

}
