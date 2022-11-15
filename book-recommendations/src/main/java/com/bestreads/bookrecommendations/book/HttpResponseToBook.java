package com.bestreads.bookrecommendations.book;


import java.net.http.HttpResponse;

import java.util.List;

public interface HttpResponseToBook {

    List<Book> extractFromHttpResponse(HttpResponse<String> httpResponse);

}
