package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/private/bookshelf/singleBookshelf")
public class SingleCollectionController {

  private final CollectionsService collectionsService;

  @Autowired
  public SingleCollectionController(CollectionsService collectionsService) {
    this.collectionsService = collectionsService;
  }

  @GetMapping
  public SingleCollection getBooksInCollection(JwtAuthenticationToken authenticationToken,
      @Param("bookshelfId") Long bookshelfId) {
    var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    var collectionDetails = collectionsService.findByIdAndUser(bookshelfId, userId)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection found");
        });
    ;

    var booksInCollection = collectionDetails.getBookDAOS()
        .stream()
        .toList();
    var collectionName = collectionDetails.getName();

    return new SingleCollection(collectionName, booksInCollection);
  }

  @PutMapping
  public void updateCollectionName(JwtAuthenticationToken authenticationToken,
      @Param("collectionId") Long bookshelfId,
      @Param("newCollectionName") String newCollectionName) {

    var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    collectionsService.updateCollectionName(bookshelfId, newCollectionName, userId);

  }
}
