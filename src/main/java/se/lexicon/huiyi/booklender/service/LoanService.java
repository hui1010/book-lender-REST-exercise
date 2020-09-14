package se.lexicon.huiyi.booklender.service;

import se.lexicon.huiyi.booklender.dto.LoanDto;

import java.util.List;

public interface LoanService {
    LoanDto findById(long loanId);
    List<LoanDto> FindByBookId(int bookId);
    List<LoanDto> findByUserId(int userId);
    List<LoanDto> findByIsTerminated(boolean terminated);
    List<LoanDto> findAll();
    LoanDto create(LoanDto loanDto);
    LoanDto update(LoanDto loanDto);
    boolean delete(int bookId);
}
