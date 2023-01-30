package model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {
    /**
    * @author Andreev Valentin
    * константы типов данных для классов MinioController и MinIOController
    */
    public static final String PDF = "pdf";
    public static final String PNG = "png";
    public static final String JPEG = "jpeg";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String JPG = "jpg";

    public static final String EDO_REPOSITORY_NAME = "edo-repository";
    public static final String EDO_SERVICE_NAME = "edo-service";
    public static final String EMPLOYEE_FIO_SEARCH_PARAMETER = "fullName";
    public static final String EDO_FILE_STORAGE_NAME = "edo-file-storage";
}
