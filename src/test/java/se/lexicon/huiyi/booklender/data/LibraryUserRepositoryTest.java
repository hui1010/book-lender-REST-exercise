package se.lexicon.huiyi.booklender.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserRepositoryTest {

    LibraryUser user1;
    LibraryUser user2;

    @Autowired
    LibraryUserRepository libraryUserRepository;


    @BeforeEach
    void setUp() {
        user1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@test.com");
        user2 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@test.com");

        libraryUserRepository.save(user1);
        libraryUserRepository.save(user2);
    }

    @Test
    void findByEmailIgnoreCase() {
        LibraryUser found1 = libraryUserRepository.findByEmailIgnoreCase("TOM@test.com");
        LibraryUser found2 = libraryUserRepository.findByEmailIgnoreCase("jerry@test.com");
        LibraryUser found3 = libraryUserRepository.findByEmailIgnoreCase("dadada");


        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(found1, user1);
        assertEquals(found2, user2);
        assertTrue(found1.getName().equalsIgnoreCase("tom"));
        assertEquals(found2.getRegDate(),LocalDate.of(2020,2,2));
        assertNull(found3);
    }
}