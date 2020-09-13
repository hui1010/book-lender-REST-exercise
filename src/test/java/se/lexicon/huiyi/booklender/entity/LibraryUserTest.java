package se.lexicon.huiyi.booklender.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LibraryUserTest {
    LibraryUser testObject;

    @BeforeEach
    void setUp() {
        testObject = new LibraryUser(LocalDate.of(2020,1,1), "test", "test@123.com");
    }

    @Test
    void successfully_created() {
        assertNotNull(testObject);
    }

    @Test
    void getUserId() {
        assertEquals(0, testObject.getUserId());
    }

    @Test
    void getRegDate() {
        assertEquals(LocalDate.of(2020,1,1), testObject.getRegDate());
    }

    @Test
    void setRegDate() {
        testObject.setRegDate(LocalDate.of(2020,2,2));
        assertEquals(LocalDate.of(2020,2,2), testObject.getRegDate());
    }

    @Test
    void getName() {
        assertEquals("test", testObject.getName());
    }

    @Test
    void setName() {
        testObject.setName("Tester");
        assertEquals("Tester", testObject.getName());
    }

    @Test
    void getEmail() {
        assertEquals("test@123.com", testObject.getEmail());
    }

    @Test
    void setEmail() {
        testObject.setEmail("test@1234.com");
        assertEquals("test@1234.com", testObject.getEmail());
    }

}