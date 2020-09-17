package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.huiyi.booklender.data.BookRepository;
import se.lexicon.huiyi.booklender.dto.BookDto;
import se.lexicon.huiyi.booklender.entity.Book;
import se.lexicon.huiyi.booklender.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * convert List<Book> to List<BookDto>
     * */
    protected List<BookDto> getBookDtos(List<Book> foundItems) {
        List<BookDto> result = new ArrayList<>();
        for (Book b : foundItems){
            BookDto bookDto = getBookDto(b);
            result.add(bookDto);
        }
        return result;
    }
/**
 * covert Book to BookDto
 * */
    protected BookDto getBookDto(Book book) {
        return new BookDto(book.getBookId(), book.getTitle(), book.isAvailable(), book.isReserved(), book.getMaxLoanDays(),
                book.getFinePerDay(), book.getDescription());
    }

    /**
     * convert BookDto to Book
     * */
    protected Book getBook(BookDto bookDto){
        Book book = new Book(bookDto.getTitle(), bookDto.getMaxLoanDays(), bookDto.getFinePerDay(), bookDto.getDescription());
        return book;
    }

    @Override
    public List<BookDto> findByReserved(boolean reserved) {
        List<Book> foundItems = bookRepository.findAllByReserved(reserved);
        return getBookDtos(foundItems);
    }

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        List<Book> foundItems = bookRepository.findAllByAvailable(available);
        return getBookDtos(foundItems);
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        if (title == null || title.equals(""))
            throw new IllegalArgumentException("Title should not be empty.");
        List<Book> foundItems = bookRepository.findAllByTitleContainingIgnoreCase(title);
        return getBookDtos(foundItems);
    }

    @Override
    public BookDto findById(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Cannot find book with the id: " + bookId));
        return getBookDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> foundItems = bookRepository.findAll();
        return getBookDtos(foundItems);
    }

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        if (bookRepository.findById(bookDto.getBookId()).isPresent())
            throw new RuntimeException("Book already exists, please update");
        Book book = new Book(bookDto.getTitle(),bookDto.getMaxLoanDays(),bookDto.getFinePerDay(),bookDto.getDescription());
        book.setAvailable(bookDto.isAvailable());
        book.setReserved(bookDto.isReserved());
        return getBookDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto update(BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getBookId());
        if (!optionalBook.isPresent())
            throw new RuntimeException("Book does not exist, please create first");
        Book toUpdated = optionalBook.get();
        if (!toUpdated.getTitle().equals( bookDto.getTitle()))
            toUpdated.setTitle(bookDto.getTitle());
        if (toUpdated.isAvailable() != bookDto.isAvailable())
            toUpdated.setAvailable(bookDto.isAvailable());
        if (toUpdated.isReserved() != bookDto.isReserved())
            toUpdated.setReserved(bookDto.isReserved());
        if (toUpdated.getMaxLoanDays()!= bookDto.getMaxLoanDays())
            toUpdated.setMaxLoanDays(bookDto.getMaxLoanDays());
        if (!toUpdated.getFinePerDay().equals(bookDto.getFinePerDay()))
            toUpdated.setFinePerDay(bookDto.getFinePerDay());
        if (!toUpdated.getDescription().equals(bookDto.getDescription()))
            toUpdated.setDescription(bookDto.getDescription());

        return getBookDto(bookRepository.save(toUpdated));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) {
        if (!bookRepository.findById(bookId).isPresent())
            throw new ResourceNotFoundException("Book does not exist");
        boolean deleted = false;
        if (bookRepository.existsById(bookId)){
            bookRepository.delete(bookRepository.findById(bookId).get());
            deleted = true;
        }
        return deleted;
    }
}
