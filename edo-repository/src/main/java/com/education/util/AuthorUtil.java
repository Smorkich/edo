package com.education.util;

import com.education.entity.Author;
import model.dto.AuthorDto;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Util класс для реализации вспомогательных методов
 */

public class AuthorUtil {

    /**
     *Конвертация сущности Author в AuthorDto
     */
    public static AuthorDto toDto(Author author) {
        return AuthorDto.builder().
                id(author.getId()).
                firstName(author.getFirstName()).
                lastName(author.getLastName()).
                middleName(author.getMiddleName()).
                address(author.getAddress()).
                snils(author.getSnils()).
                mobilePhone(author.getMobilePhone()).
                email(author.getEmail()).
                employment(author.getEmployment()).
                fioDative(author.getFioDative()).
                fioGenitive(author.getFioGenitive()).
                fioNominative(author.getFioNominative())
                .build();
    }

    /**
     *Конвертация AuthorDto в сущность Author
     */
    public static Author toAuthor(AuthorDto authorDto){
        return Author.authorBuilder().
                firstName(authorDto.getFirstName()).
                lastName(authorDto.getLastName()).
                middleName(authorDto.getMiddleName()).
                address(authorDto.getAddress()).
                snils(authorDto.getSnils()).
                mobilePhone(authorDto.getMobilePhone()).
                email(authorDto.getEmail()).
                employment(authorDto.getEmployment()).
                fioDative(authorDto.getFioDative()).
                fioGenitive(authorDto.getFioGenitive()).
                fioNominative(authorDto.getFioNominative())
                .build();
    }

    /**
     *Конвертация коллекции <Author> в коллекцию <AuthorDto>
     */
    public static List<AuthorDto> ListAuthorDtos(List<Author> authors) {
        return authors.stream().
                map(AuthorUtil::toDto).
                toList();
    }
}
