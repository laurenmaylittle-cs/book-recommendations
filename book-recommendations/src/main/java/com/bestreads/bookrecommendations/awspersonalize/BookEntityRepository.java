package com.bestreads.bookrecommendations.awspersonalize;

import java.util.Optional;

import com.bestreads.bookrecommendations.book.BookDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookEntityRepository extends CrudRepository<BookDAO, String> {

  Optional<BookDAO> findByIsbn(String isbn);
}
