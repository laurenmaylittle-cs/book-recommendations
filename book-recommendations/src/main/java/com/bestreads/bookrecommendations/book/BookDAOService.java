package com.bestreads.bookrecommendations.book;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookDAOService {

  private final BookDAORepository bookDAORepository;

  @Autowired
  public BookDAOService(BookDAORepository bookDAORepository) {
    this.bookDAORepository = bookDAORepository;
  }

  public Optional<BookDAO> findBookDAOByISBN(String isbn) {
    return bookDAORepository.findByIsbn(isbn);
  }

  @Transactional
  public BookDAO addNewBook(Book bookToAdd) {
    var currentBook = bookDAORepository.findByIsbn(bookToAdd.isbn());
    if (currentBook.isEmpty()) {
      var newBook = new BookDAO();
      newBook.setIsbn(bookToAdd.isbn());
      newBook.setAuthor(
              Optional.ofNullable(bookToAdd.authors()).map(a -> String.join(", ", a)).orElse("N/A"));
      newBook.setTitle(bookToAdd.title());
      newBook.setThumbnail(bookToAdd.imageLinks().thumbnail());
      newBook.setGenre(Optional.ofNullable(bookToAdd.categories()).map(c -> c.get(0)).orElse("N/A"));
      newBook.setPublisher(Optional.ofNullable(bookToAdd.publisher()).orElse("N/A"));
      newBook.setPublishedDate(Optional.ofNullable(bookToAdd.publishedDate()).orElse("N/A"));
      return bookDAORepository.save(newBook);
    } else {
      return currentBook.get();
    }
  }

}
