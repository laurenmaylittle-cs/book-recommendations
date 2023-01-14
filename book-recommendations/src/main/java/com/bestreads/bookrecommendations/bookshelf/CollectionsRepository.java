package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.CollectionBookProjection;
import com.bestreads.bookrecommendations.bookshelf.model.CollectionDAO;
import com.bestreads.bookrecommendations.bookshelf.model.CollectionProjection;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface CollectionsRepository extends CrudRepository<CollectionDAO, Long> {

  // issues just 1 query
  Set<CollectionProjection> findByUserId(String userId);

  //this issues 1 select for each of the books - not sure how to fix this N+1
  @EntityGraph(attributePaths = {"bookDAOS"})
  List<CollectionBookProjection> findByIdAndUserId(Long id, String userId);

  //For getting the collections by book ISBN (issues 1 query and gives you the collection Id's and names of them - N+1 tested)
  List<CollectionProjection> findByUserIdAndBookDAOS_Isbn(String userId, String isbn);

}
