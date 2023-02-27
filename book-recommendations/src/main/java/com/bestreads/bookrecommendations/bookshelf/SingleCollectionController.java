package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/private/bookshelf/singleBookshelf")
public class SingleCollectionController {

  private final CollectionsRepository collectionsRepository;

  @Autowired
  public SingleCollectionController(CollectionsRepository collectionsRepository) {
    this.collectionsRepository = collectionsRepository;
  }

  @GetMapping
  public List<BookDAO> getBooksInCollection(JwtAuthenticationToken authenticationToken,
      @Param("bookshelfId") Long bookshelfId) {
    var userId = AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
    });

    return collectionsRepository.findByIdAndUserId(bookshelfId, userId).orElseThrow(() -> {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection found");
    }).getBookDAOS().stream().toList();
  }
}
