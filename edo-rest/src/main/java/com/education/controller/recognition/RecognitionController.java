package com.education.controller.recognition;

import com.education.service.recognition.RecognitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest-контроллер в "edo-rest", служит для отправки файла на распознавание по id Appeal
 */
@RestController
@Log4j2
@AllArgsConstructor
@Tag(name ="Контроллер для отправки id обращения для распознования")
@RequestMapping("/api/rest/recognition")
public class RecognitionController {
    private final RecognitionService recognitionService;

    @Operation(summary = "Принимает id обращения, достает по нему файл из file-storage, " +
            "отправляет на распознавание в очередь FILE_RECOGNITION_START")
    @GetMapping("/{id}")
    public ResponseEntity<String> recognition(@PathVariable("id") Long id) {

        log.info("Send appeal to recognize");
        recognitionService.recognize(id);
        return ResponseEntity.status(HttpStatus.OK).body("File sent to recognize");
    }
}
