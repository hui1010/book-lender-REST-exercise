package se.lexicon.huiyi.booklender.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.huiyi.booklender.entity.Book;
import se.lexicon.huiyi.booklender.entity.LibraryUser;
import se.lexicon.huiyi.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {

    Loan loan1;
    Loan loan2;
    Loan loan3;
    LibraryUser user1;
    LibraryUser user3;
    Book book1;
    Book book2;

    @Autowired
    LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        book1 = new Book("The Big Book", 30, BigDecimal.valueOf(5), "A Cook Book");
        book2 = new Book("Another Big Book", 30, BigDecimal.valueOf(5), "A Cook Book");

        user1 = new LibraryUser(LocalDate.of(2020,1,1), "Tom", "tom@123.com");
        user3 = new LibraryUser(LocalDate.of(2020,2,2), "Jerry", "jerry@123.com");

        loan1 = new Loan(user1, book1, LocalDate.of(2020,1,1), true);
        loan2 = new Loan(user1, book2, LocalDate.of(2020,1,1), true );
        loan3 = new Loan(user3, book2, LocalDate.of(2020,1,1), false);

        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);

    }

    @Test
    void findAllByLoanTaker_UserId() {
        List<Loan> found1 = loanRepository.findAllByLoanTaker_UserId(user1.getUserId());
        List<Loan> found2 = loanRepository.findAllByLoanTaker_UserId(user3.getUserId());

        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(2, found1.size());
        assertTrue(found1.contains(loan1));
        assertTrue(found1.contains(loan2));
        assertEquals(1, found2.size());
        assertTrue(found2.contains(loan3));

    }

    @Test
    void findAllByBook_BookId() {
        List<Loan> found1 = loanRepository.findAllByBook_BookId(book1.getBookId());
        List<Loan> found2 = loanRepository.findAllByBook_BookId(book2.getBookId());

        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(1, found1.size());
        assertTrue(found1.contains(loan1));
        assertEquals(2, found2.size());
        assertTrue(found2.contains(loan2));
        assertTrue(found2.contains(loan3));
    }

    @Test
    void findAllByIsTerminated() {
        List<Loan> found1 = loanRepository.findAllByIsTerminated(true);
        List<Loan> found2 = loanRepository.findAllByIsTerminated(false);

        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(2, found1.size());
        assertTrue(found1.contains(loan1));
        assertTrue(found1.contains(loan2));
        assertEquals(1, found2.size());
        assertTrue(found2.contains(loan3));

    }
}