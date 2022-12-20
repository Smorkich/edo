package com.education.controller;

import com.education.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Рест-контроллер для Author
 */

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/author")
public class AuthorRestController {
    private final AuthorService authorService;

    @ApiOperation(value = "Gets all authors", notes = "Author must exist")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        log.info("GET: /api/service/author");
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Add author", notes = "Author not must exist")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addAuthorAction(@RequestBody AuthorDto authorDto) {
        log.info("POST: /api/service/author");
        authorService.save(authorDto);
        log.info("POST request successful");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete author", notes = "Author must exist")
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Long id) {
        log.info("DELETE: /api/service/author/"+id);
        authorService.delete(id);
        log.info("DELETE request successful");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Gets authors by id", notes = "Author must exist")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        log.info("Get: /api/service/author/"+id);
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }
}
