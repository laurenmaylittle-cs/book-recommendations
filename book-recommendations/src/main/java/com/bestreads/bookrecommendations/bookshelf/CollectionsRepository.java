package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.Collection;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface CollectionsRepository extends CrudRepository<Collection, Long> {

  Set<Collection> findByUserId(String userId);
}
