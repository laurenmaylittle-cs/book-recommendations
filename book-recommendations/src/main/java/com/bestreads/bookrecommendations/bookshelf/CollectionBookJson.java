package com.bestreads.bookrecommendations.bookshelf;

/**
 * From frontend, collections are associated through the use of a checkbox for each collection.
 * Frontend updates a boolean value related to the collection. This is the json representation of
 * the object that is used by the frontend to update the collections.
 */
record CollectionBookJson(Long id, String name, Boolean enabled) {

}
