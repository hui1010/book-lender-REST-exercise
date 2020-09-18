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
     * Method for converting List<Book> to List<BookDto>
     *
     * @param books the books needed to be converted
     * @return List<BookDto>
     */
    protected List<BookDto> getBookDtos(List<Book> books) {
        List<BookDto> result = new ArrayList<>();
        for (Book b : books){
            BookDto bookDto = getBookDto(b);
            result.add(bookDto);
        }
        return result;
    }

    /**
     * Method for converting Book to BookDto
     *
     * @param book Book
     * @return BookDto
     */
    protected BookDto getBookDto(Book book) {
        return new BookDto(book.getBookId(), book.getTitle(), book.isAvailable(), book.isReserved(), book.getMaxLoanDays(),
                book.getFinePerDay(), book.getDescription());
    }

    /**
     * Method for converting BookDto to Book
     *
     * @param bookDto BookDto
     * @return Book
     */
    protected Book getBook(BookDto bookDto){
        Book book = new Book(bookDto.getTitle(), bookDto.getMaxLoanDays(), bookDto.getFinePerDay(), bookDto.getDescription());
        return book;
    }

    /**
     * Method for finding List<BookDto> according to the reserved status
     *
     * @param reserved true: book is reserved, false: book is not reserved
     * @return found List<BookDto>
     */

    @Override
    public List<BookDto> findByReserved(boolean reserved) {
        List<Book> foundItems = bookRepository.findAllByReserved(reserved);
        return getBookDtos(foundItems);
    }

    /**
     * Method for finding List<BookDto> according to the available status
     *
     * @param available true: book is available, false: book is not available
     * @return found List<BookDto>
     */

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        List<Book> foundItems = bookRepository.findAllByAvailable(available);
        return getBookDtos(foundItems);
    }

    /**
     * Method for finding List<BookDto> whose title contains or equals to the searching title
     *
     * @param title String title
     * @return found List<BookDto>
     * @throws IllegalArgumentException if the title is empty
     */
    @Override
    public List<BookDto> findByTitle(String title) throws IllegalArgumentException {
        if (title == null || title.equals(""))
            throw new IllegalArgumentException("Title should not be empty.");
        List<Book> foundItems = bookRepository.findAllByTitleContainingIgnoreCase(title);
        return getBookDtos(foundItems);
    }

    /**
     * Method for finding a BookDto with book id
     *
     * @param bookId id of the book
     * @return found BookDto
     * @throws ResourceNotFoundException if cannot find any book with the given id
     */
    @Override
    public BookDto findById(int bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Cannot find book with the id: " + bookId));
        return getBookDto(book);
    }

    /**
     * Method for finding all the bookDtos
     *
     * @return List<BookDto>
     */
    @Override
    public List<BookDto> findAll() {
        List<Book> foundItems = bookRepository.findAll();
        return getBookDtos(foundItems);
    }

    /**
     * Method for creating and saving a new BookDto
     *
     * @param bookDto bookDto that needs to be created
     * @return the bookDto that needs to be created
     * @throws RuntimeException if the book already exists, then should update the book if want to change any properties of the book
     */

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) throws RuntimeException {
        if (bookRepository.findById(bookDto.getBookId()).isPresent())
            throw new RuntimeException("Book already exists, please update");
        Book book = new Book(bookDto.getTitle(),bookDto.getMaxLoanDays(),bookDto.getFinePerDay(),bookDto.getDescription());
        book.setAvailable(bookDto.isAvailable());
        book.setReserved(bookDto.isReserved());
        return getBookDto(bookRepository.save(book));
    }

    /**
     * Method for updating a BookDto
     *
     * @param bookDto the bookDto that needs to be updated
     * @return the updated bookDto
     * @throws RuntimeException if the book does not exist, should create it first
     */
    @Override
    @Transactional
    public BookDto update(BookDto bookDto) throws RuntimeException {
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

    /**
     * Method for deleting a BookDto
     *
     * @param bookId id of the book that needs to be deleted
     * @return true: successfully deleted, false: book does not exist
     * @throws ResourceNotFoundException if cannot find a book with given id
     */

    @Override
    @Transactional
    public boolean delete(int bookId) throws ResourceNotFoundException{
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
