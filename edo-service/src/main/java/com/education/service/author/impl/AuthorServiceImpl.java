package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import com.education.util.GeocodeMapsYandexMapper;
import lombok.AllArgsConstructor;
import model.dto.AuthorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

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
            var authorFullAddressData = "full address: " + addressDto.getFullAddress()
                    + (addressDto.getIndex() != null ? ", index: " + addressDto.getIndex() : "")
                    + ", longitude: " + addressDto.getLongitude() + ", latitude: " + addressDto.getLatitude();
            authorDto.setAddress(authorFullAddressData);
        }
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL);
        return restTemplate.postForObject(builder.toString(), authorDto, AuthorDto.class);
    }

    /**
     * Удаление Author по id
     */
    @Override
    public void delete(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.toString());
    }

    /**
     * Поиск Author по id
     */
    @Override
    public AuthorDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, AUTHOR_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
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
