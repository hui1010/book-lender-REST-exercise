package se.lexicon.huiyi.booklender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.huiyi.booklender.data.BookRepository;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.entity.Book;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Transactional(rollbackFor = Exception.class)
public class MyCommandLineRunner implements CommandLineRunner {

    BookRepository bookRepository;
    LibraryUserRepository libraryUserRepository;

    @Autowired
    public MyCommandLineRunner(BookRepository bookRepository, LibraryUserRepository libraryUserRepository) {
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LibraryUser user1 = new LibraryUser(LocalDate.of(2020,1,1),"Huiyi", "huiyi@test.com");
        user1 =libraryUserRepository.save(user1);

        LibraryUser user2 = new LibraryUser(LocalDate.of(2020,2,2), "Niklas", "niklas@test.com");
        user2 = libraryUserRepository.save(user2);

        Book book1 = new Book("The Big Book", 30, BigDecimal.TEN, "The first version");
        book1.setAvailable(true);
        book1.setReserved(false);
        book1 = bookRepository.save(book1);

        Book book2 = new Book("The Second Big Book", 20, BigDecimal.ONE, "The second version");
        book2.setAvailable(false);
        book2.setReserved(true);
        book2 = bookRepository.save(book2);

    }
}
