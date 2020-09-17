package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.huiyi.booklender.data.BookRepository;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.data.LoanRepository;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.dto.LoanDto;
import se.lexicon.huiyi.booklender.entity.Book;
import se.lexicon.huiyi.booklender.entity.LibraryUser;
import se.lexicon.huiyi.booklender.entity.Loan;
import se.lexicon.huiyi.booklender.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;
    LibraryUserRepository libraryUserRepository;
    BookRepository bookRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, LibraryUserRepository libraryUserRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.libraryUserRepository = libraryUserRepository;
        this.bookRepository = bookRepository;
    }


    /**
     * convert Loan to LoanDto
     * */
    protected LoanDto getLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setLoanId(loan.getLoanId());
        loanDto.setLoanTaker(getLibraryUserDto(loan.getLoanTaker()));
        loanDto.setBook(getBookDto(loan.getBook()));
        loanDto.setLoanDate(loan.getLoanDate());
        loanDto.setExpired(loan.isExpired());
        return loanDto;
    }


    /**
     * convert List<Loan> to List<LoanDto>
     * */
    protected List<LoanDto> getLoanDtos(List<Loan> foundItems) {
        List<LoanDto> result = new ArrayList<>();
        for (Loan l : foundItems) {
            LoanDto loanDto = getLoanDto(l);
            result.add(loanDto);
        }
        return result;
    }


    public BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAvailable(book.isAvailable());
        bookDto.setReserved(book.isReserved());
        bookDto.setMaxLoanDays(book.getMaxLoanDays());
        bookDto.setFinePerDay(book.getFinePerDay());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }

    public LibraryUserDto getLibraryUserDto(LibraryUser libraryUser) {
        LibraryUserDto libraryUserDto = new LibraryUserDto();
        libraryUserDto.setUserId(libraryUser.getUserId());
        libraryUserDto.setRegDate(libraryUser.getRegDate());
        libraryUserDto.setName(libraryUser.getName());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }

    @Transactional
    public LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){

        return libraryUserRepository.findByUserId(libraryUserDto.getUserId());
    }

    @Transactional
    public Book getBook(BookDto bookDto){
        return bookRepository.findById(bookDto.getBookId()).orElseThrow(()-> new IllegalArgumentException("Book does not exist"));
    }

    @Override
    public LoanDto findById(long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()-> new ResourceNotFoundException(
                "Cannot find the loan with id: " + loanId
        ));
        return getLoanDto(loan);
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
        return getLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        List<Loan> foundItems = loanRepository.findAllByLoanTaker_UserId(userId);
        return getLoanDtos(foundItems);
    }


    @Override
    public List<LoanDto> findByExpired(boolean expired) {
        List<Loan> foundItems = loanRepository.findAllByExpired(expired);
        return getLoanDtos(foundItems);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> all = loanRepository.findAll();
        return getLoanDtos(all);
    }

    @Override
    @Transactional
    public LoanDto create(LoanDto loanDto) {
        if (loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan already existed, please update");
        Loan loan = new Loan(getLibraryUser(loanDto.getLoanTaker()),
                getBook(loanDto.getBook()),
                loanDto.getLoanDate(),
                loanDto.isExpired());

        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanDto update(LoanDto loanDto) {
        if (!loanRepository.existsById(loanDto.getLoanId()))
            throw new RuntimeException("Loan does not exist, please create first");
        Loan loan = loanRepository.findById(loanDto.getLoanId()).get();
        if (!loan.getLoanTaker().equals(getLibraryUser(loanDto.getLoanTaker())))
            loan.setLoanTaker(getLibraryUser(loanDto.getLoanTaker()));
        if (!loan.getBook().equals(getBook(loanDto.getBook())))
            loan.setBook(getBook(loanDto.getBook()));
        if (loan.getLoanDate() != loanDto.getLoanDate())
            loan.setLoanDate(loanDto.getLoanDate());
        if (loan.isOverdue() != loanDto.isExpired())
            loan.setExpired(loanDto.isExpired());

        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) {
        boolean deleted = false;

        if (loanRepository.findAllByBook_BookId(bookId).isEmpty()){
            throw new ResourceNotFoundException("Cannot find any loan with book id: " + bookId);
        }else{
            List<Loan> foundItems = loanRepository.findAllByBook_BookId(bookId);
            for (Loan l : foundItems){
                loanRepository.delete(l);
                deleted = true;
            }
        }
        return deleted;
    }
}
