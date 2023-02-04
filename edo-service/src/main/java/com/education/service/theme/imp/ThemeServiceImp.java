package com.education.service.theme.imp;

import com.education.service.theme.ThemeService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ThemeDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

/**
 * @author AlexeySpiridonov
 */

@Service
@Log4j2
@AllArgsConstructor
public class ThemeServiceImp implements ThemeService {

    private final RestTemplate restTemplate;


    @Override
    public void save(ThemeDto themeDto) throws URISyntaxException {
        log.info("sent a request to save the theme in edo - repository");
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), themeDto, ThemeDto.class);
        log.info("sent a request to save the theme in edo - repository");
    }

    @Override
    public void moveToArchived(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.build(), null, ThemeDto.class);
        log.info("sent a request to archive the theme in edo - repository");
    }

    @Override
    public ThemeDto findById(Long id) throws URISyntaxException {
        log.info("sent a request to receive the theme in edo - repository");
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), ThemeDto.class);
    }

    @Override
    public Collection<ThemeDto> findByAllId(String ids) throws URISyntaxException {
        log.info("sent a request to receive the theme in edo - repository");
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/all/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    public ThemeDto findByIdNotArchived(Long id) throws URISyntaxException {
        log.info("sent a request to receive the theme not archived in edo - repository");
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/NotArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), ThemeDto.class);
    }

    @Override
    public Collection<ThemeDto> findByAllIdNotArchived(String ids) throws URISyntaxException {
        log.info("sent a request to receive the theme not archived in edo - repository");
        var builder = buildURI(EDO_REPOSITORY_NAME, THEME_URL)
                .setPath("/NotArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }
}
