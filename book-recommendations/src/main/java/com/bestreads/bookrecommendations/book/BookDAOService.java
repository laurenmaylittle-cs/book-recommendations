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
  public BookDAO addNewBook(Book book) {
    var newBook = new BookDAO();
    newBook.setIsbn(book.isbn());
    newBook.setAuthor(
        Optional.ofNullable(book.authors()).map(a -> String.join(", ", a)).orElse("N/A"));
    newBook.setTitle(book.title());
    newBook.setThumbnail(book.imageLinks().thumbnail());
    newBook.setGenre(Optional.ofNullable(book.categories()).map(c -> c.get(0)).orElse("N/A"));
    newBook.setPublisher(Optional.ofNullable(book.publisher()).orElse("N/A"));
    newBook.setPublishedDate(Optional.ofNullable(book.publishedDate()).orElse("N/A"));
    return bookDAORepository.save(newBook);
  }

}
