package com.bestreads.bookrecommendations.bookshelf;

/**
 * Went down a rabbit hole to find this fix! This is a projection to get the collections by book
 * ISBN. I tried different approaches but all ended up fetching duplicate book for each collection
 * that it belong to. By defining this interface JPA will only fetch the fields specified in the
 * interface, and it will avoid fetching additional data that is not needed. In this case, JPA will
 * only fetch the fields specified in the CollectionProjection interface, and it will avoid fetching
 * the additional books.
 */
interface CollectionProjection {

  Long getId();

  String getName();

}
