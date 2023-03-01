package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
class CollectionsService {

  private final CollectionsRepository collectionsRepository;

  @Autowired
  CollectionsService(CollectionsRepository collectionsRepository) {
    this.collectionsRepository = collectionsRepository;
  }

  LinkedHashSet<CollectionJson> getCollections(String userId) {
    return collectionsRepository.findByUserId(userId)
        .stream().map(x -> new CollectionJson(x.getId(), x.getName()))
        .sorted(comparing(CollectionJson::name))
        .collect(toCollection(LinkedHashSet::new));
  }

  Optional<CollectionDAO> getCollectionById(Long collectionId) {
    return collectionsRepository.findById(collectionId);
  }

  @Transactional
  CollectionDAO createNewCollection(String userId, String name) {
    var collection = new CollectionDAO();
    collection.setName(name);
    collection.setUserId(userId);
    return collectionsRepository.save(collection);
  }

  boolean collectionBelongsToUser(String userId, Long collectionId) {
    return collectionsRepository.findByIdAndUserId(collectionId, userId).isPresent();
  }

  @Transactional
  void deleteBooksFromCollection(Long bookshelfId, List<Long> bookIds) {
    var collection = collectionsRepository.findById(bookshelfId)
            .orElseThrow(() -> new IllegalArgumentException("No bookshelf with such ID"));
    var remainingBooks = collection.getBookDAOS()
            .stream()
            .filter(bookDAO -> !bookIds.contains(Long.parseLong(bookDAO.getIsbn())))
            .collect(Collectors.toSet());
    collection.setBookDaos(remainingBooks);
    collectionsRepository.save(collection);
  }

  List<BookDAO> getBooksInCollectionByIdAndUserOrBadRequest(Long bookshelfId, String userId) {
    return collectionsRepository.findByIdAndUserId(bookshelfId, userId).orElseThrow(() -> {
                      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection found");
                    }
            )
            .getBookDAOS()
            .stream()
            .toList();
  }
}
