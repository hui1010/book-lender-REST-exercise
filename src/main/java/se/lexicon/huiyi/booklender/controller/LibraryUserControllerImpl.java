package se.lexicon.huiyi.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.service.LibraryUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class LibraryUserControllerImpl implements LibraryUserController{
    LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserControllerImpl(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Override
    @GetMapping(path = "/{userId}")
    public ResponseEntity<LibraryUserDto> findById(@PathVariable int userId) {
        return ResponseEntity.ok(libraryUserService.findById(userId));
    }

    @Override
    @GetMapping(path = "/email/{userEmail}")
    public ResponseEntity<LibraryUserDto> findByEmail(@Valid @PathVariable("userEmail") String email) {
        return ResponseEntity.ok(libraryUserService.findByEmail(email));
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<LibraryUserDto>> findAll() {
        return ResponseEntity.ok(libraryUserService.findAll());
    }

    @Override
    @PostMapping
    public ResponseEntity<LibraryUserDto> create(@Valid @RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(libraryUserDto));
    }

    @Override
    @PutMapping(path = "/update")
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.ok(libraryUserService.update(libraryUserDto));
    }
}
