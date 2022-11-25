package com.bestreads.bookrecommendations.homepage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestreads.bookrecommendations.nytimesapi.NyTimesHttpResponseToBook;
import com.bestreads.bookrecommendations.nytimesapi.NyTimesService;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BestSellersServiceTest {

  @Mock
  private NyTimesService nyTimesService;

  @Mock
  private NyTimesHttpResponseToBook nyTimesHttpResponseToBook;

  @InjectMocks
  private BestSellersService bestSellersService;

  @Test
  void getBestSellers() {
    ArgumentCaptor<HttpResponse> httpResponseArgumentCaptor = ArgumentCaptor.forClass(HttpResponse.class);
    HttpResponse<String> httpResponse = mock(HttpResponse.class);
    when(nyTimesService.getCurrentBestSellers()).thenReturn(httpResponse);

    bestSellersService.getBestSellers();

    verify(nyTimesHttpResponseToBook).extractFromHttpResponse(httpResponseArgumentCaptor.capture());
    assertEquals(httpResponse, httpResponseArgumentCaptor.getValue());
  }
}

