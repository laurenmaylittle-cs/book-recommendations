package com.bestreads.bookrecommendations.bookshelf;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CollectionsRepository extends CrudRepository<CollectionDAO, Long> {

  // issues just 1 query
  Set<CollectionProjection> findByUserId(String userId);

  //this issues 1 select for each of the books - not sure how to fix this N+1
  @EntityGraph(attributePaths = {"bookDAOS"})
  Optional<CollectionBookProjection> findByIdAndUserId(Long id, String userId);

  //For getting the collections by book ISBN (issues 1 query and gives you the collection Id's and names of them - N+1 tested)
  List<CollectionProjection> findByUserIdAndBookDAOS_Isbn(String userId, String isbn);

  @Query(value = """
      select c from CollectionDAO c
      where c.userId = ?1 and
        not exists (select 1 from c.bookDAOS b where b.isbn = ?2)""")
  List<CollectionProjection> findUnassociatedCollectionsForIsbn(String userId, String isbn);

  Set<CollectionDAO> findAllCollectionByUserId(String userId);
}
