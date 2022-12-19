package com.education.controller;

import com.education.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Рест-контроллер для Author
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/service/author")
public class AuthorRestController {
    private final AuthorService authorService;

    @ApiOperation(value = "Gets all authors", notes = "Author must exist")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Add author", notes = "Author not must exist")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addAuthorAction(@RequestBody AuthorDto authorDto) {
        authorService.save(authorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete author", notes = "Author must exist")
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Gets authors by ids", notes = "Author must exist")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }
}
