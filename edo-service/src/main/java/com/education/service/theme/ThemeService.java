package com.education.service.theme;

import model.dto.ThemeDto;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author AlexeySpiridonov
 */

public interface ThemeService {
    void save(ThemeDto themeDto) throws URISyntaxException;

    void moveToArchived(Long id) throws URISyntaxException;

    ThemeDto findById(Long id) throws URISyntaxException;

    Collection<ThemeDto> findByAllId(String ids) throws URISyntaxException;

    ThemeDto findByIdNotArchived(Long id) throws URISyntaxException;

    Collection<ThemeDto> findByAllIdNotArchived(String ids) throws URISyntaxException;
}
