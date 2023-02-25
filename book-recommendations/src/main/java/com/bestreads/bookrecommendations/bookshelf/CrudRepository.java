package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import org.springframework.data.jpa.repository.JpaRepository;

interface CrudRepository extends JpaRepository<BookDAO, Long> {

  BookDAO findBookDAOByIsbn(String isbn);
}
