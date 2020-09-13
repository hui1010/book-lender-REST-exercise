package se.lexicon.huiyi.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    BookDto testObject = new BookDto();

    @BeforeEach
    void setUp() {
        testObject.setBookId(100);
        testObject.setTitle("The Big Book");
        testObject.setMaxLoanDays(30);
        testObject.setFinePerDay( BigDecimal.valueOf(5));
        testObject.setAvailable(false);
        testObject.setReserved(false);
        testObject.setDescription("A Cook Book");
    }

    @Test
    void successfully_created() {
        assertNotNull(testObject);
    }

    @Test
    void getBookId() {
        assertEquals(100,testObject.getBookId());
    }

    @Test
    void setBookId() {
        testObject.setBookId(105);
        assertEquals(105, testObject.getBookId());
    }

    @Test
    void getTitle() {
        assertEquals("The Big Book", testObject.getTitle());
    }

    @Test
    void setTitle() {
        testObject.setTitle("Big Book");
        assertEquals("Big Book", testObject.getTitle());
    }

    @Test
    void isAvailable() {
        assertFalse(testObject.isAvailable());
    }

    @Test
    void setAvailable() {
        testObject.setAvailable(true);
        assertTrue(testObject.isAvailable());
    }

    @Test
    void isReserved() {
        assertFalse(testObject.isReserved());
    }

    @Test
    void setReserved() {
        testObject.setReserved(true);
        assertTrue(testObject.isReserved());
    }

    @Test
    void getMaxLoanDays() {
        assertEquals(30, testObject.getMaxLoanDays());
    }

    @Test
    void setMaxLoanDays() {
        testObject.setMaxLoanDays(31);
        assertEquals(31, testObject.getMaxLoanDays());
    }

    @Test
    void getFinePerDay() {
        assertEquals(BigDecimal.valueOf(5), testObject.getFinePerDay());
    }

    @Test
    void setFinePerDay() {
        testObject.setFinePerDay(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), testObject.getFinePerDay());
    }

    @Test
    void getDescription() {
        assertEquals("A Cook Book", testObject.getDescription());
    }

    @Test
    void setDescription() {
        testObject.setDescription("Cook Book");
        assertEquals("Cook Book", testObject.getDescription());
    }
}