package com.bestreads.bookrecommendations.bookshelf;

import java.util.Set;

/**
 * See the comment left on CollectionProjection for why this exists
 */
interface CollectionBookProjection {

  Long getId();

  String getName();

  Set<BookDAO> getBookDAOS();

}
