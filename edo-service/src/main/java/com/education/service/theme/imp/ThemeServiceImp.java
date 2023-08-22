package com.education.service.theme.imp;

import com.education.feign.ThemeFeignClient;
import com.education.service.theme.ThemeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ThemeDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author AlexeySpiridonov
 */

@Service
@Log4j2
@AllArgsConstructor
public class ThemeServiceImp implements ThemeService {

    private final ThemeFeignClient themeFeignClient;


    @Override
    public void save(ThemeDto themeDto) {
        themeFeignClient.save(themeDto);
    }

    @Override
    public void moveToArchived(Long id) {
        themeFeignClient.moveToArchive(id);
    }

    @Override
    public ThemeDto findById(Long id) {
        return themeFeignClient.findById(id);
    }

    @Override
    public Collection<ThemeDto> findByAllId(String ids) {
        return themeFeignClient.findByAllId(ids);

    }

    @Override
    public ThemeDto findByIdNotArchived(Long id) {
        return themeFeignClient.findByIdNotArchived(id);
    }

    @Override
    public Collection<ThemeDto> findByAllIdNotArchived(String ids) {
        return  themeFeignClient.findByAllIdNotArchived(ids);
    }
}
