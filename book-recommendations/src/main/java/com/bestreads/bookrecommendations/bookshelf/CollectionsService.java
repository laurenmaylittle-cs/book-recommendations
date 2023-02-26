package com.bestreads.bookrecommendations.bookshelf;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        .sorted(Comparator.comparing(CollectionJson::name))
        .collect(Collectors.toCollection(LinkedHashSet::new));
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

  Optional<CollectionBookProjection> findByIdAndUser(Long id, String userId) {
    return collectionsRepository.findByIdAndUserId(id, userId);
  }

  @Transactional
  void updateCollectionName(Long id, String name, String userId) {
    var collection = collectionsRepository.findById(id);

    if (collection.isEmpty()) {
      createNewCollection(userId, name);
    } else {
      collection.get().setName(name);
      collectionsRepository.save(collection.get());
    }
  }
}
