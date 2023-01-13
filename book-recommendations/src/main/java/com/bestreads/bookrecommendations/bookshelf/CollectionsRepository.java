package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.CollectionDAO;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface CollectionsRepository extends CrudRepository<CollectionDAO, Long> {

  Set<CollectionDAO> findByUserId(String userId);

}
