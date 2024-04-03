package com.education.service.taskForm;

import com.education.feign.CurrentUserFeignClient;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class TaskFormServiceImpl implements TaskFormService {

    private final CurrentUserFeignClient currentUserFeignClient;
    private final ResolutionFeignClient resolutionFeignClient;

    @Override
    public byte[] generateTaskForm(Long id, String description) {

        Optional<EmployeeDto> currentUserOptional = Optional.ofNullable(
                currentUserFeignClient.getCurrentUser().getBody());
        Optional<ResolutionDto> resolutionOptional = Optional.ofNullable(
                resolutionFeignClient.findById(id).getBody());

        if (currentUserOptional.isPresent() && resolutionOptional.isPresent()) {
            EmployeeDto currentUser = currentUserOptional.get();
            String[] fioCurrentUser = currentUser.getFioNominative().split(" ");

            EmployeeDto executor = resolutionOptional.get().getExecutor().get(0);
            String[] fioExecutor = executor.getFioNominative().split(" ");

            List<String> partOfDescription = TextBuilderUtil.splitText(description);
            try {
                ClassPathResource templateResource = new ClassPathResource("templates/template.pdf");
                PDDocument document = PDDocument.load(templateResource.getInputStream());
                PDPage page = document.getPage(0);

                PDPageContentStream contentStream = new PDPageContentStream(document, page,
                        PDPageContentStream.AppendMode.APPEND, true, true);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(410, 700);
                for (String name : fioCurrentUser){
                    contentStream.showText(name);
                    contentStream.newLineAtOffset(0, -18);
                }
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(410, 621);
                if (currentUser.getPhone().isEmpty()){
                    contentStream.showText(currentUser.getPhone());
                    contentStream.newLineAtOffset(0, -18);
                }
                if (!currentUser.getWorkPhone().isEmpty()){
                    contentStream.showText(currentUser.getWorkPhone());
                    contentStream.newLineAtOffset(0, -18);
                }
                if (!currentUser.getEmail().isEmpty()){
                    contentStream.showText(currentUser.getEmail());
                }
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(410, 541);
                for (String name : fioExecutor){
                    contentStream.showText(name);
                    contentStream.newLineAtOffset(0, -18);
                }
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(80, 447);
                for (String part : partOfDescription){
                    contentStream.showText(part);
                    contentStream.newLineAtOffset(0, -18.1f);
                }
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(160, 180);
                contentStream.showText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(430, 180);
                contentStream.showText("333");
                contentStream.endText();
                contentStream.close();

                document.close();
                return new byte[0];
            } catch (IOException e) {
                log.error("bad request");
                return new byte[0];
            }
        } else {
            if (currentUserOptional.isEmpty()) {
                throw new IllegalArgumentException("Current user not found");
            }
            throw new IllegalArgumentException("Resolution not found");
        }
    }
}
