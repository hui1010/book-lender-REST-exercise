package se.lexicon.huiyi.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.data.BookRepository;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.entity.Book;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceImplTest {

    BookServiceImpl testObject;

    Book book1;
    Book book2;

    BookDto bookDto1 = new BookDto();
    BookDto bookDto2 = new BookDto();

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        testObject = new BookServiceImpl(bookRepository);
        book1 = new Book("The Big Book", 30, BigDecimal.valueOf(10), "A cook book");
        bookRepository.save(book1);
        bookDto1 = testObject.getBookDto(book1);

        book2 = new Book("The Second Big Book", 30, BigDecimal.valueOf(10), "The second version");
        bookRepository.save(book2);
        bookDto2 = testObject.getBookDto(book2);
    }

    @Test
    void successfully_created() {
        assertNotNull(book1);
        assertNotNull(book2);
        assertNotNull(bookDto1);
        assertNotNull(bookDto2);

    }

    @Test
    void findByReserved() {
        assertEquals(2, testObject.findByReserved(false).size());
        assertEquals(0, testObject.findByReserved(true).size());

        book1.setReserved(true);

        assertTrue( testObject.findByReserved(true).contains(testObject.getBookDto(book1)));
        assertFalse( testObject.findByReserved(true).contains(testObject.getBookDto(book2)));
    }

    @Test
    void findByAvailable() {
        assertEquals(2, testObject.findByAvailable(false).size());
        assertEquals(0, testObject.findByAvailable(true).size());

        book2.setAvailable(true);
        assertEquals(1, testObject.findByAvailable(true).size());
        assertEquals(1, testObject.findByAvailable(false).size());
        assertTrue( testObject.findByAvailable(true).contains(testObject.getBookDto(book2)));
        assertFalse( testObject.findByAvailable(true).contains(testObject.getBookDto(book1)));
    }

    @Test
    void findByTitle() {
        String title1 = "the big book";
        String title2 = "big book";

        assertEquals(1, testObject.findByTitle(title1).size());
        assertEquals(2, testObject.findByTitle(title2).size());
        assertTrue(testObject.findByTitle(title1).contains(bookDto1));
        assertFalse(testObject.findByTitle(title1).contains(bookDto2));
        assertTrue(testObject.findByTitle(title2).contains(bookDto1));
        assertTrue(testObject.findByTitle(title2).contains(bookDto2));
    }

    @Test
    void findById() {
        assertEquals(bookDto1, testObject.findById(bookDto1.getBookId()));
        assertEquals(bookDto2, testObject.findById(bookDto2.getBookId()));
    }

    @Test
    void findAll() {
        assertEquals(2, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(bookDto1));
        assertTrue(testObject.findAll().contains(bookDto2));
    }

    @Test
    void create() {
        BookDto bookDto3 = new BookDto();
        bookDto3.setTitle("The Third Cook Book");
        bookDto3.setMaxLoanDays(30);
        bookDto3.setFinePerDay(BigDecimal.valueOf(15));
        bookDto3.setDescription("The third version");

        bookDto3 = testObject.create(bookDto3);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(bookDto3));
        assertEquals(3, bookDto3.getBookId());
        assertTrue(testObject.findAll().contains(bookDto3));

    }

    @Test
    void update() {
        bookDto1.setTitle("The First Cook Book");
        bookDto1.setMaxLoanDays(15);
        testObject.update(bookDto1);

        assertEquals("The First Cook Book", testObject.findById(book1.getBookId()).getTitle());
        assertEquals(15, testObject.findById(bookDto1.getBookId()).getMaxLoanDays());
    }

    @Test
    void delete() {
        assertTrue(testObject.delete(book1.getBookId()));
        assertFalse(testObject.findAll().contains(bookDto1));
        assertEquals(1, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(bookDto2));
    }
}