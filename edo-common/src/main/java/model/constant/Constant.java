package model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Kostenko Aleksandr
 * В этом обьекте лежит константы для ServiceExternalEmployeeImpl
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {
    public static final String EDO_REPOSITORY_NAME = "edo-repository";
    public static final String EDO_SERVICE_NAME = "edo-service";
    public static final String EDO_FILE_STORAGE_NAME = "edo-file-storage";

    // Extensions
    public static final String PNG = "png";
    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String PDF = "pdf";
}