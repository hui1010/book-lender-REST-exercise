package se.lexicon.huiyi.booklender.controller;

import org.springframework.http.ResponseEntity;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;



import java.util.List;

public interface LibraryUserController {
    ResponseEntity<LibraryUserDto> findById(int userId);
    ResponseEntity<LibraryUserDto> findByEmail(String email);
    ResponseEntity<List<LibraryUserDto>> findAll();
    ResponseEntity<LibraryUserDto> create(LibraryUserDto libraryUserDto);
    ResponseEntity<LibraryUserDto> update(LibraryUserDto libraryUserDto);
}
