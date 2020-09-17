package se.lexicon.huiyi.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUserDtoTest {
    LibraryUserDto testObject = new LibraryUserDto();

    @BeforeEach
    void setUp() {
        testObject.setUserId(100);
        testObject.setRegDate(LocalDate.of(2020,1,1));
        testObject.setName("test");
        testObject.setEmail("test@test.com");
    }

    @Test
    void successfully_created() {
        assertNotNull(testObject);
    }

    @Test
    void getUserId() {
        assertEquals(100, testObject.getUserId());
    }

    @Test
    void setUserId() {
        testObject.setUserId(200);
        assertEquals(200,testObject.getUserId());
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
        assertEquals("test@test.com", testObject.getEmail());
    }

    @Test
    void setEmail() {
        testObject.setEmail("test@tester.com");
        assertEquals("test@tester.com", testObject.getEmail());
    }
}