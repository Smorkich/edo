package com.education.service.taskForm;

import com.education.exceptions.TaskFormGenerateException;
import com.education.feign.CurrentUserFeignClient;
import com.education.feign.FacsimileFeignClient;
import com.education.feign.ResolutionFeignClient;
import com.education.utils.TextBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.ResolutionDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static model.constant.Constant.TASKFORM_TEMPLATE_PATH;

@Log4j2
@Service
@AllArgsConstructor
public class TaskFormServiceImpl implements TaskFormService {

    private final CurrentUserFeignClient currentUserFeignClient;
    private final ResolutionFeignClient resolutionFeignClient;
    private final FacsimileFeignClient facsimileFeignClient;

    @Override
    public byte[] generateTaskForm(Long id, String description) {

        EmployeeDto currentUser = getCurrentUser();
        ResolutionDto resolution = getResolution(id);
        EmployeeDto executor = resolution.getExecutor().get(0);
        List<String> partOfDescription = TextBuilderUtil.splitText(description);

        try {
            try (PDDocument document = loadDocument(TASKFORM_TEMPLATE_PATH)) {
                PDPage page = document.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(document, page,
                        PDPageContentStream.AppendMode.APPEND, true, true);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

                addTextToStream(contentStream, 410, 700, currentUser.getFioNominative());
                addContactInfoToStream(contentStream, 410, 621, currentUser);
                addTextToStream(contentStream, 410, 541, executor.getFioNominative());
                addTextListToStream(contentStream, 80, 447, partOfDescription);
                addDateToStream(contentStream, 160, 180);

                addFacsimileToStream(contentStream, document, 430, 180,
                        facsimileFeignClient.getFacsimileFileByEmployeeId(currentUser.getId()).getBody());

                contentStream.close();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);

                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            log.error("Ошибка при генерации PDF для резолюции с ID: " + resolution.getId(), e);
            throw new TaskFormGenerateException(e.getMessage());
        }
    }

    private EmployeeDto getCurrentUser() {
        return Optional.ofNullable(currentUserFeignClient.getCurrentUser().getBody())
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
    }

    private ResolutionDto getResolution(Long id) {
        return Optional.ofNullable(resolutionFeignClient.findById(id).getBody())
                .orElseThrow(() -> new IllegalArgumentException("Resolution not found"));
    }

    private PDDocument loadDocument(String templatePath) throws IOException {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        return PDDocument.load(templateResource.getInputStream());
    }

    private void addTextToStream(PDPageContentStream contentStream, float x, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void addTextListToStream(PDPageContentStream contentStream, float x, float y,
                                     List<String> textList) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        for (String text : textList) {
            contentStream.showText(text);
            contentStream.newLineAtOffset(0, -18);
        }
        contentStream.endText();
    }

    private void addContactInfoToStream(PDPageContentStream contentStream, float x, float y,
                                        EmployeeDto employee) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        if (!employee.getPhone().isEmpty()) {
            contentStream.showText(employee.getPhone());
            contentStream.newLineAtOffset(0, -18);
        }
        if (!employee.getWorkPhone().isEmpty()) {
            contentStream.showText(employee.getWorkPhone());
            contentStream.newLineAtOffset(0, -18);
        }
        if (!employee.getEmail().isEmpty()) {
            contentStream.showText(employee.getEmail());
        }
        contentStream.endText();
    }

    private void addDateToStream(PDPageContentStream contentStream, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        contentStream.endText();
    }

    private void addFacsimileToStream(PDPageContentStream contentStream, PDDocument document, float x, float y,
                                      Resource facsimile) throws IOException {
        if (facsimile != null) {
            try (InputStream imageStream = facsimile.getInputStream()) {
                byte[] imageBytes = imageStream.readAllBytes();
                PDImageXObject image = PDImageXObject.createFromByteArray(document, imageBytes,
                        facsimile.getFilename());
                contentStream.drawImage(image, x, y);
            }
        }
    }
}