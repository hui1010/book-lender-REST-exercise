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

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        testObject = new LoanServiceImpl(loanRepository, libraryUserService, bookService);
        bookService = new BookServiceImpl(bookRepository);
        libraryUserService = new LibraryUserServiceImpl(libraryUserRepository);

        book1 = new Book("The Big Book", 30, BigDecimal.valueOf(10), "A cook book");
        book1 = bookRepository.save(book1);
        bookDto1 = bookService.getBookDto(book1);

        book2 = new Book("The Second Big Book", 30, BigDecimal.valueOf(10), "The second version");
        book2 = bookRepository.save(book2);
        bookDto2 = bookService.getBookDto(book2);

        user1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@123.com");
        user1 = libraryUserRepository.save(user1);
        userDto1 = libraryUserService.getLibraryUserDto(user1);

        user2 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@123.com");
        user2 = libraryUserRepository.save(user2);
        userDto2 = libraryUserService.getLibraryUserDto(user2);

        loan1 = new Loan(user1, book1, LocalDate.of(2020,1,1), false);
        loan1 = loanRepository.save(loan1);
        loanDto1 = testObject.getLoanDto(loan1);

        loan2 = new Loan(user2, book2, LocalDate.of(2020,2,2), false);
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

    }

    @Test
    void findByBookId() {

    }

    @Test
    void findByUserId() {

    }

    @Test
    void findByIsTerminated() {

    }

    @Test
    void findAll() {

    }

    @Test
    void create() {

    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }
}