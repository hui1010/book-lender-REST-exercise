package se.lexicon.huiyi.booklender.controller;

import org.springframework.http.ResponseEntity;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.entity.Book;

public interface BookController {
    ResponseEntity<BookDto> findById(int bookId);
    ResponseEntity<Object> find(final String type, final String value);
    ResponseEntity<BookDto> save(BookDto bookDto);
    ResponseEntity<BookDto> update(BookDto bookDto);
}
