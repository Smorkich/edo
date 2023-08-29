package com.education.service.minio.impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static model.constant.Constant.*;
import static org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromByteArray;

/**
 * @author Anna Artemyeva
 */
@Service
@Log4j2
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {
    private static final long NUMBER_OF_PAGES_FOR_NOT_PDF_FILES = 0L;
    private final RestTemplate restTemplate;


    @Override
    public ResponseEntity<String> uploadOneFile(MultipartFile currentFile,
                                                UUID UUIDKey, String fileName, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());
        body.add("key", UUIDKey.toString());
        body.add("fileName", fileName);

        var requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(getUri("/api/file-storage/upload"),
                requestEntity,
                String.class);
    }

    private static String getUri(String path) {
        return URIBuilderUtil.buildURI(EDO_FILE_STORAGE_NAME, path)
                .toString();
    }

    @Override
    public Resource downloadOneFile(String objectName) {
        String uri = getUri("/api/file-storage/download/") + objectName;
        return restTemplate.getForObject(uri, Resource.class);
    }

    @Override
    public void delete(String name) {
        restTemplate.delete(getUri("/api/file-storage/delete/" + name), Void.class);
    }

    /**
     * method counts pages of pdf files
     */
    @Override
    public long countPages(UUID UUIDKey, String originalExtension, String convertedContentType) {
        var convertedFileName = getConvertedFilename(UUIDKey.toString(), originalExtension, convertedContentType);
        var convertedFile = downloadOneFile(convertedFileName);

        if (PDF_CONTENT_TYPE.equals(convertedContentType)) {
            try (var fileInputStream = convertedFile.getInputStream();
                 var pddDoc = PDDocument.load(fileInputStream)) {
                return pddDoc.getNumberOfPages();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return NUMBER_OF_PAGES_FOR_NOT_PDF_FILES;
        }
    }

    @Override
    public String getConvertedFilename(String key, String extension, String contentType) {
        if (PDF_CONTENT_TYPE.equals(contentType)) {
            return String.format("%s.%s", key, PDF);
        } else {
            return String.format("%s.%s", key, extension);
        }
    }

    @Override
    public void overlayFacsimileOnFirstFile(UUID UUIDKey, String originalExtension, String convertedContentType, InputStream facsimileImage) {
        var convertedFileName = getConvertedFilename(UUIDKey.toString(), originalExtension, convertedContentType);
        var firstFile = downloadOneFile(convertedFileName);

        // Проверяем, что первый файл существует и является PDF-файлом
        if (firstFile != null && PDF_CONTENT_TYPE.equals(convertedContentType)) {
            try (var fileInputStream = firstFile.getInputStream();
                 var pddDoc = PDDocument.load(fileInputStream)) {
                // Создаем страницу с факсимиле изображением
                var fImage = new PDPage(new PDRectangle(100, 100));
                try (var contentStream = new PDPageContentStream(pddDoc, fImage)) {
                    // Загружаем факсимиле изображение
                    var facsimileXImage = createFromByteArray(pddDoc, IOUtils.toByteArray(facsimileImage), null);

                    // Осуществляем наложение факсимиле на первую страницу
                    var firstPage = pddDoc.getPage(0);
                    var graphicsState = new PDExtendedGraphicsState();
                    graphicsState.setNonStrokingAlphaConstant(0.5f); // Установите желаемую прозрачность
                    contentStream.setGraphicsStateParameters(graphicsState);
                    contentStream.drawImage(facsimileXImage, firstPage.getMediaBox().getWidth() - 20, 20, 100, 100); // Внесите необходимые изменения

                    // Добавляем страницу с факсимиле в начало документа
                    pddDoc.getDocumentCatalog().getPages().insertBefore(fImage, firstPage);
                }

                // Сохраняем изменения в PDF-файле
                //todo добавить сохранение файла с факсимиле в файловое хранилище
                // и замену основного файла обращения на файл с факсимиле
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
