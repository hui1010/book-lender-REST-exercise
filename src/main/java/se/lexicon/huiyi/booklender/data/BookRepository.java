package se.lexicon.huiyi.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.huiyi.booklender.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAllByReserved(boolean ReservedStatus);
    List<Book> findAllByAvailable(boolean availableStatus);
    List<Book> findAllByTitleContainingIgnoreCase(String title);
}
