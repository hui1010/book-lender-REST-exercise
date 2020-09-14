package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.huiyi.booklender.data.LoanRepository;
import se.lexicon.huiyi.booklender.dto.LoanDto;
import se.lexicon.huiyi.booklender.entity.Loan;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * convert Loan to LoanDto
     * */
    protected static LoanDto getLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setLoanId(loan.getLoanId());
        loanDto.setLoanTaker(LibraryUserServiceImpl.getLibraryUserDto(loan.getLoanTaker()));
        loanDto.setBook(BookServiceImpl.getBookDto(loan.getBook()));
        loanDto.setLoanDate(loan.getLoanDate());
        loanDto.setTerminated(loan.isTerminated());
        return loanDto;
    }

    /**
     * convert List<Loan> to List<LoanDto>
     * */
    protected static List<LoanDto> getLoanDtos(List<Loan> foundItems) {
        List<LoanDto> result = new ArrayList<>();
        for (Loan l : foundItems) {
            LoanDto loanDto = getLoanDto(l);
            result.add(loanDto);
        }
        return result;
    }

    @Override
    public LoanDto findById(long loanId) {
        Loan loan = loanRepository.findById(loanId).get();
        return getLoanDto(loan);
    }

    @Override
    public List<LoanDto> FindByBookId(int bookId) {
        List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
        return getLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        List<Loan> foundItems = loanRepository.findAllByLoanTaker_UserId(userId);
        return getLoanDtos(foundItems);
    }


    @Override
    public List<LoanDto> findByIsTerminated(boolean terminated) {
        List<Loan> foundItems = loanRepository.findAllByIsTerminated(terminated);
        return getLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> all = loanRepository.findAll();
        return getLoanDtos(all);
    }

    @Override
    public LoanDto create(LoanDto loanDto) {
        if (loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan has already existed");
        Loan loan = new Loan(LibraryUserServiceImpl.getLibraryUser(loanDto.getLoanTaker()),
                BookServiceImpl.getBook(loanDto.getBook()),
                loanDto.getLoanDate(),
                loanDto.isTerminated());

        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    public LoanDto update(LoanDto loanDto) {
        if (!loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan does not exist");
        Loan loan = loanRepository.findById(loanDto.getLoanId()).get();
        if (!loan.getLoanTaker().equals(LibraryUserServiceImpl.getLibraryUser(loanDto.getLoanTaker())))
            loan.setLoanTaker(LibraryUserServiceImpl.getLibraryUser(loanDto.getLoanTaker()));
        if (!loan.getBook().equals(BookServiceImpl.getBook(loanDto.getBook())))
            loan.setBook(BookServiceImpl.getBook(loanDto.getBook()));
        if (loan.getLoanDate() != loanDto.getLoanDate())
            loan.setLoanDate(loanDto.getLoanDate());
        if (loan.isTerminated() != loanDto.isTerminated())
            loan.setTerminated(loanDto.isTerminated());

        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    public boolean delete(int bookId) {
        boolean deleted = false;
        if (!loanRepository.findAllByBook_BookId(bookId).isEmpty()){
            List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
            for (Loan l : foundItems){
                loanRepository.delete(l);
            }
            deleted = true;
        }
        return deleted;
    }
}