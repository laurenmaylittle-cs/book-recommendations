package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.book.BookDAOService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalize.PersonalizeClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;


@Service
class AwsPersonalizeService {

  private final BookDAOService bookDAOService;
  private final PersonalizeClient personalizeClient;
  private final PersonalizeRuntimeClient personalizeRuntimeClient;


  @Value("${AWS_CAMPAIGN_ARN}")
  private String campaignArn;

  @Autowired
  AwsPersonalizeService(BookDAOService bookDAOService, PersonalizeClient personalizeClient,
      PersonalizeRuntimeClient personalizeRuntimeClient) {
    this.bookDAOService = bookDAOService;
    this.personalizeClient = personalizeClient;
    this.personalizeRuntimeClient = personalizeRuntimeClient;
  }

  @Transactional
  public void addBookToDb(Book bookToAdd) {
    var book = bookDAOService.findBookDAOByISBN(bookToAdd.isbn());
    if (book.isEmpty()) {
      bookDAOService.addNewBook(bookToAdd);
    }
  }

  public List<BookDAO> getRecommendations(String chosenBook) {
    var isbns = getListOfIsbns(chosenBook);
    var books = new ArrayList<BookDAO>();

    for (String isbn : isbns) {
      var book = bookDAOService.findBookDAOByISBN(isbn);
      book.ifPresent(books::add);
      if (books.size() == 5) {
        break;
      }
    }
    return books;
  }

  /**
   * This method takes in an isbn, connects to our campaign and returns a list of similar books.
   * @param itemId - isbn of the current book we want to get similar items of
   * @return a list of isbns that we can use to then display the similar items
   */
  private List<String> getListOfIsbns(String itemId) {
    var isbns = new ArrayList<String>();
    try {
      GetRecommendationsRequest recommendationsRequest = GetRecommendationsRequest.builder()
          .campaignArn(campaignArn)
          .numResults(20)
          .itemId(itemId)
          .build();
      GetRecommendationsResponse recommendationsResponse = personalizeRuntimeClient.getRecommendations(
          recommendationsRequest);
      List<PredictedItem> items = recommendationsResponse.itemList();

      for (PredictedItem item : items) {
        isbns.add(item.itemId());
      }

    } catch (AwsServiceException e) {
      throw new IllegalArgumentException("Invalid ISBN provided");
    }
    return isbns;
  }
}
