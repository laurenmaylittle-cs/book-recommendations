package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.book.Book;
import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.book.BookDAOService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalizeevents.PersonalizeEventsClient;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import software.amazon.awssdk.services.personalizeevents.model.Event;
import software.amazon.awssdk.services.personalizeevents.model.PutEventsRequest;
import software.amazon.awssdk.services.personalizeevents.model.PersonalizeEventsException;
import software.amazon.awssdk.services.personalizeevents.model.Item;
import software.amazon.awssdk.services.personalizeevents.model.PutItemsRequest;
import software.amazon.awssdk.services.personalizeevents.model.User;
import software.amazon.awssdk.services.personalizeevents.model.PutUsersRequest;

import java.time.Instant;

import static java.lang.String.join;

@Service
class AwsPersonalizeService {

  private final BookDAOService bookDAOService;
  private final PersonalizeRuntimeClient personalizeRuntimeClient;
  private final PersonalizeEventsClient personalizeEventsClient;

  @Value("${AWS_CAMPAIGN_ARN}")
  private String campaignArn;

  @Value("${AWS_EVENT_TRACKER_ID}")
  private String eventTrackerId;

  @Value("${AWS_BOOKS_DATASET_ARN}")
  private String booksDatasetArn;

  @Value("${AWS_USERS_DATASET_ARN}")
  private String usersDatasetArn;

  @Autowired
  AwsPersonalizeService(BookDAOService bookDAOService, PersonalizeRuntimeClient personalizeRuntimeClient,
                        PersonalizeEventsClient personalizeEventsClient) {
    this.bookDAOService = bookDAOService;
    this.personalizeRuntimeClient = personalizeRuntimeClient;
    this.personalizeEventsClient = personalizeEventsClient;
  }

  public List<BookDAO> getRecommendations(String selectedIsbn) {
    var isbns = getListOfIsbns(selectedIsbn);
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

  public void putEvents(String sessionId, String userId, String itemId) {

    try {
      Event event = Event.builder()
              .sentAt(Instant.ofEpochMilli(System.currentTimeMillis() + 10 * 60 * 1000))
              .itemId(itemId)
              .eventType("CLICK")
              .build();

      PutEventsRequest putEventsRequest = PutEventsRequest.builder()
              .trackingId(eventTrackerId)
              .userId(userId)
              .sessionId(sessionId)
              .eventList(event)
              .build();

      personalizeEventsClient.putEvents(putEventsRequest);

    } catch (PersonalizeEventsException e) {
      throw new RuntimeException("Unable to add event to the interactions dataset. It is likely an invalid sessionId provided.");
    }
  }

  public void putItems(Book book) {

    var authors = join("/", book.authors());
    var categories = join("/", book.categories());

    ArrayList<Item> items = new ArrayList<>();

    try {
      Item item1 = Item.builder()
              .itemId(book.isbn())
              .properties(String.format("{\"%1$s\": \"%2$s\",\"%3$s\": \"%4$s\",\"%5$s\": \"%6$s\",\"%7$s\": \"%8$s\",\"%9$s\": \"%10$s\"}",
                      "itemName", book.title(),
                      "itemAuthor", authors,
                      "itemGenre", categories,
                      "itemPublisher", book.publisher(),
                      "itemThumbnail", book.imageLinks().thumbnail()))
              .build();

      items.add(item1);

      PutItemsRequest putItemsRequest = PutItemsRequest.builder()
              .datasetArn(booksDatasetArn)
              .items(items)
              .build();

      personalizeEventsClient.putItems(putItemsRequest);

    } catch (PersonalizeEventsException e) {
      throw new RuntimeException("Unable to add item to the items dataset. It is likely there are invalid Item properties.");
    }
  }

  public void putUsers(String id, com.bestreads.bookrecommendations.users.User userToAdd) {

    ArrayList<User> users = new ArrayList<>();

    try {
      User user = User.builder()
              .userId(id)
              .properties(String.format("{\"%1$s\": \"%2$s\",\"%3$s\": \"%4$s\",\"%5$s\": \"%6$s\"}",
                      "userName", userToAdd.name(),
                      "userEmail", userToAdd.email(),
                      "userVerified", Boolean.toString(userToAdd.emailVerified()).toUpperCase()))
              .build();

      users.add(user);

      PutUsersRequest putUsersRequest = PutUsersRequest.builder()
              .datasetArn(usersDatasetArn)
              .users(users)
              .build();

      personalizeEventsClient.putUsers(putUsersRequest);

    } catch (PersonalizeEventsException e) {
      throw new RuntimeException("Unable to add user to the users dataset. It is likely there are invalid User properties.");
    }
  }
}
