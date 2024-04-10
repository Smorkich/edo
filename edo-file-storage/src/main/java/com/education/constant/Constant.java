package com.education.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Anna Artemyeva
 * В этом обьекте константы для работы с файлами
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {
    public static final String FILE_CONTENT_TYPE = "application/pdf";

    // Size
    public static final Long objectSize = -1L;
    public static final Long partSize = 104857600L;

    // Extensions
    public static final String PNG = "png";
    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String PDF = "pdf";
    public static final List<String> APPEAL_XLSX_FILE_HEADERS = List.of("Резолюции", "Дата создания", "Исполнители",
            "Крайний срок резолюции", "Статус резолюции");
    public static final DateTimeFormatter APPEAL_FILE_ZDT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    public static final DateTimeFormatter APPEAL_FILE_LD_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
