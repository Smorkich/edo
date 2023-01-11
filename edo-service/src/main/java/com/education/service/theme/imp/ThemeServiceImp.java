package com.education.service.theme.imp;

import com.education.service.theme.ThemeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ThemeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

/**
 * @author AlexeySpiridonov
 */

@Service
@Log4j2
@AllArgsConstructor
public class ThemeServiceImp implements ThemeService {

    static final String URL = "http://edo-repository/api/repository/theme";
    private final RestTemplate restTemplate;

    @Override
    public void save(ThemeDto themeDto) {
        log.info("sent a request to save the theme in edo - repository");
        restTemplate.postForObject(URL, themeDto, ThemeDto.class);
        log.info("sent a request to save the theme in edo - repository");
    }

    @Override
    public void moveToArchived(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, String.class);
        log.info("sent a request to archive the theme in edo - repository");
    }

    @Override
    public ThemeDto findById(Long id) {
        log.info("sent a request to receive the theme in edo - repository");
        return restTemplate.getForObject(URL + "/" + id, ThemeDto.class);
    }

    @Override
    public Collection<ThemeDto> findByAllId(String ids) {
        log.info("sent a request to receive the theme in edo - repository");
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public ThemeDto findByIdNotArchived(Long id) {
        log.info("sent a request to receive the theme not archived in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchived/" + id, ThemeDto.class);    }

    @Override
    public Collection<ThemeDto> findByAllIdNotArchived(String ids) {
        log.info("sent a request to receive the theme not archived in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchivedAll/" + ids, List.class);
    }
}
