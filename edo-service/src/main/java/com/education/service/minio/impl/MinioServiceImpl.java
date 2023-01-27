package com.education.service.minio.impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static model.constant.Constant.EDO_FILE_STORAGE_NAME;
import static model.constant.Constant.EDO_SERVICE_NAME;

/**
 * @author Anna Artemyeva
 */
@Service
@Log4j2
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final RestTemplate restTemplate;

    @Override
    public void uploadOneFile(MultipartFile currentFile)  {

        Path tempFilePDF = null;

        if (isImageFile(currentFile)) {
            tempFilePDF = convertImageToPDF(currentFile);
        } else if (isWordFile(currentFile)) {
            tempFilePDF = convertWordFileToPDF(currentFile);
        } else {
            return;
        }

        if (tempFilePDF != null) {
            File fileToSend = tempFilePDF.toFile();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(fileToSend));

            HttpEntity requestEntity = new HttpEntity<>(body, headers);

            String uri = URIBuilderUtil.buildURI(EDO_FILE_STORAGE_NAME, "/api/file-storage/upload")
                    .toString();

            try {
                restTemplate.postForObject(uri,
                        requestEntity,
                        String.class);
            } finally {
                fileToSend.delete();
            }
        }

    }

    public boolean isImageFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg");
    }

    public boolean isWordFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return extension.equals("doc") || extension.equals("docx");
    }

    public Path convertImageToPDF(MultipartFile file) {

        Path tempFile = null;
        Path tempFilePDF = null;

        try (PDDocument doc = new PDDocument()) {

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            tempFile = Files.createTempFile(null, String.format(".%s", extension));
            tempFilePDF = Files.createTempFile(null, ".pdf");

            Files.write(tempFile, file.getBytes());

            String imgFileName = tempFile.toAbsolutePath().toString();
            PDImageXObject pdImage = PDImageXObject.createFromFile(imgFileName, doc);

            int width = pdImage.getWidth();
            int height = pdImage.getHeight();

            PDPage myPage = new PDPage(new PDRectangle(width, height));
            doc.addPage(myPage);

            float offset = 20f;

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
                cont.drawImage(pdImage, offset, offset, width, height);
            }

            doc.save(tempFilePDF.toAbsolutePath().toString());

        } catch (IOException e) {
            log.info("Couldn't convert image file to PDF: {}", file.getOriginalFilename());
        } finally {
            if (tempFile != null) {
                tempFile.toFile().delete();
            }
        }

        return tempFilePDF;
    }

    public Path convertWordFileToPDF(MultipartFile file) {

        Path tempFile = null;
        Path tempFilePDF = null;
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        try {

            tempFile = Files.createTempFile(null, String.format(".%s", extension));
            tempFilePDF = Files.createTempFile(null, ".pdf");

            Files.write(tempFile, file.getBytes());
            Files.write(tempFilePDF, file.getBytes());

            InputStream inputStream = new FileInputStream(tempFile.toAbsolutePath().toString());
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);

            FileOutputStream outputStream = new FileOutputStream(tempFilePDF.toAbsolutePath().toString());
            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(wordMLPackage);
            Docx4J.toFO(foSettings, outputStream, Docx4J.FLAG_EXPORT_PREFER_XSL);

        } catch (Throwable e) {
            log.info("Couldn't convert doc/docx file to PDF: {}", file.getOriginalFilename());
        } finally {
            if (tempFile != null) {
                tempFile.toFile().delete();
            }
        }

        return tempFilePDF;
    }

}
