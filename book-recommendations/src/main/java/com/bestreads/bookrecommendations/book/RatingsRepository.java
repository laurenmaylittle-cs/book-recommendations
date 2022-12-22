package com.bestreads.bookrecommendations.book;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RatingsRepository extends CrudRepository<Rating, Integer> {

  List<Rating> findAllByIsbnAndEmail(String isbn, String email);

}
