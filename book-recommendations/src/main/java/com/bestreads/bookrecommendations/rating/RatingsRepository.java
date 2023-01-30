package com.bestreads.bookrecommendations.rating;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RatingsRepository extends CrudRepository<Rating, Integer> {

  Optional<Rating> findByIsbnAndEmail(String isbn, String email);

}
