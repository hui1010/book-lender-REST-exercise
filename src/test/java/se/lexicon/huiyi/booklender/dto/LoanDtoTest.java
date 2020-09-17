package se.lexicon.huiyi.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanDtoTest {

    LoanDto testObject = new LoanDto();
    BookDto book = new BookDto();

    LibraryUserDto user = new LibraryUserDto();


    @BeforeEach
    void setUp() {
        user.setUserId(100);
        user.setRegDate(LocalDate.of(2020,1,1));
        user.setName("Tom");
        user.setEmail("tom@test.com");

        book.setBookId(1000);
        book.setTitle("Book1");
        book.setMaxLoanDays(30);
        book.setFinePerDay( BigDecimal.valueOf(5));
        book.setAvailable(false);
        book.setReserved(false);
        book.setDescription("A Cook Book");

        testObject.setLoanId(10000);
        testObject.setLoanTaker(user);
        testObject.setBook(book);
        testObject.setLoanDate(LocalDate.of(2020,1,1));
        testObject.setExpired(false);

    }

    @Test
    void successfully_created() {
        assertNotNull(testObject);
        assertNotNull(user);
        assertNotNull(book);
    }

    @Test
    void getLoanId() {
        assertEquals(10000, testObject.getLoanId());
    }

    @Test
    void getLoanTaker() {
        assertEquals(user, testObject.getLoanTaker());
    }

    @Test
    void getBook() {
        assertEquals(book, testObject.getBook());
    }

    @Test
    void getLoanDate() {
        assertEquals(LocalDate.parse("2020-01-01"), testObject.getLoanDate());
    }


    @Test
    void isTerminated() {
        assertFalse(testObject.isExpired());
    }

}