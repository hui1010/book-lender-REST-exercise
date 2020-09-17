package se.lexicon.huiyi.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.data.BookRepository;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.data.LoanRepository;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.dto.LoanDto;
import se.lexicon.huiyi.booklender.entity.Book;
import se.lexicon.huiyi.booklender.entity.LibraryUser;
import se.lexicon.huiyi.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class LoanServiceImplTest {

    LoanServiceImpl testObject;
    LibraryUserServiceImpl libraryUserService;
    BookServiceImpl bookService;

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibraryUserRepository libraryUserRepository;

    Loan loan1;
    Loan loan2;

    LoanDto loanDto1;
    LoanDto loanDto2;

    Book book1;
    Book book2;

    BookDto bookDto1;
    BookDto bookDto2;

    LibraryUser user1;
    LibraryUser user2;

    LibraryUserDto userDto1;
    LibraryUserDto userDto2;


    @BeforeEach
    void setUp() {
        testObject = new LoanServiceImpl(loanRepository, libraryUserRepository, bookRepository);
        bookService = new BookServiceImpl(bookRepository);
        libraryUserService = new LibraryUserServiceImpl(libraryUserRepository);

        book1 = new Book("The Big Book", 30, BigDecimal.valueOf(10), "A cook book");
        book1 = bookRepository.save(book1);
        bookDto1 = bookService.getBookDto(book1);

        book2 = new Book("The Second Big Book", 30, BigDecimal.valueOf(10), "The second version");
        book2 = bookRepository.save(book2);
        bookDto2 = bookService.getBookDto(book2);

        user1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@test.com");
        user1 = libraryUserRepository.save(user1);
        userDto1 = libraryUserService.getLibraryUserDto(user1);

        user2 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@test.com");
        user2 = libraryUserRepository.save(user2);
        userDto2 = libraryUserService.getLibraryUserDto(user2);

        loan1 = new Loan(user1, book1, LocalDate.of(2020,1,1), false);
        loan1 = loanRepository.save(loan1);
        loanDto1 = testObject.getLoanDto(loan1);

        loan2 = new Loan(user2, book2, LocalDate.of(2020,2,2), true);
        loan2 = loanRepository.save(loan2);
        loanDto2 = testObject.getLoanDto(loan2);

    }

    @Test
    void successfully_created() {
        assertNotNull(book1);
        assertNotNull(book2);
        assertNotNull(bookDto1);
        assertNotNull(bookDto2);
        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(userDto1);
        assertNotNull(userDto2);
        assertNotNull(loan1);
        assertNotNull(loan2);
        assertNotNull(loanDto1);
        assertNotNull(loanDto2);
    }

    @Test
    void findById() {
        assertEquals(loanDto1, testObject.findById(loanDto1.getLoanId()));
        assertEquals(loanDto2, testObject.findById(loanDto2.getLoanId()));
    }

    @Test
    void findByBookId() {

        assertEquals(1, testObject.findByBookId(bookDto1.getBookId()).size());
        assertTrue(testObject.findByBookId(bookDto1.getBookId()).contains(loanDto1));
        assertFalse(testObject.findByBookId(bookDto1.getBookId()).contains(loanDto2));

        assertEquals(1, testObject.findByBookId(bookDto2.getBookId()).size());
        assertTrue(testObject.findByBookId(bookDto2.getBookId()).contains(loanDto2));
        assertFalse(testObject.findByBookId(bookDto2.getBookId()).contains(loanDto1));
    }

    @Test
    void findByUserId() {

        assertEquals(1, testObject.findByUserId(loanDto1.getLoanTaker().getUserId()).size());
        assertTrue(testObject.findByUserId(loanDto1.getLoanTaker().getUserId()).contains(loanDto1));
        assertFalse(testObject.findByUserId(loanDto1.getLoanTaker().getUserId()).contains(loanDto2));

        assertEquals(1, testObject.findByUserId(loanDto2.getLoanTaker().getUserId()).size());
        assertTrue(testObject.findByUserId(loanDto2.getLoanTaker().getUserId()).contains(loanDto2));
        assertFalse(testObject.findByUserId(loanDto2.getLoanTaker().getUserId()).contains(loanDto1));

    }

    @Test
    void findByExpired() {

        assertEquals(1, testObject.findByExpired(false).size());

        loan1.setExpired(true);
        loanDto1 = testObject.getLoanDto(loan1);
        assertEquals(0, testObject.findByExpired(false).size());
        assertFalse(testObject.findByExpired(false).contains(loanDto2));
        assertFalse(testObject.findByExpired(false).contains(loanDto1));

        assertEquals(2, testObject.findByExpired(true).size());
        assertTrue(testObject.findByExpired(true).contains(loanDto1));
        assertTrue(testObject.findByExpired(true).contains(loanDto2));

    }

    @Test
    void findAll() {

        assertEquals(2, testObject.findAll().size());
    }

    @Test
    void create() {

        LoanDto loanDto3 = new LoanDto();
        loanDto3.setLoanTaker(userDto1);
        loanDto3.setBook(bookDto2);
        loanDto3.setLoanDate(LocalDate.of(2019,1,1));
        loanDto3.setExpired(true);

        loanDto3 = testObject.create(loanDto3);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(loanDto3));

    }

    @Test
    void update() {
        loanDto2.setLoanDate(LocalDate.of(2013,1,1));
        loanDto2 = testObject.update(loanDto2);
        assertEquals(LocalDate.of(2013,1,1), loanDto2.getLoanDate());

        loanDto1.setBook(testObject.getBookDto(book2));
        loanDto1 = testObject.update(loanDto1);
        assertEquals(bookDto2, loanDto1.getBook());

        loanDto2.setLoanTaker(userDto1);
        loanDto2 = testObject.update(loanDto2);
        assertEquals(userDto1, loanDto2.getLoanTaker());
    }

    @Test
    void delete() {
        testObject.delete(loanDto1.getBook().getBookId());
        assertEquals(1, testObject.findAll().size());
        assertFalse(testObject.findAll().contains(loanDto1));

    }
}