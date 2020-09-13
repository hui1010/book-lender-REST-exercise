package se.lexicon.huiyi.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.huiyi.booklender.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findAllByLoanTaker_UserId(Integer userId);
    List<Loan> findAllByBook_BookId(Integer bookId);
    List<Loan> findAllByIsTerminated(boolean terminatedStatus);
}
