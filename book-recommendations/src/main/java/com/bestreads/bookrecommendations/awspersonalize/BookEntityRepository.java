package com.bestreads.bookrecommendations.awspersonalize;

import com.bestreads.bookrecommendations.bookshelf.BookDAO;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookEntityRepository extends CrudRepository<BookDAO, String> {

  Optional<BookDAO> findByIsbn(String isbn);
}
