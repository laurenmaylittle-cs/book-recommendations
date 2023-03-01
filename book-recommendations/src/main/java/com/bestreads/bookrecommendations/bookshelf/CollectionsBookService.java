package com.bestreads.bookrecommendations.bookshelf;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.book.BookDAOService;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CollectionsBookService {

  private final CollectionsRepository collectionsRepository;

  private final BookDAOService bookDAOService;

  @Autowired
  CollectionsBookService(CollectionsRepository collectionsRepository,
      BookDAOService bookDAOService) {
    this.collectionsRepository = collectionsRepository;
    this.bookDAOService = bookDAOService;
  }

  @Transactional
  LinkedHashSet<CollectionBookJson> getCollectionsForBook(String userId, String isbn) {
    LinkedHashSet<CollectionBookJson> allCollections = new LinkedHashSet<>();
    allCollections.addAll(getCollectionsForBookHelper(userId, isbn, true));
    allCollections.addAll(getCollectionsForBookHelper(userId, isbn, false));
    return allCollections;
  }

  @Transactional
  Set<CollectionBookJson> updateCollectionsForBook(String userId,
      CollectionBookRootJson collectionBookRootJson, String isbn) {
    var book = bookDAOService.findBookDAOByISBN(isbn)
        .orElseGet(() -> bookDAOService.addNewBook(collectionBookRootJson.book()));

    var newCollections = createNewCollections(collectionBookRootJson.collections(), userId, book);
    var updatedCollections = updateExistingCollections(
        collectionsRepository.findAllCollectionByUserId(userId),
        collectionBookRootJson.collections(), book);

    var savedCollections = collectionsRepository.saveAll(
        concat(newCollections.stream(), updatedCollections.stream())
            .collect(toSet()));

    return StreamSupport.stream(savedCollections.spliterator(), false)
        .map(collectionDAO -> new CollectionBookJson(collectionDAO.getId(), collectionDAO.getName(),
            collectionDAO.getBookDaos().contains(book)))
        .collect(toSet());
  }

  private LinkedHashSet<CollectionBookJson> getCollectionsForBookHelper(String userId, String isbn,
      boolean associatedToBook) {
    var collectionsList = associatedToBook ?
        collectionsRepository.findByUserIdAndBookDAOS_Isbn(userId, isbn) :
        collectionsRepository.findUnassociatedCollectionsForIsbn(userId,
            isbn);

    return collectionsList.stream()
        .map(x -> new CollectionBookJson(x.getId(), x.getName(), associatedToBook))
        .sorted(comparing(CollectionBookJson::name))
        .collect(toCollection(LinkedHashSet::new));
  }

  private Set<CollectionDAO> createNewCollections(List<CollectionBookJson> collectionBookJsons,
      String userId, BookDAO book) {
    return collectionBookJsons.stream()
        .filter(cb -> cb.id() < 0) //new collections have negative ids (assigned by frontend)
        .map(cb -> new CollectionDAO(cb.name(), userId, cb.enabled() ? Set.of(book) : Set.of()))
        .collect(toSet());
  }

  private Set<CollectionDAO> updateExistingCollections(Set<CollectionDAO> existingCollections,
      List<CollectionBookJson> collectionBookJsons, BookDAO book) {
    return existingCollections.stream()
        .map(collectionDAO -> updateCollectionDAO(collectionDAO, collectionBookJsons, book))
        .collect(toSet());
  }

  private CollectionDAO updateCollectionDAO(CollectionDAO collectionDAO,
      List<CollectionBookJson> collectionBookJsons, BookDAO book) {
    //find the collectionBookJson that matches the collectionDAO
    var collectionBook = collectionBookJsons.stream()
        .filter(cb -> Objects.equals(cb.id(), collectionDAO.getId()))
        .findFirst();
    var enabled = collectionBook.map(CollectionBookJson::enabled).orElse(false);
    if (enabled) {
      collectionDAO.getBookDaos().add(book);
    } else {
      collectionDAO.getBookDaos().remove(book);
    }
    return collectionDAO;
  }

}
