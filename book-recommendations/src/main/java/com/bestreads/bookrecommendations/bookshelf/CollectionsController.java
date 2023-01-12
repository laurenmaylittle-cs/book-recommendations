package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.json.CollectionJson;
import com.bestreads.bookrecommendations.helper.AuthHelper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
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
  public ResponseEntity<Set<CollectionJson>> getCollections(
      JwtAuthenticationToken authenticationToken) {
    var userId = AuthHelper.getUserId(authenticationToken);

    if (userId.isPresent()) {
      return ResponseEntity.ok(collectionsService.getCollections(userId.get()));
    } else {
      return ResponseEntity.badRequest().build();
    }

  }

  @GetMapping("/{Id}")
  public CollectionJson getCollectionById(@PathVariable Long Id) {
    var collection = collectionsService.getCollectionById(Id);
    return new CollectionJson(collection.getId(), collection.getName());
  }

  @PostMapping
  public ResponseEntity createCollection(JwtAuthenticationToken authenticationToken,
      @RequestParam String name, HttpServletRequest httpServletRequest)
      throws URISyntaxException {

    var userId = AuthHelper.getUserId(authenticationToken);

    if (userId.isEmpty()) {
      return new ResponseEntity<>("User id not found in token", HttpStatus.BAD_REQUEST);
    }

    var createdCollection = collectionsService.createNewCollection(userId.get(), name);
    var location = new URI(httpServletRequest.getRequestURL() + "/" + createdCollection.getId());
    return ResponseEntity.created(location).build();
  }
}
