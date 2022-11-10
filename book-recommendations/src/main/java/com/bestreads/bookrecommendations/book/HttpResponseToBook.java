package com.bestreads.bookrecommendations.book;

import com.bestreads.bookrecommendations.googlebooks.Item;

import java.net.http.HttpResponse;

import java.util.List;

public interface HttpResponseToBook {

    List<Book> extractFromHttpResponse(HttpResponse<String> httpResponse);

    Item extractBookFromHttpResponse(HttpResponse<String> httpResponse);
}
