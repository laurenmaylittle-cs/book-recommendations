package com.bestreads.bookrecommendations.bookshelf;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import com.bestreads.bookrecommendations.utils.AuthUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/bookshelf/collections")
public class CollectionsController {

  private final CollectionsService collectionsService;

  @Autowired
  public CollectionsController(CollectionsService collectionsService) {
    this.collectionsService = collectionsService;
  }

  @GetMapping
  public ResponseEntity<LinkedHashSet<CollectionJson>> getCollections(
      JwtAuthenticationToken authenticationToken) {
    var userId = AuthUtils.getUserId(authenticationToken);

    return userId.map(s -> ok(collectionsService.getCollections(s)))
        .orElseGet(() -> badRequest().build());
  }

  @GetMapping("/{Id}")
  public ResponseEntity<CollectionJson> getCollectionById(@PathVariable Long Id) {
    var collection = collectionsService.getCollectionById(Id);

    return collection.map(collectionDAO -> ok(
            new CollectionJson(collectionDAO.getId(), collectionDAO.getName())))
        .orElseGet(() -> notFound().build());
  }

  @PostMapping
  public ResponseEntity createCollection(JwtAuthenticationToken authenticationToken,
      @RequestParam String name, HttpServletRequest httpServletRequest)
      throws URISyntaxException {

    var userId = AuthUtils.getUserId(authenticationToken);

    if (userId.isEmpty()) {
      return new ResponseEntity<>("User id not found in token", HttpStatus.BAD_REQUEST);
    }

    var createdCollection = collectionsService.createNewCollection(userId.get(), name);
    var location = new URI(httpServletRequest.getRequestURL() + "/" + createdCollection.getId());
    return created(location).build();
  }
}
