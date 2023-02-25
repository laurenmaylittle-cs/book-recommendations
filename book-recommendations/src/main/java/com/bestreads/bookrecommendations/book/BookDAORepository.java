package com.bestreads.bookrecommendations.book;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BookDAORepository extends CrudRepository<BookDAO, Long> {

  Optional<BookDAO> findBookDAOByIsbn(String isbn);

}
