package se.lexicon.huiyi.booklender.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {
    private Loan testObject;
    private LibraryUser loanTaker1;
    private LibraryUser loanTaker2;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        loanTaker1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@test.com");
        loanTaker2 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@test.com");
        book1 = new Book("Book1", 30, BigDecimal.valueOf(10), "book one");
        book2 = new Book("Book2", 40, BigDecimal.valueOf(20), "book two");
        testObject = new Loan(loanTaker1, book1, LocalDate.of(2020,3,3), false);
    }

    @Test
    void successfully_created() {
        assertNotNull(testObject);
        assertNotNull(loanTaker1);
        assertNotNull(loanTaker2);
        assertNotNull(book1);
        assertNotNull(book2);
    }

    @Test
    void isOverdue() {
        assertTrue(testObject.isExpired());
    }

    @Test
    void extendLoan() {
        book1.setReserved(true);
        assertFalse(testObject.extendLoan(30));
        assertFalse(testObject.extendLoan(40));

        book1.setReserved(false);
        assertTrue(testObject.extendLoan(30));
        assertEquals(60, testObject.getBook().getMaxLoanDays());
    }

    @Test
    void getLoanId() {
        assertEquals(0,testObject.getLoanId());
    }

    @Test
    void getLoanTaker() {
        assertEquals(loanTaker1, testObject.getLoanTaker());
    }

    @Test
    void setLoanTaker() {
        testObject.setLoanTaker(loanTaker2);
        assertEquals(loanTaker2, testObject.getLoanTaker());
    }

    @Test
    void getBook() {
        assertEquals(book1, testObject.getBook());
    }

    @Test
    void setBook() {
        testObject.setBook(book2);
        assertEquals(book2, testObject.getBook());
    }

    @Test
    void getLoanDate() {
        assertEquals(LocalDate.of(2020,3,3), testObject.getLoanDate());
    }

    @Test
    void setLoanDate() {
        testObject.setLoanDate(LocalDate.of(2020,4,4));
        assertEquals(LocalDate.of(2020,4,4), testObject.getLoanDate());
    }

    @Test
    void isExpired() {
        assertFalse(testObject.isExpired());
    }

    @Test
    void setExpired() {
        testObject.setExpired(true);
        assertTrue(testObject.isExpired());
    }
}