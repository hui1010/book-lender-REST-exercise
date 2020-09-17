package se.lexicon.huiyi.booklender.controller;

import org.springframework.http.ResponseEntity;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.dto.LoanDto;

public interface LoanController {
    ResponseEntity<LoanDto> findById(long loanId);
    ResponseEntity<Object> find(final String type, final String value);
    ResponseEntity<LoanDto> save(LoanDto loanDto);
    ResponseEntity<LoanDto> update(LoanDto loanDto);
}
