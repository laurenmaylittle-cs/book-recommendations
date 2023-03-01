package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/private/bookshelf/books")
public class IndividualBookshelfController {

  private final CollectionsService collectionsService;

  @Autowired
  public IndividualBookshelfController(CollectionsService collectionsService) {
    this.collectionsService = collectionsService;
  }

  @GetMapping
  public CollectionBookProjection getBooksInCollection(JwtAuthenticationToken authenticationToken,
      @Param("bookshelfId") Long bookshelfId) {
    var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    return collectionsService.findByIdAndUser(bookshelfId, userId)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection found");
        });
  }

  @DeleteMapping("/delete")
  public List<BookDAO> deleteBookInCollection(JwtAuthenticationToken authenticationToken,
      @RequestParam Long bookshelfId,
      @RequestParam List<Long> bookIds) {
    var userId = AuthUtils.getUserIdOrBadRequest(authenticationToken);
    if (!collectionsService.collectionBelongsToUser(userId, bookshelfId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection for user found");
    }

    collectionsService.deleteBooksFromCollection(bookshelfId, bookIds);
    return collectionsService.getBooksInCollectionByIdAndUserOrBadRequest(bookshelfId, userId);
  }

  @PutMapping
  public void updateCollectionName(JwtAuthenticationToken authenticationToken,
      @Param("collectionId") Long bookshelfId,
      @Param("newCollectionName") String newCollectionName) {
    AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    collectionsService.updateCollectionName(bookshelfId, newCollectionName);
  }
}
