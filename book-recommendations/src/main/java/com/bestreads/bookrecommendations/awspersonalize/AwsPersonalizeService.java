package com.bestreads.bookrecommendations.awspersonalize;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalize.PersonalizeClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;

import static java.lang.String.join;


@Service
class AwsPersonalizeService {

  private final BookEntityRepository bookEntityRepository;
  private final PersonalizeClient personalizeClient;
  private final PersonalizeRuntimeClient personalizeRuntimeClient;

  @Value("${AWS_CAMPAIGN_ARN}")
  private String campaignArn;

  @Autowired
  AwsPersonalizeService(BookEntityRepository bookEntityRepository, PersonalizeClient personalizeClient, PersonalizeRuntimeClient personalizeRuntimeClient) {
    this.bookEntityRepository = bookEntityRepository;
    this.personalizeClient = personalizeClient;
    this.personalizeRuntimeClient = personalizeRuntimeClient;
  }

  @Transactional
  public void addBookToDb(Book bookToAdd) {
    var authors = join("/", bookToAdd.authors());
    var genres = join("/", bookToAdd.categories());

    var book = new BookDAO();
    book.setTitle(bookToAdd.title());
    book.setIsbn(bookToAdd.isbn());
    book.setAuthor(authors);
    book.setGenre(genres);
    book.setThumbnail(bookToAdd.imageLinks().thumbnail());
    book.setPublisher(bookToAdd.publisher());

    var currentBook = bookEntityRepository.findByIsbn(bookToAdd.isbn());
    if (currentBook.isEmpty()) {
      bookEntityRepository.save(book);
    }
  }

  public List<BookDAO> getRecs(String itemId) {
    var isbns = getListOfIsbns(itemId);
    var books = new ArrayList<BookDAO>();

    for (String isbn : isbns) {
      var book = bookEntityRepository.findByIsbn(isbn);
      book.ifPresent(books::add);
      if (books.size() == 5) {
        break;
      }
    }
    return books;
  }

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
      System.err.println(e.awsErrorDetails().errorMessage());
    }
    return isbns;
  }
}
