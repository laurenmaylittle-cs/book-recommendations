package com.bestreads.bookrecommendations.bookshelf;

import static java.lang.String.join;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CollectionsService {

  private final CollectionsRepository collectionsRepository;

  private final BookDAORepository bookDAORepository;

  @Autowired
  CollectionsService(CollectionsRepository collectionsRepository, JdbcTemplate jdbcTemplate,
      BookDAORepository bookDAORepository) {
    this.collectionsRepository = collectionsRepository;
    this.bookDAORepository = bookDAORepository;
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

  @Transactional
  LinkedHashSet<CollectionBookJson> getCollectionsForBook(String userId, String isbn) {
    LinkedHashSet<CollectionBookJson> allCollections = new LinkedHashSet<>();
    allCollections.addAll(getCollectionsForBookHelper(userId, isbn, true));
    allCollections.addAll(getCollectionsForBookHelper(userId, isbn, false));
    return allCollections;
  }

  @Transactional
  public Set<CollectionBookJson> updateCollectionsForBook(String userId,
      CollectionBookRootJson collectionBookRootJson, String isbn) {

    var bookOptional = bookDAORepository.findBookDAOByIsbn(isbn);
    var book = new BookDAO();
    if (bookOptional.isEmpty()) {
      var bookJson = collectionBookRootJson.book();
      book.setIsbn(bookJson.isbn());
      book.setAuthor(join(", ", bookJson.authors()));
      book.setTitle(bookJson.title());
      book.setThumbnail(bookJson.imageLinks().thumbnail());
      book = bookDAORepository.save(book);
    }

    var collectionBookJsons = collectionBookRootJson.collections();

    BookDAO finalBook = bookOptional.orElse(book);
    Set<CollectionDAO> newCollections = collectionBookJsons.stream()
        .filter(collectionBookJson -> collectionBookJson.id() < 0)
        .map(collectionBookJson -> new CollectionDAO(collectionBookJson.name(), userId,
            collectionBookJson.enabled() ? Set.of(finalBook) : Set.of()))
        .collect(Collectors.toSet());

    Set<CollectionDAO> allCollections = collectionsRepository.findAllCollectionsByUser(userId);

    Set<CollectionDAO> updatedCollections = allCollections.stream()
        .map(collectionDAO -> {
          Optional<CollectionBookJson> record = collectionBookJsons.stream()
              .filter(collectionBookJson -> Objects.equals(collectionBookJson.id(),
                  collectionDAO.getId()))
              .findFirst();

          boolean enabled = record.map(CollectionBookJson::enabled).orElse(false);
          if (enabled) {
            collectionDAO.getBookDaos().add(finalBook);
          } else {
            collectionDAO.getBookDaos().remove(finalBook);
          }
          return collectionDAO;
        }).collect(Collectors.toSet());

    Set<CollectionDAO> collectionsToSave = new HashSet<>();
    collectionsToSave.addAll(newCollections);
    collectionsToSave.addAll(updatedCollections);

    Iterable<CollectionDAO> savedCollections = collectionsRepository.saveAll(collectionsToSave);
//contains(finalBook)
    return StreamSupport.stream(savedCollections.spliterator(), false)
        .map(collectionDAO -> new CollectionBookJson(collectionDAO.getId(), collectionDAO.getName(),
            collectionDAO.getBookDaos().contains(finalBook)))
        .collect(Collectors.toSet());
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
}
