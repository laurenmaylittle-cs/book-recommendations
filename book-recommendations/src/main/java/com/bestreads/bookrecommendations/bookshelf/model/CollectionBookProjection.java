package com.bestreads.bookrecommendations.bookshelf.model;

import java.util.Set;

/**
 * See the comment left on CollectionProjection for why this exists
 */
public interface CollectionBookProjection {

  Long getId();

  String getName();

  Set<BookDAO> getBookDAOS();

}
