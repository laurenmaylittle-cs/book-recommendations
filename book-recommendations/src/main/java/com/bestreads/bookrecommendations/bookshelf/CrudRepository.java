package com.bestreads.bookrecommendations.bookshelf;

import org.springframework.data.jpa.repository.JpaRepository;

interface CrudRepository extends JpaRepository<BookDAO, Long> {

  BookDAO findBookDAOByIsbn(String isbn);
}
