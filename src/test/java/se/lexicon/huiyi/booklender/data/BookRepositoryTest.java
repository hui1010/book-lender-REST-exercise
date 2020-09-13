package se.lexicon.huiyi.booklender.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    Book book1;
    Book book2;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        book1 = new Book("The Big Book", 30, BigDecimal.valueOf(5), "A Cook Book");
        book2 = new Book("Another Big Book", 30, BigDecimal.valueOf(5), "A Cook Book");

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    void findAllByReserved() {
        assertEquals(2, bookRepository.findAllByReserved(false).size());
        assertEquals(0, bookRepository.findAllByReserved(true).size());
        assertTrue(bookRepository.findAllByReserved(false).contains(book1));
        assertTrue(bookRepository.findAllByReserved(false).contains(book2));

        book1.setReserved(true);
        bookRepository.save(book1);

        assertEquals(1, bookRepository.findAllByReserved(false).size());
        assertEquals(1, bookRepository.findAllByReserved(true).size());
        assertTrue(bookRepository.findAllByReserved(true).contains(book1));
        assertFalse(bookRepository.findAllByReserved(true).contains(book2));
        assertTrue(bookRepository.findAllByReserved(false).contains(book2));
        assertFalse(bookRepository.findAllByReserved(false).contains(book1));
    }

    @Test
    void findAllByAvailable() {
        assertEquals(2, bookRepository.findAllByAvailable(false).size());
        assertEquals(0, bookRepository.findAllByAvailable(true).size());
        assertTrue(bookRepository.findAllByAvailable(false).contains(book1));
        assertTrue(bookRepository.findAllByAvailable(false).contains(book2));

        book1.setAvailable(true);
        book2.setAvailable(true);
        bookRepository.save(book1);
        bookRepository.save(book2);

        assertEquals(0, bookRepository.findAllByAvailable(false).size());
        assertEquals(2, bookRepository.findAllByAvailable(true).size());
        assertTrue(bookRepository.findAllByAvailable(true).contains(book1));
        assertTrue(bookRepository.findAllByAvailable(true).contains(book2));
        assertFalse(bookRepository.findAllByAvailable(false).contains(book2));
        assertFalse(bookRepository.findAllByAvailable(false).contains(book1));
    }

    @Test
    void findAllByTitleContainingIgnoreCase() {
        List<Book> found1 = bookRepository.findAllByTitleContainingIgnoreCase("the big book");
        List<Book> found2 = bookRepository.findAllByTitleContainingIgnoreCase("big book");

        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(1, found1.size());
        assertTrue(found1.contains(book1));
        assertEquals(2, found2.size());
        assertTrue(found2.contains(book1));
        assertTrue(found2.contains(book2));
    }
}