package se.lexicon.huiyi.booklender.service;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserServiceImplTest {

    LibraryUserServiceImpl testObject;
    LibraryUserDto userDto1 = new LibraryUserDto();
    LibraryUserDto userDto2 = new LibraryUserDto();

    LibraryUser user1;
    LibraryUser user2;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        testObject = new LibraryUserServiceImpl(libraryUserRepository);

        user1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@123.com");
        libraryUserRepository.save(user1);
        userDto1 = testObject.getLibraryUserDto(user1);

        user2 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@123.com");
        libraryUserRepository.save(user2);
        userDto2 = testObject.getLibraryUserDto(user2);
    }

    @Test
    void successfully_created() {
        assertNotNull(userDto1);
        assertNotNull(userDto2);
        assertNotNull(user1);
        assertNotNull(user2);
    }

    @Test
    void findById() {
        assertEquals(userDto1, testObject.findById(user1.getUserId()));
        assertEquals(userDto2, testObject.findById(user2.getUserId()));
      //  assertNull(testObject.findById(3000));
    }

    @Test
    void findByEmail() {
        String email1 = "tOm@123.com";
        String email2 = "jerry@123.com";
        assertEquals(userDto1, testObject.findByEmail(email1));
        assertEquals(userDto2, testObject.findByEmail(email2));
    }

    @Test
    void findAll() {
        assertEquals(2, testObject.findAll().size());
    }

    @Test
    void create() {
        LibraryUserDto userDto3 = new LibraryUserDto();
        userDto3.setRegDate(LocalDate.of(2020,3,3));
        userDto3.setName("Bowser");
        userDto3.setEmail("bowser@123.com");

        userDto3 = testObject.create(userDto3);

        assertEquals(3, testObject.findAll().size());
        assertEquals(userDto3, testObject.findById(userDto3.getUserId()));

    }

    @Test
    void update() {
        userDto2.setName("Jessie");
        userDto2.setEmail("jessie@123.com");
        testObject.update(userDto2);

        assertEquals(userDto2, testObject.findById(userDto2.getUserId()));
        assertEquals("Jessie", testObject.findById(userDto2.getUserId()).getName());
        assertEquals("jessie@123.com", testObject.findById(userDto2.getUserId()).getEmail());
    }

    @Test
    void delete() {
        assertTrue(testObject.delete(userDto1.getUserId()));
        assertEquals(1, testObject.findAll().size());
        assertFalse(testObject.findAll().contains(userDto1));
        assertTrue(testObject.findAll().contains(userDto2));

    }
}