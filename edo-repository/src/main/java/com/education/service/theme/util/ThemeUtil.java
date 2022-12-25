package com.education.service.theme.util;

import com.education.entity.Theme;

import model.dto.ThemeDto;

import java.util.List;

/**
 *@author AlexeySpiridonov
 * Реализация вспомогательных методов
 */

public class ThemeUtil {
    /**
     *Конвертация сущности Theme в ThemeDto
     */
    public static ThemeDto toDto(Theme theme) {
        return new ThemeDto(
                theme.getId(),
                theme.getName(),
                theme.getArchivedDate(),
                theme.getCreationDate(),
                theme.getCode()
        );
    }

    /**
     *Конвертация ThemeDto в Theme
     */
    public static Theme toTheme(ThemeDto themeDto){
        return new Theme(
                themeDto.getId(),
                themeDto.getName(),
                themeDto.getArchivedDate(),
                themeDto.getCreationDate(),
                themeDto.getCode()
        );
    }

    /**
     *Конвертация коллекции <Theme> в коллекцию <ThemeDto>
     */
    public static List<ThemeDto> ListThemeDto(List<Theme> themees) {
        return themees.stream()
                .map(ThemeUtil::toDto)
                .toList();
    }
}
