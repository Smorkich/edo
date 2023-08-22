package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import com.education.util.GeocodeMapsYandexMapper;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.AUTHOR_URL;
import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Сервис-класс с методами для реализации API
 */

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final RestTemplate restTemplate;

    private final GeocodeMapsYandexMapper geocodeMapsYandexMapper;

    /**
     * Сохранение Author
     */
    @Override
    public AuthorDto save(AuthorDto authorDto) {
        var addressDto = geocodeMapsYandexMapper.toAddressDto(authorDto.getAddress());
        if (addressDto != null) {
            var authorFullAddressData = "Полный адрес: " + addressDto.getFullAddress()
                    + (addressDto.getIndex() != null ? ", почтовый индекс: " + addressDto.getIndex() : "")
                    + ", долгота: " + addressDto.getLongitude() + ", широта: " + addressDto.getLatitude();
            authorDto.setAddress(authorFullAddressData);
        }
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL);
        return restTemplate.postForObject(builder.toString(), authorDto, AuthorDto.class);
    }

    /**
     * Сохраняет коллекцию AuthorDto, которая передаётся в параметр - отправляет запрос в контроллер edo-repository
     *
     * @param authorDtos коллекция добавляемых AuthorDto
     * @return Collection<AuthorDto> - коллекция DTO сущности Author (авторы)
     */
    @Override
    public Collection<AuthorDto> saveAll(Collection<AuthorDto> authorDtos) {
        var dtos = Stream.ofNullable(authorDtos)
                .flatMap(Collection::stream)
                .map(this::save).toList();
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL + "/all");
        HttpEntity<Collection<AuthorDto>> httpEntity = new HttpEntity<>(dtos);
        ParameterizedTypeReference<Collection<AuthorDto>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri.toString(), HttpMethod.POST, httpEntity, responseType).getBody();
    }

    /**
     * Удаление Author по id
     */
    @Override
    public void delete(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL + "/" + id);
        restTemplate.delete(builder.toString());
    }

    /**
     * Поиск Author по id
     */
    @Override
    public AuthorDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL + "/" + id);
        return restTemplate.getForObject(builder.toString(), AuthorDto.class);
    }

    /**
     * Список Author`s
     */
    @Override
    public List<AuthorDto> findAll() {
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.toString(), List.class);
    }
}
