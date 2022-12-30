package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.Collection;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CollectionsRepository extends CrudRepository<Collection, Long> {

    Set<Collection> findByUserId(String userId);

}
