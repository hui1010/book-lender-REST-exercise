package se.lexicon.huiyi.booklender.dto;


import java.time.LocalDate;
import java.util.Objects;

public class LoanDto {
    private long loanId;
    private LibraryUserDto loanTaker;
    private BookDto book;
    private LocalDate loanDate;
    private boolean isTerminated;

    public LoanDto() {
    }

    public LoanDto(long loanId, LibraryUserDto loanTaker, BookDto book, LocalDate loanDate, boolean isTerminated) {
        this.loanId = loanId;
        this.loanTaker = loanTaker;
        this.book = book;
        this.loanDate = loanDate;
        this.isTerminated = isTerminated;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public LibraryUserDto getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUserDto loanTaker) {
        this.loanTaker = loanTaker;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDto loanDto = (LoanDto) o;
        return loanId == loanDto.loanId &&
                isTerminated == loanDto.isTerminated &&
                Objects.equals(loanTaker, loanDto.loanTaker) &&
                Objects.equals(book, loanDto.book) &&
                Objects.equals(loanDate, loanDto.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, isTerminated);
    }

    @Override
    public String toString() {
        return "LoanDto{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", isTerminated=" + isTerminated +
                '}';
    }
}
