package com.education.controller;

import com.education.service.taskForm.TaskFormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static model.constant.Constant.FILE_STORAGE_RESOLUTION_URL;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Контроллер для заполнения печатной формы задания", description = "Возвращает заполненный файл задачи по " +
                                                                              "резолюции")
@RequestMapping(FILE_STORAGE_RESOLUTION_URL)
public class TaskFormController {

    private TaskFormService taskFormService;
    @PostMapping("/{id}/report")
    public ResponseEntity<byte[]> createForm(@PathVariable Long id, @RequestBody String description) {
        byte[] pdfBytes = taskFormService.generateTaskForm(id,description);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=task_form.pdf")
                .body(pdfBytes);
    }
}
