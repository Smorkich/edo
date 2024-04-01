package com.education.service;

import com.education.feign.ResolutionFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.AppealFileDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AppealXLSXFileGenerationImpl implements AppealXLSXFileGeneration {

    private final ResolutionFeignClient resolutionFeignClient;

    /**
     * Generates an appeal file in xlsx format
     */
    @Override
    public ResponseEntity<byte[]> generateXLSXFile(Long appealId) {
        var appealFileDto = resolutionFeignClient.findAllByAppealIdForXLSX(appealId).stream().toList();
        log.info("Received information about appeal resolutions for file generation");

        try (Workbook workbook = new XSSFWorkbook()) {
            log.info("File creation started");
            Sheet sheet = workbook.createSheet("Резолюции по обращению");
            sheet.setColumnWidth(0, 11 * 256);
            sheet.setColumnWidth(1, 18 * 256);
            sheet.setColumnWidth(2, 100 * 256);
            sheet.setColumnWidth(3, 23 * 256);
            sheet.setColumnWidth(4, 17 * 256);

            Row row;
            List<String> fileInformation = new ArrayList<>();
            DateTimeFormatter formatterForZonedDateTime = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
            DateTimeFormatter formatterForLocalDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            for (int i = 0; i <= appealFileDto.size(); i++) {
                row = sheet.createRow(i);
                if (i == 0) {
                    fileInformation.addAll(Arrays.asList("Резолюции", "Дата создания", "Исполнители",
                            "Крайний срок резолюции", "Статус резолюции"));
                } else {
                    AppealFileDto appealfiledto = appealFileDto.get(i - 1);
                    String deadline = appealfiledto.getDeadlineResolution().format(formatterForLocalDate);
                    fileInformation.addAll(Arrays.asList(String.valueOf(i),
                            appealfiledto.getCreationDate().format(formatterForZonedDateTime),
                            String.join(", ", appealfiledto.getExecutorFIOs()),
                            deadline.equals("01.01.0001") ? "Нет крайнего срока" : deadline,
                            appealfiledto.getResolutionStatus()));
                }
                int cellId = 0;
                for (String fileInfo : fileInformation) {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue(fileInfo);
                }
                fileInformation.clear();
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                log.info("File creation finished");

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=appeal_%d.xlsx", appealId));
                headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                log.info("Sending response with XLSX file");
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(outputStream.toByteArray());
            }
        } catch (IOException e) {
            log.error("Can't create workbook for XLSX file", e);
        }

        return null;
    }
}
