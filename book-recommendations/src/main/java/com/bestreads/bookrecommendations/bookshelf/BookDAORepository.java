package com.bestreads.bookrecommendations.bookshelf;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

interface BookDAORepository extends CrudRepository<BookDAO, Long> {

  Optional<BookDAO> findBookDAOByIsbn(String isbn);

}
