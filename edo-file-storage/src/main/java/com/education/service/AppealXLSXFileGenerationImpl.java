package com.education.service;

import com.education.feign.AppealFeignClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import java.util.List;

import static com.education.constant.Constant.*;

@AllArgsConstructor
@Service
@Slf4j
public class AppealXLSXFileGenerationImpl implements AppealXLSXFileGeneration {

    private AppealFeignClient appealFeignClient;

    /**
     * Generates an appeal file in xlsx format
     */
    @Override
    public ResponseEntity<byte[]> generateXLSXFile(Long appealId) {
        var appealFileDtos = appealFeignClient.findAllByAppealIdForXLSX(appealId);
        log.info("Received information about appeal resolutions for file generation");

        try (Workbook workbook = new XSSFWorkbook()) {
            log.info("File creation started");
            Sheet sheet = workbook.createSheet("Резолюции по обращению");
            initializeXLSXFile(sheet);

            Row row;
            Cell cell;
            int rowId = 1;
            for (AppealFileDto appealFileDto : appealFileDtos) {
                row = sheet.createRow(rowId);
                var fileInformation = List.of(String.valueOf(rowId++),
                        appealFileDto.getCreationDate().format(APPEAL_FILE_ZDT_FORMATTER),
                        String.join(", ", appealFileDto.getExecutorFIOs()),
                        appealFileDto.getDeadlineResolution() != null ? appealFileDto.getDeadlineResolution()
                                .format(APPEAL_FILE_LD_FORMATTER) : "Нет крайнего срока",
                        appealFileDto.getResolutionStatus());
                int cellId = 0;
                for (String fileInfo : fileInformation) {
                    cell = row.createCell(cellId++);
                    cell.setCellValue(fileInfo);
                }
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

    private void initializeXLSXFile(Sheet sheet) {
        sheet.setColumnWidth(0, 11 * 256);
        sheet.setColumnWidth(1, 18 * 256);
        sheet.setColumnWidth(2, 100 * 256);
        sheet.setColumnWidth(3, 23 * 256);
        sheet.setColumnWidth(4, 17 * 256);

        Row row = sheet.createRow(0);
        Cell cell;
        for (int i = 0; i < 5; i++) {
            cell = row.createCell(i);
            cell.setCellValue(APPEAL_XLSX_FILE_HEADERS.get(i));
        }

    }
}
