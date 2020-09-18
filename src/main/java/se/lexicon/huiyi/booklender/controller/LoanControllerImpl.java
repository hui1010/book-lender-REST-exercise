package se.lexicon.huiyi.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.huiyi.booklender.dto.LoanDto;
import se.lexicon.huiyi.booklender.service.LoanServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/loans")
public class LoanControllerImpl implements LoanController {

    public static final String ALL = "all";
    public static final String BOOK_ID = "bookid";
    public static final String USER_ID = "userid";
    public static final String EXPIRED = "expired";
    LoanServiceImpl loanService;

    @Autowired
    public LoanControllerImpl(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @Override
    @GetMapping(path = "/{loanId}")
    public ResponseEntity<LoanDto> findById(@PathVariable long loanId) {
        return ResponseEntity.ok(loanService.findById(loanId));
    }

    @Override
    @GetMapping
    public ResponseEntity<Object> find(
            @RequestParam(name = "type", defaultValue = ALL) String type,
            @RequestParam(name = "value", defaultValue = ALL) String value) {

        switch (type.toLowerCase().trim()){
            case BOOK_ID: return ResponseEntity.ok(loanService.findByBookId(Integer.parseInt(value)));
            case USER_ID: return ResponseEntity.ok(loanService.findByUserId(Integer.parseInt(value)));
            case EXPIRED: return ResponseEntity.ok(loanService.findByExpired(Boolean.parseBoolean(value)));
            case ALL: return ResponseEntity.ok(loanService.findAll());
            default: throw new IllegalArgumentException("Not a valid type: " + type);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<LoanDto> save(@Valid @RequestBody LoanDto loanDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(loanDto));
    }


    @Override
    @PutMapping
    public ResponseEntity<LoanDto> update(@RequestBody LoanDto loanDto) {
        return ResponseEntity.ok(loanService.update(loanDto));
    }
}
