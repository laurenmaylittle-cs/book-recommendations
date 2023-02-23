package com.bestreads.bookrecommendations.awspersonalize;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository extends CrudRepository<BookEntity, String> {
}
