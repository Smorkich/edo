package com.education.component;

import com.education.exceptions.MinIOPutException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.filter.MissingImageReaderException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static model.constant.Constant.DOC;
import static model.constant.Constant.DOCX;
import static model.constant.Constant.JPEG;
import static model.constant.Constant.JPG;
import static model.constant.Constant.PNG;

@Slf4j
@Log4j2
@RequiredArgsConstructor
@Component
public class MinioComponent {

    private final MinioClient minioClient;


    @Value("${minio.bucket-name}")
    private String bucketName;

    public void postObject(String objectName, InputStream inputStream, String contentType) {
        try (inputStream) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .contentType(contentType)
                    .stream(inputStream, -1, 104857600)
                    .build());
        } catch (Exception e) {
            log.error("Error while put object in MinIO {}", e.getMessage());
            throw new MinIOPutException(e.getMessage());
        }
    }

    public InputStream getObject(String objectName) {

        InputStream stream = null;
        try {
            stream = minioClient
                    .getObject(GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("Upload failed: {}",e.getMessage());
        }
        return stream;
    }

    public void deleteObjects(String objectNumber) {
        try {
            minioClient.removeObject(RemoveObjectArgs.
                    builder()
                    .bucket(bucketName)
                    .object(objectNumber)
                    .build());
        } catch (MinioException e) {
            log.error("Delete failed: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkConnection() {
        try {
            log.info("Starting connection to MINIO server");
            List<Bucket> blist = minioClient.listBuckets();
            log.info("Connection success, total buckets: " + blist.size());
        } catch (MinioException e) {
            log.error("Connection failed: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ByteArrayInputStream convertFileToPDF(MultipartFile file, String extension) {
        if (isImageFile(file)) {
            return convertImageToPDF(file, extension);
        } else if (isWordFile(file)) {
            return convertWordFileToPDF(file, extension);
        }

        return new ByteArrayInputStream(new byte[0]);
    }

    public boolean isImageFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return PNG.equals(extension) || JPEG.equals(extension) || JPG.equals(extension);
    }

    public boolean isWordFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return DOC.equals(extension) || DOCX.equals(extension);
    }

    private ByteArrayInputStream convertImageToPDF(MultipartFile file, String extension) {

        try (var fis = new BufferedInputStream(file.getInputStream())) {
            var doc = new PDDocument();
            var bos = new ByteArrayOutputStream();

            ByteArrayInputStream byteStream = new ByteArrayInputStream(fis.readAllBytes());
            BufferedImage awtImage = readImageFile(byteStream, extension);
            byteStream.reset();

            PDImageXObject pdImage = new PDImageXObject(doc, byteStream,
                    COSName.DCT_DECODE, awtImage.getWidth(), awtImage.getHeight(),
                    awtImage.getColorModel().getComponentSize(0),
                    PDDeviceRGB.INSTANCE);

            int width = pdImage.getWidth();
            int height = pdImage.getHeight();

            PDPage myPage = new PDPage(new PDRectangle(width, height));
            doc.addPage(myPage);

            float offset = 20f;

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
                cont.drawImage(pdImage, offset, offset, width, height);
            }

            doc.save(bos);
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            log.error("Convert image to PDF failed. File name: {}", file.getOriginalFilename());
            throw new RuntimeException(e);
        }

    }

    private ByteArrayInputStream convertWordFileToPDF(MultipartFile file, String extension) {

        try (var inputStream = new BufferedInputStream(file.getInputStream())) {
            var bos = new ByteArrayOutputStream();
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);
            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(wordMLPackage);
            Docx4J.toFO(foSettings, bos, Docx4J.FLAG_EXPORT_PREFER_XSL);
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (Docx4JException | IOException e) {
            log.error("Convert doc/docx to PDF failed. File name: {}", file.getOriginalFilename());
            throw new RuntimeException(e);
        }

    }

    private static BufferedImage readImageFile(InputStream stream, String extension) throws IOException {

        Iterator readers = ImageIO.getImageReadersByFormatName(extension.toUpperCase(Locale.ROOT));
        ImageReader reader = null;
        while (readers.hasNext()) {
            reader = (ImageReader) readers.next();
            if (reader.canReadRaster()) {
                break;
            }
        }

        if (reader == null) {
            throw new MissingImageReaderException("Cannot read image:");
        }

        ImageInputStream imageInputStream = null;
        try {
            imageInputStream = ImageIO.createImageInputStream(stream);
            reader.setInput(imageInputStream);

            ImageIO.setUseCache(false);
            return reader.read(0);
        } finally {
            if (imageInputStream != null) {
                imageInputStream.close();
            }
            reader.dispose();
        }
    }
}
