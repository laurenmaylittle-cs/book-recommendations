package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/private/bookshelf/collections/book")
public class CollectionsBookController {

  private final CollectionsBookService collectionsBookService;

  @Autowired
  public CollectionsBookController(CollectionsBookService collectionsBookService) {
    this.collectionsBookService = collectionsBookService;
  }

  /**
   * @return associated collections and unassociated collections for a book that belong to user
   */
  @GetMapping
  public LinkedHashSet<CollectionBookJson> getCollectionsForBook(
      JwtAuthenticationToken authenticationToken, @RequestParam String isbn) {
    var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id")
    );

    return collectionsBookService.getCollectionsForBook(userId, isbn);
  }

  @PutMapping
  public Set<CollectionBookJson> updateCollectionsForBook(
      JwtAuthenticationToken jwtAuthenticationToken,
      @RequestBody CollectionBookRootJson collectionsData) {

    var userId = AuthUtils.getUserId(jwtAuthenticationToken).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id")
    );

    return collectionsBookService.updateCollectionsForBook(userId, collectionsData);
  }
}
